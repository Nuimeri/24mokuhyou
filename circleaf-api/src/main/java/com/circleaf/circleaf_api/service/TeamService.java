package com.circleaf.circleaf_api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.circleaf.circleaf_api.constant.Constants;
import com.circleaf.circleaf_api.model.Member;
import com.circleaf.circleaf_api.model.Profile;
import com.circleaf.circleaf_api.model.Team;
import com.circleaf.circleaf_api.repository.MemberRepository;
import com.circleaf.circleaf_api.repository.ProfileRepository;
import com.circleaf.circleaf_api.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamService {
    
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    @Transactional(readOnly = true)
    public List<Team> findAll(){
        return teamRepository.findAll();
    }

    @Transactional(readOnly = false)
    public int insert(Team team){
        // ログイン中ユーザのaccount_idを取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String authAccountId = authentication.getName();
        final Long accountId = Long.parseLong(authAccountId);

        team.setCreateBy(accountId);
        team.setFounder(accountId);
        team.setUpdateBy(accountId);
        team.setLeader(accountId);

        final int teamId = teamRepository.insert(team);
        if(teamId < 0){
            // 失敗時ロールバックしてnull返却
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Constants.IS_ERR_INT;
        }

        return teamId;
    }

    @Transactional(readOnly = true)
    public Team get(String code){
        return teamRepository.get(code);
    }

    @Transactional(readOnly = false)
    public int update(Team team){
        // ログイン中ユーザのaccount_idを取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String authAccountId = authentication.getName();

        team.setUpdateBy(Long.parseLong(authAccountId));
        return teamRepository.update(team);
    }

    @Transactional(readOnly = true)
    public int delete(Long id){
        return teamRepository.delete(id);
    }

    /* 限定取得API */

    @Transactional(readOnly = true)
    public List<Team> getJoinTeams(Long id){
        // 所属しているチームのリストを取得
        return teamRepository.getJoinTeams(id);
    }

    @Transactional(readOnly = true)
    public List<Profile> getJoinMembers(String tesmCode){
        // チームメンバーのリストをプロフィールのリストで取得
        final Team team = teamRepository.get(tesmCode);
        final List<Member> members = memberRepository.getJoinMembers(team.getId());
        List<Profile> profiles = new ArrayList<>();

        for (Member member : members) {
            // accountIdからusernameを取得
            Profile profile = profileRepository.getRefAccount(member.getAccountId());
            // usernameからProfileを取得
            profile = profileRepository.get(profile.getUsername());
            profile.setId(null);
            profiles.add(profile);
        }

        return profiles;
    }

    /* leaderとログイン中ユーザが同一か確認する */
    /* return IS_SAME_USER / IS_DIFFERENT_USER */
    @Transactional(readOnly = true)
    public int confirmIsUserLeader(String code){
        // ログイン中ユーザusername取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // 指定チームのリーダーusername取得
        final Team team = teamRepository.get(code);
        final Profile profile = profileRepository.getRefAccount(team.getLeader());
        final String username = profile.getUsername();

        if( authentication.getName().equals(username)){
            return Constants.IS_SAME_USER;
        }

        return Constants.IS_DIFFERENT_USER; 
    }

    /* チームコードからteam_idを取得 */
    @Transactional(readOnly = true)
    public Long getTeamIdByCode(String code){
        return teamRepository.getTeamIdByCode(code);
    }

    /* team_idからチームコードを取得 */
    @Transactional(readOnly = true)
    public String getTeamCodeById(Long id){
        return teamRepository.getTeamCodeById(id);
    }
}
