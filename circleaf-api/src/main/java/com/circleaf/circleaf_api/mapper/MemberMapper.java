package com.circleaf.circleaf_api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.circleaf.circleaf_api.model.Member;

public interface MemberMapper {
    List<Member> findAll();
    Member get(@Param("id") Long id);
    int insert(@Param("member") Member member);
    int update(@Param("member") Member member);
    int delete(@Param("id") Long id);

    /* 限定取得API */

    // チーム(teamId)に参加しているメンバーを取得する
    List<Member> getJoinMembers(@Param("teamId") Long teamId);

}
