package com.thihahtetkyaw.user.service;

import com.thihahtetkyaw.user.entity.Profile;
import com.thihahtetkyaw.user.repo.ProfileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepo repo;

    public String getProfile(long id){
        return repo.findById(id).getUrl();
    }

    public Profile save(long id, String url){
        return repo.save(new Profile(url, id));
    }
}
