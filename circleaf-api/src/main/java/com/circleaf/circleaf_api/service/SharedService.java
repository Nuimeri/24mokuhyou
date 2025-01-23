package com.circleaf.circleaf_api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.circleaf.circleaf_api.model.Profile;
import com.circleaf.circleaf_api.model.Team;
import com.circleaf.circleaf_api.repository.MemberRepository;
import com.circleaf.circleaf_api.repository.ProfileRepository;
import com.circleaf.circleaf_api.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SharedService {
    
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    @Transactional(readOnly = true)
    public String getTeamLeaderUsername(String code){
        final Team team = teamRepository.get(code);
        final Profile profile = profileRepository.getRefAccount(team.getLeader());

        return profile.getUsername();
    }
}
