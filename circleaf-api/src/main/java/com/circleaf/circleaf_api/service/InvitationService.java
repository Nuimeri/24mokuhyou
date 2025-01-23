package com.circleaf.circleaf_api.service;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.circleaf.circleaf_api.constant.Constants;
import com.circleaf.circleaf_api.model.Invitation;
import com.circleaf.circleaf_api.model.InvitationPublish;
import com.circleaf.circleaf_api.model.Member;
import com.circleaf.circleaf_api.repository.InvitationRepository;
import com.circleaf.circleaf_api.repository.MemberRepository;
import com.circleaf.circleaf_api.repository.TeamRepository;
import com.circleaf.circleaf_api.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvitationService {   
    private final InvitationRepository invitationRepository;
    private final TeamRepository teamRepository;
    private final ProfileRepository profileRepository;
    private final MemberRepository memberRepository;
    
    // teamcodeからチーム招待を取得
    @Transactional(readOnly = true)
    public List<InvitationPublish> getByTeamCode(String teamcode) {
        final List<Invitation> invitations = invitationRepository.getByTeamCode(teamcode);
        
        // InvitationPublishのリストを作成
        List<InvitationPublish> invitationPublishes = new ArrayList<>();

        // 公開用に編集
        for (Invitation invitation : invitations) {
            // invitationPublishに変換
            final InvitationPublish invitationPublish = editForPublication(invitation);
            invitationPublishes.add(invitationPublish);
        }

        return invitationPublishes;
    }

    // チーム招待を作成
    @Transactional(readOnly = false)
    public int insert(String teamcode, String recipient) {

        // ログイン中のユーザのaccount_idを取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String authAccountId = authentication.getName();
        final Long senderId = Long.parseLong(authAccountId);
        
        
        // teamcodeのteam_idを取得
        Long teamId = teamRepository.getTeamIdByCode(teamcode);

        // teamcodeのteam_idが存在しない場合はnullを返却
        if(teamId == null){
            return Constants.IS_NOT_EXIST_TEAM;
        }
        
        // recipientのaccount_idを取得
        Long recipientId = profileRepository.getAccount(recipient);
        
        // recipientのaccount_idが存在しない場合はnullを返却
        if(recipientId == null){
            return Constants.IS_NOT_EXIST_USER;
        }
        
        // 招待を作成
        Invitation invitation = new Invitation();
        invitation.setTeamId(teamId);
        invitation.setRecipient(recipientId);
        invitation.setSender(senderId);
        invitation.setStatus(Constants.INVITATION_UNAPPROVED);
        invitation.setCreateBy(senderId);
        invitation.setUpdateBy(senderId);
        
        // UUID:BINARY(16)を作成
        UUID uuid = UUID.randomUUID();

        // UUIDをバイト配列に変換
        byte[] uuidBytes = ByteBuffer.allocate(16)
        .putLong(uuid.getMostSignificantBits())
        .putLong(uuid.getLeastSignificantBits())
        .array();
        
        // UUIDを設定
        invitation.setUuid(uuidBytes);

        return invitationRepository.insert(invitation);
    }

    // 招待を承認
    @Transactional(readOnly = false)
    public int accept(String code) {
        final int updRes = update(code, Constants.INVITATION_APPROVED);
        // 招待を承認した場合は、招待されたユーザーをチームに追加
        if(updRes != Constants.IS_OK_INT){
            return updRes;
        }

        // 招待を取得
        Invitation invitation = invitationRepository.getByCode(code.toString());
        // Memberを作成
        final Member member = new Member();
        member.setTeamId(invitation.getTeamId());
        member.setAccountId(invitation.getRecipient());
        member.setCreateBy(invitation.getRecipient());
        member.setUpdateBy(invitation.getRecipient());
        // Memberを作成
        final int memberRes = memberRepository.insert(member);

        return memberRes;
    }

    // 招待を拒否
    @Transactional(readOnly = false)
    public int reject(String code) {
        return update(code, Constants.INVITATION_REJECTED);
    }

    // 招待をキャンセル
    @Transactional(readOnly = false)
    public int cancel(String code) {
        return update(code, Constants.INVITATION_CANCELED);
    }

    // 招待を更新
    @Transactional(readOnly = false)
    public int update(String code, int status) {
        // recipientがログイン中のユーザーのaccount_idと一致するか確認
        if(!isRecipient(code)){
            return Constants.IS_ERR_INT;
        }

        // ログイン中のユーザのaccount_idを取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String authAccountId = authentication.getName();

        // 招待を更新
        Invitation invitation = new Invitation();
        invitation.setCode(code);
        invitation.setStatus(status);
        invitation.setUpdateBy(Long.parseLong(authAccountId));

        return invitationRepository.update(invitation);
    }

    // 受け取った招待を取得
    @Transactional(readOnly = true)
    public List<InvitationPublish> getReceivedInvitation() {
        // ログイン中のユーザーのaccount_idを取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String authAccountId = authentication.getName();
        final Long recipient = Long.parseLong(authAccountId);

        final List<Invitation> invitations = invitationRepository.getReceivedInvitation(recipient);

        // InvitationPublishのリストを作成
        List<InvitationPublish> invitationPublishes = new ArrayList<>();

        // 公開用に編集
        for (Invitation invitation : invitations) {
            // invitationPublishに変換
            final InvitationPublish invitationPublish = editForPublication(invitation);
            invitationPublishes.add(invitationPublish);
        }

        return invitationPublishes;
    }

    // 送った招待を取得
    @Transactional(readOnly = true)
    public List<InvitationPublish> getSentInvitation() {
        // ログイン中のユーザーのaccount_idを取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String authAccountId = authentication.getName();
        final Long sender = Long.parseLong(authAccountId);

        final List<Invitation> invitations = invitationRepository.getSentInvitation(sender);
        // InvitationPublishのリストを作成
        List<InvitationPublish> invitationPublishes = new ArrayList<>();

        // 公開用に編集
        for (Invitation invitation : invitations) {
            // invitationPublishに変換
            final InvitationPublish invitationPublish = editForPublication(invitation);
            invitationPublishes.add(invitationPublish);
        }

        return invitationPublishes;
    }

    // 招待のrecipientがログイン中のユーザーのaccount_idと一致するか確認
    @Transactional(readOnly = true)
    public boolean isRecipient(String code) {
        // ログイン中のユーザーのaccount_idを取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String authAccountId = authentication.getName();
        final Long accountId = Long.parseLong(authAccountId);

        // 招待を取得
        Invitation invitation = invitationRepository.getByCode(code.toString());
        // 招待が存在しない場合はfalseを返却
        if(invitation == null){
            return false;
        }

        // 招待のrecipientがログイン中のユーザーのaccount_idと一致するか確認
        return invitation.getRecipient() == accountId;
    }

    // Invitationを公開用に編集
    @Transactional(readOnly = true)
    public InvitationPublish editForPublication(Invitation invitation) {
        // uuidを文字列に変換
        final String code = invitation.getCode().toString();
        // team_idからteamのcodeを取得
        final String teamCode = teamRepository.getTeamCodeById(invitation.getTeamId());
        // senderIdからsenderのusernameを取得
        final String senderName = profileRepository.getRefAccount(invitation.getSender()).getUsername();
        // recipientIdからrecipientのusernameを取得
        final String recipientName = profileRepository.getRefAccount(invitation.getRecipient()).getUsername();

        // invitationPublishに変換
        InvitationPublish invitationPublish = new InvitationPublish();
        invitationPublish.setCode(code);
        invitationPublish.setStatus(invitation.getStatus());
        invitationPublish.setTeamcode(teamCode);
        invitationPublish.setSenderName(senderName);
        invitationPublish.setRecipientName(recipientName);

        return invitationPublish;
    }
}
