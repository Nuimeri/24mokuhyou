package com.circleaf.circleaf_api.model;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;

@Data
public class Profile {
    private Long id;
    private String nickname;
    private String username;
    private String icon;
    private String description;
    private LocalDate birthday;
    private Date createAt;
    private Date updateAt;
    private Long createBy;
    private Long updateBy;
    private Long accountId;
}
