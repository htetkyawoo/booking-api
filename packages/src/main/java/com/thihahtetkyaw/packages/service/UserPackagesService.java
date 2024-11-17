package com.thihahtetkyaw.packages.service;

import com.thihahtetkyaw.packages.exception.PurchaseException;
import com.thihahtetkyaw.packages.dto.UserPackagesDto;
import com.thihahtetkyaw.packages.entity.UserPackages;
import com.thihahtetkyaw.packages.entity.embedded.UserPackagesId;
import com.thihahtetkyaw.packages.mapping.UserPackagesMapper;
import com.thihahtetkyaw.packages.repo.UserPackagesRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserPackagesService {

    private final UserPackagesRepo repo;
    private final PaymentService paymentService;
    private final PackagesService packagesService;
    private final UserPackagesMapper mapping;

    public List<UserPackagesDto> findUserPackages(long id){
        return repo.findAllUserPackages(id).stream().map(mapping::toDto).toList();
    }

    public int deduct(UserPackagesId id, int amount){
        var userPackages = findById(id);
        var remaining = userPackages.getRemainingCredits() - amount;
        userPackages.setRemainingCredits(remaining);
        repo.save(userPackages);
        return remaining;
    }

    public int refund(UserPackagesId id, int amount){
        var userPackages = findById(id);
        var remaining = userPackages.getRemainingCredits() + amount;
        userPackages.setRemainingCredits(remaining);
        repo.save(userPackages);
        return remaining;
    }

    public UserPackages findById(UserPackagesId id){
        return repo.findByUserPackageId(id).orElseThrow(() -> new EntityNotFoundException("You did not buy the class."));
    }

    public Optional<UserPackages> findByIdOptional(UserPackagesId id){
        return repo.findById(id);
    }

    @Transactional
    public UserPackages purchase(UserPackagesId id, String cardDetails){
        var prepackagesOpt = findByIdOptional(id);
        var packages = packagesService.findById(id.getPackageId());
        if(paymentService.PaymentCharge(cardDetails, packages.getPrice())){
            if(prepackagesOpt.isPresent()){
                var userPackage = prepackagesOpt.get();
                userPackage.setStartTime(LocalDateTime.now());
                userPackage.setRemainingCredits(userPackage.getRemainingCredits() + packages.getCredits());
                return repo.save(userPackage);
            }
            return repo.save(new UserPackages(id, LocalDateTime.now(), packages.getCredits()));
        }
        throw new PurchaseException("Please check the card details or amount.");
    }

    public Optional<UserPackagesDto> getActivePackage(long id){
        return this.findUserPackages(id).stream().peek(p -> System.out.println(p.isExpired())).filter(p -> !p.isExpired()).findFirst();
    }

}
