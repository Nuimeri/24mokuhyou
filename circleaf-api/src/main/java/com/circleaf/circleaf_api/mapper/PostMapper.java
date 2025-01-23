package com.circleaf.circleaf_api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.circleaf.circleaf_api.model.Post;

@Mapper
public interface PostMapper {    
    List<Post> findAll();
    Post get(@Param("code") String code);
    int insert(@Param("post") Post post);
    int update(@Param("post") Post post);
    int delete(@Param("id") Long id);

    // 最新投稿を取得
    String getLatestPost(@Param("accountId") Long accountId);

    // 固定投稿を取得
    String getAnchoredPost(@Param("accountId") Long accountId);
}
