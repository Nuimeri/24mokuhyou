package com.circleaf.circleaf_api.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.circleaf.circleaf_api.mapper.PostMapper;
import com.circleaf.circleaf_api.model.Post;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    
    private final PostMapper postMapper;

    public List<Post> findAll() {
        return postMapper.findAll();
    }

    public Post get(String code) {
        return postMapper.get(code);
    }

    public int insert(Post post) {
        return postMapper.insert(post);
    }

    public int update(Post post) {
        return postMapper.update(post);
    }

    public int delete(Long id) {
        return postMapper.delete(id);
    }


    // 最新投稿を取得
    public String getLatestPost(Long accountId){
        return postMapper.getLatestPost(accountId);
    }

    // 固定投稿を取得
    public String getAnchoredPost(Long accountId){
        return postMapper.getAnchoredPost(accountId);
    }
}
