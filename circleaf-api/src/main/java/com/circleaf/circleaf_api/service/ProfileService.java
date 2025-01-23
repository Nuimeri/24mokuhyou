package com.circleaf.circleaf_api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.circleaf.circleaf_api.model.Profile;
import com.circleaf.circleaf_api.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    @Transactional(readOnly = true)
    public List<Profile> findAll(){
        return profileRepository.findAll();
    }

    @Transactional(readOnly = false)
    public int insert(Profile profile){
        return profileRepository.insert(profile);
    }

    @Transactional(readOnly = true)
    public Profile get(String username){
        return profileRepository.get(username);
    }

    @Transactional(readOnly = false)
    public int update(Profile profile){
        return profileRepository.update(profile);
    }

    @Transactional(readOnly = true)
    public int delete(Long id){
        return profileRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public Profile getRefAccount(Long accountId){
        // accountIdと紐付いているProfileを取得
        return profileRepository.getRefAccount(accountId);
    }

    @Transactional(readOnly = true)
    public Long getAccount(String username){
        // usernameと紐付いているAccountを取得
        return profileRepository.getAccount(username);
    }

}
