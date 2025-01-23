package com.circleaf.circleaf_api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.circleaf.circleaf_api.model.Post;
import com.circleaf.circleaf_api.repository.PostRepository;
import com.circleaf.circleaf_api.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ProfileRepository profileRepository;

    @Transactional(readOnly = true)
    public List<Post> findAll(){
        return postRepository.findAll();
    }

    @Transactional(readOnly = false)
    public int insert(Post post){
        return postRepository.insert(post);
    }

    @Transactional(readOnly = true)
    public Post get(String code){
        return postRepository.get(code);
    }

    @Transactional(readOnly = false)
    public int update(Post post){
        return postRepository.update(post);
    }

    @Transactional(readOnly = true)
    public int delete(Long id){
        return postRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public String getAttentionPost(String username){
    /* 固定投稿か最新投稿を取得する。 */
        final Long accountId = profileRepository.getAccount(username);
        final String anchoredPostCode = postRepository.getAnchoredPost(accountId);
        
        // 固定された投稿があればそれを返却
        if(anchoredPostCode != null){
            return anchoredPostCode;
        }

        // 固定がなければ最新投稿を返却
        return postRepository.getLatestPost(accountId);
    }
}
