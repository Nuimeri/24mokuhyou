package com.circleaf.circleaf_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.circleaf.circleaf_api.constant.Constants;
import com.circleaf.circleaf_api.model.Profile;
import com.circleaf.circleaf_api.model.Team;
import com.circleaf.circleaf_api.service.AccountService;
import com.circleaf.circleaf_api.service.PostService;
import com.circleaf.circleaf_api.service.ProfileService;
import com.circleaf.circleaf_api.service.TeamService;

import java.util.List;

@RestController
@RequestMapping
public class ProfileController {
    private final ProfileService profileService;
    private final TeamService teamService;
    private final AccountService accountService;
    private final PostService postService;

    @Autowired
    public ProfileController(ProfileService profileService,TeamService teamService,AccountService accountService,PostService postService){
        this.profileService = profileService;
        this.teamService = teamService;
        this.accountService = accountService;
        this.postService = postService;
    }

    @GetMapping("/profiles")
    public List<Profile> getProfileAll() {
        return profileService.findAll();
    }
    

    @GetMapping("/{username}")
    public Profile getProfile(@PathVariable String username){
        Profile profile = profileService.get(username);
        return profile;
    }

    @GetMapping("/{username}/is-self")
    public int confirmIsSelf(@PathVariable String username) {
        // ログインユーザによるアクセスであるか確認
        return accountService.confirmLoginUser(username);
    }

    @PostMapping("/profile/new")
    public int newProfile(@RequestBody Profile profile){
        return profileService.insert(profile);
    }

    @PostMapping("/{username}/edit")
    public int updateProfile(@PathVariable String username, @RequestBody Profile profile) {   
        // ログインユーザによるアクセスであるか確認
        int isSelf = accountService.confirmLoginUser(username);

        if(isSelf == Constants.IS_DIFFERENT_USER){
        // ログイン中ユーザと編集対象ユーザが違ったら失敗
            return Constants.IS_DIFFERENT_USER;
        }

        Profile srcProfile = profileService.get(username);
        profile.setId(srcProfile.getId());

        final int res = profileService.update(profile);
        return res;
    }

    /* 他テーブル情報取得 */
    
    // 所属チーム取得 : List<Team> 
    @GetMapping("/{username}/teams")
    public List<Team> getJoinTeams(@PathVariable String username) {
        Profile srcProfile = profileService.get(username);
        Long id = srcProfile.getId();
        
        return teamService.getJoinTeams(id);
    }    

    // フォロー関係取得 : List<account_id>

    // 連携サイト取得 : List<web_id>

    // 紹介ユーザ取得 : List<account_id>

    // リアクション総数取得 : Int

    // 固定投稿または最新投稿を取得 : Post
    @GetMapping("/{username}/attention")
    public String getAttentionPost(@PathVariable String username) {
        final String postcode = postService.getAttentionPost(username);
        
        if(postcode == null){
            return "";
        }
        return postcode;
    }
    
}
