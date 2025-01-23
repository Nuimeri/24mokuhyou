package com.circleaf.circleaf_api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.circleaf.circleaf_api.model.Profile;

@Mapper
public interface ProfileMapper {   
    List<Profile> findAll();
    Profile get(@Param("username") String username);
    int insert(@Param("profile") Profile profile);
    int update(@Param("profile") Profile profile);
    int delete(@Param("id") Long id);

    // AccountとProfileの紐付け
    Profile getRefAccount(@Param("accountId") Long accountId);
    // AccountId取得
    Long getAccount(@Param("username") String username);
    // signup時の仮プロフィール作成
    void signup(@Param("profile") Profile profile, @Param("accountId") Long accountId);
}
