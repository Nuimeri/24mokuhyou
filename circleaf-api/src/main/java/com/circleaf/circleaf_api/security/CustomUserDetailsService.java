package com.circleaf.circleaf_api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.circleaf.circleaf_api.model.Account;
import com.circleaf.circleaf_api.repository.AccountRepository;
import com.circleaf.circleaf_api.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final AccountRepository accountRepository;
	private final ProfileRepository profileRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Long accountId = profileRepository.getAccount(username);
		Account account = accountRepository.get(Long.valueOf(accountId));
		if (account == null) {
			throw new UsernameNotFoundException("not found : " + username);
		}
		return (new CustomUserDetails(account));
	}
}
