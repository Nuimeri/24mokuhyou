package com.circleaf.circleaf_api.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.circleaf.circleaf_api.mapper.TeamMapper;
import com.circleaf.circleaf_api.model.Team;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TeamRepository {
    
    private final TeamMapper teamMapper;

    public List<Team> findAll() {
        return teamMapper.findAll();
    }

    public Team get(String code) {
        return teamMapper.get(code);
    }

    public int insert(Team team) {
        return teamMapper.insert(team);
    }

    public int update(Team team) {
        
        return teamMapper.update(team);
    }

    public int delete(Long id) {
        return teamMapper.delete(id);
    }

    /* 限定取得API */

    public List<Team> getJoinTeams(Long id){
        return teamMapper.getJoinTeams(id);
    }

    /* teamcodeからteam_idを取得 */
    public Long getTeamIdByCode(String code){
        return teamMapper.getTeamIdByCode(code);
    }

    /* team_idからteamcodeを取得 */
    public String getTeamCodeById(Long id){
        return teamMapper.getTeamCodeById(id);
    }
}
