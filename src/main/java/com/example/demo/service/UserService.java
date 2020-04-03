package com.example.demo.service;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.controller.Authentication;
import com.example.demo.dao.UserRepository;
import com.example.demo.dao.UserRoleDAO;
import com.example.demo.dto.AddUserInfo;
import com.example.demo.dto.EditUserInfo;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.AuthenticationRequest;
import com.example.demo.model.Category;
import com.example.demo.model.Types;
import com.example.demo.model.User;
import com.example.demo.model.User_Role;

@Service
public class UserService {
	@Autowired
	UserRepository usersDAO;
	@Autowired
	UserRoleDAO userRoleDAO;
	@Autowired
	Authentication authentication;
	@Autowired
	AuthenticationRequest authRequest;

//	@Autowired
//	 private BCryptPasswordEncoder bCryptPasswordEncoder;
	public List<User> getAllUsers() throws ServiceException {
		List<User> users;
		users = usersDAO.findAllUsers();
		if (users == null) {
			throw new ServiceException("Unable to fetch users");
		}
		return users;
	}

	public String editUser(EditUserInfo editUserInfo) throws ServiceException {
		try {
			User user = new User();
			user.setUsername(editUserInfo.getName());
			user.setPassword(BCrypt.hashpw(editUserInfo.getPassword(), BCrypt.gensalt()));
			if (usersDAO.exists(editUserInfo.getEmail()) != null) {
				usersDAO.updateDetails(editUserInfo.getName(),
						BCrypt.hashpw(editUserInfo.getPassword(), BCrypt.gensalt()), editUserInfo.getEmail());
				return "Updated Successfully";
			} else {
				return "email does not exist";
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("Unable to update User Details");
		}

	}
	

	public AuthenticationRequest addUser(AddUserInfo addUserInfo) throws Exception {
		User users = new User();
		User user = new User();
		user.setEmail(addUserInfo.getEmail());
		user.setIsactive(true);
		String randomUserName = RandomStringUtils.randomAlphanumeric(10);
		String randomPassword= RandomStringUtils.randomAlphanumeric(10);
		user.setUserName(randomUserName);
		user.setPassword(BCrypt.hashpw(randomPassword, BCrypt.gensalt()));
		users = usersDAO.save(user);
		int userId = (int) user.getId();
		User_Role user_role = new User_Role();

		user_role.setUser_Id(userId);
		List<Integer> roleId = addUserInfo.getRoleId();
		int length = roleId.size();
		for (int i = 0; i < length; i++) {
			user_role.setRole_Id((int)roleId.get(i));
			System.out.print(roleId.get(i));
			//userRoleDAO.save(user_role);
			userRoleDAO.saveInto(userId,(int)roleId.get(i));
		}
		//Authentication authentication=new Authentication();
//		System.out.print(randomUserName);
//		System.out.print("pass"+ randomPassword );
		authRequest.setUsername(randomUserName);
		authRequest.setPassword(randomPassword);
		//return authentication.createAuthenticateToken(randomUserName.toString(),randomPassword.toString());
		return authRequest;
//		if (users == null) {
//			throw new ServiceException("Unable to add user");
//		}
		//return users;
	}
//public boolean login(AddUserInfo addUserInfo)throws ServiceException{
//	Users users=new Users();
//	String name=addUserInfo.getName();
//	String hashedPassword = usersDAO.getHashedPassword(name);
//	String password=addUserInfo.getPassword();
//	if (BCrypt.checkpw(password, hashedPassword)) {
//		return true;
//	}
//	else {
//		return false;
//	}
//
//	}

}
