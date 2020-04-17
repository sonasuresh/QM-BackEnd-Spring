package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.UserRepository;
import com.example.demo.model.AuthenticationRequest;
import com.example.demo.model.AuthenticationResponse;
import com.example.demo.util.JwtUtil;

import java.util.HashMap;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class Authentication {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	UserRepository usersDAO;

	@PostMapping({ "/authenticate" })
	public ResponseEntity<?> splitAuthRequest(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		AuthenticationRequest authRequest = new AuthenticationRequest();
		authRequest.setUsername(authenticationRequest.getUsername());
		authRequest.setPassword(authenticationRequest.getPassword());
		return createAuthenticateToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
	}

	public ResponseEntity<?> createAuthenticateToken(String username, String rawpassword) throws Exception {
		try {

//			String name=username.toString();
//			String raw=rawpassword.toString();
			System.out.println("name" + username);
			String hashedPassword = usersDAO.getHashedPassword(username);
			System.out.println("pass" + rawpassword);
			if (hashedPassword != null) {
				String password = rawpassword;

				if (BCrypt.checkpw(password, hashedPassword)) {
					System.out.print("hello");
					String hashedPassword1 = hashedPassword;

					authenticationManager
							.authenticate(new UsernamePasswordAuthenticationToken(username, hashedPassword));
				}

				else {
					throw new Exception("Incorrect Password");

				}
			} else {
				throw new Exception("User Name Not Found!");
			}

		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect Username and Password", e);

		}
		// System.out.println("hello1");
		final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		
		final String jwt = jwtUtil.generateToken(userDetails);
		// System.out.println("hello3");
//		Map<String, Object> result = new HashMap<String,Object>();
//		  result.put("username",username);
//		  result.put("password",rawpassword);
//		  result.put("jwt",new AuthenticationResponse(jwt));
//		return ResponseEntity.ok(result);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));

	}

}
