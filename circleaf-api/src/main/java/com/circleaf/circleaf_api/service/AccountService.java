package com.circleaf.circleaf_api.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.circleaf.circleaf_api.constant.Constants;
import com.circleaf.circleaf_api.model.Account;
import com.circleaf.circleaf_api.model.Profile;
import com.circleaf.circleaf_api.repository.AccountRepository;
import com.circleaf.circleaf_api.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final ProfileRepository profileRepository;

    static int expectedDeletedRecords = 2;

    @Transactional(readOnly = true)
    public List<Account> findAll(){
        return accountRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Account get(Long id){
        return accountRepository.get(id);
    }

    @Transactional(readOnly = false)
    public int insert(Account account){
        return accountRepository.insert(account);
    }

    @Transactional(readOnly = false)
    public int delete(Long id){
        // アカウント削除時はプロフィールも削除する
        // 実装途中
        int res = 0;
        boolean is_failed= false;

        final int result_del_profile = profileRepository.delete(id);
        final int result_del_account = accountRepository.delete(id);

        is_failed = (1 != result_del_account);
        is_failed = (1 != result_del_profile);

        if(is_failed){
        // 削除数が想定数以外ならロールバック
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            res = Constants.IS_ERR_INT;
        }else{
            res = res + result_del_account;
            res = res + result_del_profile;
        }
        
        return Constants.IS_OK_INT;
    }

    //signup
    @Transactional(readOnly = false)
    public String signupAccount(Account account){
        // Account,Profile 作成
        if(accountRepository.insert(account) != 1){
            // 失敗時ロールバックしてnull返却
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return null;
        }

        if(profileRepository.signup(account.getId()) == null ){
            // 失敗時ロールバックしてnull返却
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return null;
        }

        // 作成されたProfile取得
        final Profile profile = profileRepository.getRefAccount((long)account.getId());

        // username返却
        return profile.getUsername();
    }


    /* usernameとログイン中ユーザが同一か確認する */
    /* return IS_SAME_USER / IS_DIFFERENT_USER  */
    public int confirmLoginUser(String username){
        // ログイン中ユーザのaccount_idを取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String authAccountId = authentication.getName();
        
        // usernameのaccount_idを取得
        final Long userAccountId = profileRepository.getAccount(username);

        if( authAccountId.equals(userAccountId.toString()) ){
            return Constants.IS_SAME_USER;
        }

        return Constants.IS_DIFFERENT_USER;
    }

}
