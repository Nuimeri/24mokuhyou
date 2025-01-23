package com.circleaf.circleaf_api.model;

import java.util.Date;

import lombok.Data;

@Data
public class Member {
    private Long id;
    private Long teamId;
    private Long accountId;
    private Date createAt;
    private Date updateAt;
    private Long createBy;
    private Long updateBy;
    private boolean isDeleted;
}
