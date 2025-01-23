package com.circleaf.circleaf_api.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.circleaf.circleaf_api.mapper.InvitationMapper;
import com.circleaf.circleaf_api.model.Invitation;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class InvitationRepository {
    private final InvitationMapper invitationMapper;

    public List<Invitation> getByTeamCode(String code) {
        return invitationMapper.getByTeamCode(code);
    }

    public int insert(Invitation invitation) {
        return invitationMapper.insert(invitation);
    }

    public int update(Invitation invitation) {
        return invitationMapper.update(invitation);
    }

    public Invitation getByCode(String code) {
        return invitationMapper.getByCode(code);
    }

    public List<Invitation> getReceivedInvitation(Long recipient) {
        return invitationMapper.getReceivedInvitation(recipient);
    }

    public List<Invitation> getSentInvitation(Long sender) {
        return invitationMapper.getSentInvitation(sender);
    }  
}
