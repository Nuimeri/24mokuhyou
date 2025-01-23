package com.circleaf.circleaf_api.controller;

import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
// import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.circleaf.circleaf_api.constant.Constants;
import com.circleaf.circleaf_api.model.Account;
import com.circleaf.circleaf_api.model.Profile;
import com.circleaf.circleaf_api.service.AccountService;
import com.circleaf.circleaf_api.service.ProfileService;

import jakarta.validation.Valid;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
// @RequestMapping("/accounts")
public class AccountController {
   private final AccountService accountService;
   private final PasswordEncoder passwordEncoder;
   private final ProfileService profileService;

    @Autowired
    public AccountController(AccountService accountService,PasswordEncoder passwordEncoder,ProfileService profileService) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
        this.profileService = profileService;
    }

    @GetMapping("/account/all")
   public List<Account> readAccount(){
        return accountService.findAll();
   } 

//    @GetMapping("/{id}")
//    public Account getAccount(@PathVariable Long id) {
//         Account account = accountService.get(id);
//        return account;
//    }

   @PostMapping("/signup")
   public ResponseEntity<String> createAccount(@Valid @RequestBody Account account,BindingResult bindingResult) {
       
       if(bindingResult.hasErrors()){
        // バリデーションを満たせなかったらエラーを返す 
        // Htttp Status : 400 , json { "status" , "mail" , "password" }
            return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(),HttpStatus.BAD_REQUEST) ;
        }

        // passwordをハッシュ化
        String encodedPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(encodedPassword);

        // Account,Profileを作成し仮usernameを取得
        final String username = accountService.signupAccount(account);

        // Http Status : 200 , body {username}
        return new ResponseEntity<>(username,HttpStatus.OK);
   }
   
    @GetMapping("/isAuthenticated")
    public ResponseEntity<?> authenticate(Authentication authentication) {
        // 認証オブジェクトを確認し、認証状態を返す
        if (authentication != null && authentication.isAuthenticated()) {
            // 認証に成功した場合、200 OKレスポンスを返し、メッセージを送信
            return ResponseEntity.ok("Authenticated");
        } else {
            // 認証に失敗した場合、401 Unauthorizedレスポンスを返し、メッセージを送信
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }

    @GetMapping("/isLogin")
    public String getLoginStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getName() != "anonymousUser" ){
            final Long accountId =  Long.parseLong(authentication.getName());
            final Profile profile =  profileService.getRefAccount(accountId);
            final String username = profile.getUsername();
            return username;
        }

        return null;
    }
    
    
}
