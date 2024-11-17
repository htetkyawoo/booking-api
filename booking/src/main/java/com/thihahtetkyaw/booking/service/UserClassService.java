package com.thihahtetkyaw.booking.service;

import com.thihahtetkyaw.booking.entity.Classes;
import com.thihahtetkyaw.booking.entity.UserClasses;
import com.thihahtetkyaw.booking.entity.embeddedId.UserClassesId;
import com.thihahtetkyaw.booking.exception.CountryMissMatchException;
import com.thihahtetkyaw.booking.exception.OverLappingException;
import com.thihahtetkyaw.booking.repo.UserClassRepo;
import com.thihahtetkyaw.packages.entity.embedded.UserPackagesId;
import com.thihahtetkyaw.packages.exception.PackagesExpireException;
import com.thihahtetkyaw.packages.service.UserPackagesService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserClassService {

    private final UserClassRepo userClassRepo;
    private final UserPackagesService userPackagesService;
    private final ClassesService classesService;
    private final RedisTemplate<String, Object> redisTemplate;

    public List<Classes> findByUserId(long userId) {
        return userClassRepo.findByUserId(userId);
    }

    @Transactional
    public UserClasses book(long userId, long classId) {
        var classes = classesService.findById(classId);

        if (userClassRepo.hasClassBetween(userId, classes.getStartTime(), classes.getEndTime())) {
            throw new OverLappingException("Your booking overlaps with another class.");
        }

        var packageOpt = userPackagesService.getActivePackage(userId);
        if (packageOpt.isEmpty()) {
            throw new PackagesExpireException("Please purchase a new package.");
        }
        var activePackage = packageOpt.get();

        if (activePackage.getCountryId() != classes.getCountryId()) {
            throw new CountryMissMatchException("You can't book a class in a different country.");
        }

        String bookedSlotsKey = "class:" + classId + ":booked_slots";
        String waitingListKey = "class:" + classId + ":waiting_list";

        Long currentBookingCount = redisTemplate.opsForValue().increment(bookedSlotsKey);

        try {
            if (currentBookingCount != null && currentBookingCount > classes.getTotalSlot()) {
                redisTemplate.opsForList().rightPush(waitingListKey, String.valueOf(userId));
                redisTemplate.opsForValue().decrement(bookedSlotsKey);
            }
            var userClass = new UserClasses(new UserClassesId(userId, classId));
            userPackagesService.deduct(new UserPackagesId(userId, activePackage.getId()), classes.getCost());
            return userClassRepo.save(userClass);
        } catch (Exception e) {
            redisTemplate.opsForValue().decrement(bookedSlotsKey);
            throw e;
        }
    }

    @Transactional
    public boolean delete(UserClassesId userClassesId) {
        var userClasses = userClassRepo.findById(userClassesId)
                .orElseThrow(() -> new EntityNotFoundException("You haven't booked this class."));

        var classes = classesService.findById(userClassesId.getClassId());

        if (classes.getStartTime().minusHours(4).isBefore(LocalDateTime.now())) {
            var activePackage = userPackagesService.getActivePackage(userClassesId.getUserId())
                    .orElseThrow(() -> new PackagesExpireException("Your package is expired."));
            userPackagesService.refund(new UserPackagesId(userClassesId.getUserId(), activePackage.getId()), classes.getCost());
        }

        userClassRepo.delete(userClasses);
        String bookedSlotsKey = "class:" + userClassesId.getClassId() + ":booked_slots";
        String waitingListKey = "class:" + userClassesId.getClassId() + ":waiting_list";

        redisTemplate.opsForValue().decrement(bookedSlotsKey);

        String nextUserId = (String) redisTemplate.opsForList().leftPop(waitingListKey);
        if (nextUserId != null) {
            long nextUser = Long.parseLong(nextUserId);
            var activePackage = userPackagesService.getActivePackage(nextUser).orElseThrow(() -> new PackagesExpireException("Your package is expired."));
            var nextUserClass = new UserClasses(new UserClassesId(nextUser, userClassesId.getClassId()));
            userPackagesService.deduct(new UserPackagesId(nextUser, activePackage.getId()), classes.getCost());
            userClassRepo.save(nextUserClass);
            redisTemplate.opsForValue().increment(bookedSlotsKey);
        }

        return true;
    }


}
