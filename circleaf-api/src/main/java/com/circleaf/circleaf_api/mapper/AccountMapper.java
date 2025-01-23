package com.circleaf.circleaf_api.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.circleaf.circleaf_api.model.Account;

import java.util.List;

@Mapper
public interface AccountMapper {
    List<Account> findAll();
    Account get(@Param("id") Long id);
    int insert(@Param("account") Account account);
    int update(@Param("account") Account account);
    int delete(@Param("id") Long id);
}
