package com.circleaf.circleaf_api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.circleaf.circleaf_api.model.Invitation;

@Mapper
public interface InvitationMapper {
    List<Invitation> getByTeamCode(@Param("team_id") String teamId);
    int insert(@Param("invitation") Invitation teamInvitation);
    int update(@Param("invitation") Invitation teamInvitation);

    // 招待コードから招待情報を取得
    Invitation getByCode(@Param("code") String code);

    // accountへの招待を取得
    List<Invitation> getReceivedInvitation(@Param("recipient") Long recipient);

    // 作成した招待を取得
    List<Invitation> getSentInvitation(@Param("sender") Long sender);
}