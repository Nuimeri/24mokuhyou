package com.circleaf.circleaf_api.model;

import lombok.Data;

@Data
public class InvitationPublish {
    private int status; // 0:未承認, 1:承認, 2:拒否 , 3:キャンセル
    
    // 以下、テーブルには存在しないが、表示用に使用する
    private String code;
    private String teamcode;
    private String senderName;
    private String recipientName;
}
