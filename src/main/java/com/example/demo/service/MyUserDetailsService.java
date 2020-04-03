package com.example.demo.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.MyUserDetails;
import com.example.demo.model.User;
import com.example.demo.dao.UserRepository;

import org.springframework.security.core.userdetails.UserDetailsService;
@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
	
		Optional<User> user=userRepository.findByUserName(userName); 
		user.orElseThrow(()-> new UsernameNotFoundException("Not Found"+ userName));
	return	user.map(MyUserDetails::new).get();
	}
}
