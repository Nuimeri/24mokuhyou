package com.circleaf.circleaf_api.model;

import java.sql.Date;

import lombok.Data;

@Data
public class Team {
    private Long id;
    private String code;
    private String name;
    private String category;
    private Long founder;
    private Long leader;
    private String icon;
    private String music;
    private String description;
    private Date createAt; 
    private Long createBy;
    private Date updateAt; 
    private Long updateBy;
    private boolean isDeleted;
}
