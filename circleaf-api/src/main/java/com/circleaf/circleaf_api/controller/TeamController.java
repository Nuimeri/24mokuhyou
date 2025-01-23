package com.circleaf.circleaf_api.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.circleaf.circleaf_api.model.Profile;
import com.circleaf.circleaf_api.model.Team;
import com.circleaf.circleaf_api.service.TeamService;

@RestController
@RequestMapping("/teams")
public class TeamController {
    
    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService){
        this.teamService = teamService;
    }

    @GetMapping("/{code}")
    public Team getTeam(@PathVariable String code){
        Team team = teamService.get(code);
        team.setId(null);
        return team;
    }

    @GetMapping("/{code}/isLeader")
    public int confirmIsUserLeader(@PathVariable String code) {
        return teamService.confirmIsUserLeader(code);
    }

    @PostMapping("/create")
    public int newTeam(@RequestBody Team team){
        return teamService.insert(team);
    }

    @PostMapping("/{code}/edit")
    public Team updateTeam(@PathVariable String code,@RequestBody Team team) { 
        Team srcTeam = teamService.get(code);
        team.setId(srcTeam.getId());

        teamService.update(team);
        return team;
    }

    /* メンバー管理 */

    // 所属しているメンバーのusernameリストを取得
    @GetMapping("/{code}/members")
    public List<Profile> getJoinMembers(@PathVariable String code) {
        if(code == null){
        // code未指定の場合は空リストを返す
            return Collections.emptyList();
        }

        final List<Profile> members = teamService.getJoinMembers(code);
        return members;
    }

}
