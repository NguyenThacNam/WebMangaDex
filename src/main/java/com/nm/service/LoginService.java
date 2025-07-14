package com.nm.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



import com.nm.entity.Users;
import com.nm.repository.UserRepository;

@Service
public class LoginService implements UserDetailsService {
	
	@Autowired
	
	private UserRepository usRepo;
	
	@Override
	
	public UserDetails loadUserByUsername(String usename) throws UsernameNotFoundException {
		Users users = usRepo.findByUsername(usename);
		
		if (users == null) {
	        throw new UsernameNotFoundException("Không tìm thấy người dùng");
	    }

		return new org.springframework.security.core.userdetails.User(
			    users.getUsername(),
			    users.getPassword(),
			    Collections.singleton(new SimpleGrantedAuthority(users.getRole()))
			);

		
	}
	

}
