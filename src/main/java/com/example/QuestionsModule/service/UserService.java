package com.example.QuestionsModule.service;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.QuestionsModule.controller.Authentication;
import com.example.QuestionsModule.dao.UserRepository;
import com.example.QuestionsModule.dao.UserRoleDAO;
import com.example.QuestionsModule.dto.AddUserInfo;
import com.example.QuestionsModule.dto.EditUserInfo;
import com.example.QuestionsModule.exception.DBException;
import com.example.QuestionsModule.exception.ServiceException;
import com.example.QuestionsModule.model.AuthenticationRequest;
import com.example.QuestionsModule.model.Category;
import com.example.QuestionsModule.model.Types;
import com.example.QuestionsModule.model.User;
import com.example.QuestionsModule.model.User_Role;

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

	public List<User> getAllUsers() throws ServiceException {
		List<User> users;
		try {
			users = usersDAO.findAllUsers();
			return users;

		} catch (DBException e) {
			throw new ServiceException("Unable to fetch users");

		}
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
		String randomPassword = RandomStringUtils.randomAlphanumeric(10);
		user.setUserName(randomUserName);
		user.setPassword(BCrypt.hashpw(randomPassword, BCrypt.gensalt()));
		users = usersDAO.save(user);
		int userId = (int) user.getId();
		User_Role user_role = new User_Role();

		user_role.setUser_Id(userId);
		List<Integer> roleId = addUserInfo.getRoleId();
		int length = roleId.size();
		for (int i = 0; i < length; i++) {
			user_role.setRole_Id((int) roleId.get(i));
			System.out.print(roleId.get(i));
			userRoleDAO.saveInto(userId, (int) roleId.get(i));
		}
		authRequest.setUsername(randomUserName);
		authRequest.setPassword(randomPassword);
		return authRequest;

	}

}
