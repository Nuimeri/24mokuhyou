package com.circleaf.circleaf_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.circleaf.circleaf_api.model.Post;
import com.circleaf.circleaf_api.model.Profile;
import com.circleaf.circleaf_api.service.PostService;
import com.circleaf.circleaf_api.service.ProfileService;

import java.util.List;

import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final ProfileService profileService;

    @Autowired
    public PostController(PostService postService,ProfileService profileService){
        this.postService = postService;
        this.profileService = profileService;
    }

    @GetMapping
    public List<Post> getPostAll() {
        return postService.findAll();
    }
    

    @GetMapping("/{code}")
    public Post getPost(@PathVariable String code){
        Post post = postService.get(code);
        Long accountId = post.getAccountId();
        Profile profile = profileService.getRefAccount(accountId);
        
        // 公開情報、非公開情報を整理
        post.setUsername(profile.getUsername());
        post.setAccountId(null);
        post.setId(null);

        return post;
    }

    @PostMapping("/post/new")
    public int newPost(@RequestBody Post post){
        return postService.insert(post);
    }

    // @PostMapping("/{code}/edit")
    public Post updatePost(@PathVariable String code, @RequestBody Post post) {   
        Post srcPost = postService.get(code);
        post.setId(srcPost.getId());

        postService.update(post);
        return post;
    }

    @PutMapping("/{code}/delete")
    public String deletePost(@PathVariable String code) {
        Post post = postService.get(code);
        int res = postService.delete(post.getId());
        
        String str = "post deleted";
        if (res != 1) {
            str = "failed";
        }
        return str;
    }
}
