package com.circleaf.circleaf_api.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.circleaf.circleaf_api.mapper.AccountMapper;
import com.circleaf.circleaf_api.model.Account;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AccountRepository {
    
    private final AccountMapper accountMapper;

    public List<Account> findAll() {
        return accountMapper.findAll();
    }

    public Account get(Long id) {
        return accountMapper.get(id);
    }

    public int insert(Account account) {
        return accountMapper.insert(account);
    }

    public int update(Account account) {
        return accountMapper.update(account);
    }

    public int delete(Long id) {
        return accountMapper.delete(id);
    }
}
