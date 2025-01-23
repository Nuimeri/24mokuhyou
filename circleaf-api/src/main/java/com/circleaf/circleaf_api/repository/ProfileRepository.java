package com.circleaf.circleaf_api.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.circleaf.circleaf_api.mapper.ProfileMapper;
import com.circleaf.circleaf_api.model.Profile;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProfileRepository {
    
    private final ProfileMapper profileMapper;

    public List<Profile> findAll() {
        return profileMapper.findAll();
    }

    public Profile get(String username) {
        return profileMapper.get(username);
    }

    public int insert(Profile profile) {
        return profileMapper.insert(profile);
    }

    public int update(Profile profile) {
        return profileMapper.update(profile);
    }

    public int delete(Long id) {
        return profileMapper.delete(id);
    }

    // AccountとProfileを紐付け
    public Profile getRefAccount(Long accountId){
        return profileMapper.getRefAccount(accountId);
    }

    // AccountとProfileを紐付け
    public Long getAccount(String username){
        return profileMapper.getAccount(username);
    }

    // signup時の仮プロフィール作成
    public Long signup(Long accountId){
        final Profile profile = new Profile();
        profileMapper.signup(profile,accountId);
        return profile.getId();
    }
}
