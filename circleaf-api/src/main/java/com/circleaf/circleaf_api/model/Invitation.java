package com.circleaf.circleaf_api.model;

import java.sql.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class Invitation {
    private Long id;
    private Long teamId;
    private String code;
    private Long sender; // 招待した人 : account_id
    private Long recipient; // 招待された人 : account_id
    private int status; // 0:未承認, 1:承認, 2:拒否 , 3:キャンセル
    private Date createAt;
    private Long createBy;
    private Date updateAt;
    private Long updateBy;

    // 以下、テーブルには存在しないが、登録時のUUIDを指定するために使用する
    private byte[] uuid;
}
