package com.thihahtetkyaw.booking.job;

import com.thihahtetkyaw.booking.service.ClassesService;
import com.thihahtetkyaw.booking.service.UserClassService;
import com.thihahtetkyaw.packages.entity.embedded.UserPackagesId;
import com.thihahtetkyaw.packages.exception.PackagesExpireException;
import com.thihahtetkyaw.packages.service.UserPackagesService;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RefundJob implements Job {

    private final RedisTemplate<String, Object> redisTemplate;
    private final UserPackagesService userPackagesService;
    private final ClassesService classesService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        long classId = context.getJobDetail().getJobDataMap().getLong("classId");
        String waitingListKey = "class:" + classId + ":waiting_list";
        String userId;
        var classes = classesService.findById(classId);
        while ((userId = (String) redisTemplate.opsForList().leftPop(waitingListKey)) != null) {
            var usrId = Long.parseLong(userId);
            try {
                var activePackage = userPackagesService.getActivePackage(usrId)
                        .orElseThrow(() -> new PackagesExpireException("Your package is expired. So can't refund %d.Please content our customer service.".formatted(classes.getCost())));
                userPackagesService.refund(new UserPackagesId(usrId, activePackage.getId()), classes.getCost());
                System.out.printf("Refund successful to %s amount %s.%n", userId, classes.getCost());
            }catch (PackagesExpireException e) {
                continue;
            }
        }
    }
}
