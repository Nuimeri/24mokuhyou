package com.circleaf.circleaf_api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.circleaf.circleaf_api.model.Team;

@Mapper
public interface TeamMapper {
    List<Team> findAll();
    Team get(@Param("code") String code);
    int insert(@Param("team") Team team);
    int update(@Param("team") Team team);
    int delete(@Param("id") Long id);

    /* 限定取得API */

    /* ユーザ(account_id)が参加しているチームを取得する */
    List<Team> getJoinTeams(@Param("id") Long id);

    /* teamcodeからteam_idを取得する */
    Long getTeamIdByCode(@Param("code") String code);

    /* team_idからteamcodeを取得する */
    String getTeamCodeById(@Param("id") Long id);
}
