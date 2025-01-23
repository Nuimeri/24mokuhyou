package com.circleaf.circleaf_api.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.circleaf.circleaf_api.constant.Constants;
import com.circleaf.circleaf_api.model.Invitation;
import com.circleaf.circleaf_api.model.InvitationPublish;
import com.circleaf.circleaf_api.service.InvitationService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invitations")
public class InvitationController {   
    private final InvitationService invitationService;

    @Autowired
    public InvitationController(InvitationService invitationService){
        this.invitationService = invitationService;
    }

    // sernderIdから送信した招待を取得
    @GetMapping("/sent")
    public List<InvitationPublish> getSentInvitation() {
        return invitationService.getSentInvitation();
    }

    // recipientIdが受け取った招待を取得
    @GetMapping("/received")
    public List<InvitationPublish> getReceivedInvitation() {
        return invitationService.getReceivedInvitation();
    }

    // teamcodeからチーム招待を取得
    @GetMapping("/@{teamcode}")
    public List<InvitationPublish> getInvitation(@RequestParam String teamcode) {
        return invitationService.getByTeamCode(teamcode);
    }

    
    // チーム招待を作成
    @PostMapping("/@{teamcode}/ticket")
    public  ResponseEntity<String>  ticketInvitation(@PathVariable String teamcode, @RequestBody Map<String,String> recipient) {
        // RequestBodyの中身を取得
        final String username = recipient.get("username");

        final int result = invitationService.insert(teamcode, username);
        switch (result) {
            case Constants.IS_OK_INT:
                return ResponseEntity.ok("招待を送信しました");
            case Constants.IS_NOT_EXIST_TEAM:
                return ResponseEntity.badRequest().body(teamcode + "は存在しないチームです");
            case Constants.IS_NOT_EXIST_USER:
                return ResponseEntity.badRequest().body(recipient + " は存在しないユーザーです");
            case Constants.IS_ALREADY_INVITED:
                return ResponseEntity.badRequest().body(recipient + " は既に招待済みです");
            default:
                return ResponseEntity.badRequest().body("招待の送信に失敗しました");
        }
    }

    // 招待を受ける
    @PostMapping("/accept")
    public int acceptInvitation(@RequestBody Map<String,String> ticket) {
        // RequestBodyの中身を取得
        final String ticketCode = ticket.get("ticketCode");

        return invitationService.accept(ticketCode);
    }

    // 招待を拒否する
    @PostMapping("/reject")
    public int rejectInvitation(@RequestBody Map<String,String> ticket) {
        // RequestBodyの中身を取得
        final String ticketCode = ticket.get("ticketCode");

        return invitationService.reject(ticketCode);
    }

    // 招待をキャンセルする
    @PostMapping("/cancel")
    public int cancelInvitation(@RequestBody Map<String,String> ticket) {
        // RequestBodyの中身を取得
        final String ticketCode = ticket.get("ticketCode");

        return invitationService.cancel(ticketCode);
    }    
}
