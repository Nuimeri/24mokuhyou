package com.circleaf.circleaf_api.model;

import java.sql.Date;

import lombok.Data;

@Data
public class Post {
    private Long id;
    private String code;
    private Long accountId;
    private String postText;
    private String image;
    private Date createAt;
    private Long createBy;
    private Date updateAt;
    private Long updateBy;

    // DBのテーブルにはない
    private String username;
}
