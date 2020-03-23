package com.example.demo.service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UsersDAO;
import com.example.demo.dto.AddUserInfo;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.Category;
import com.example.demo.model.Types;
import com.example.demo.model.Users;

@Service
public class UserService  {
	@Autowired
	UsersDAO usersDAO;
	@Autowired
	  // private BCryptPasswordEncoder bCryptPasswordEncoder;
	public List<Users> getAllUsers()throws ServiceException{
	List<Users> users;
	users = usersDAO.findAll();
	if (users == null) {
		throw new ServiceException("Unable to fetch users");
	}
	return users;
	}
	
public Users addUser(AddUserInfo addUserInfo)throws ServiceException{
	Users users=new Users();
	Users user=new Users();
	user.setName(addUserInfo.getName());
	
	String password=addUserInfo.getPassword();	
	user.setPassword(BCrypt.hashpw(addUserInfo.getPassword(), BCrypt.gensalt()));
	users = usersDAO.save(user);
	if (users == null) {
		throw new ServiceException("Unable to add user");
	}
	return users;
	}
public boolean login(AddUserInfo addUserInfo)throws ServiceException{
	Users users=new Users();
	String name=addUserInfo.getName();
	String hashedPassword = usersDAO.getHashedPassword(name);
	String password=addUserInfo.getPassword();
	if (BCrypt.checkpw(password, hashedPassword)) {
		return true;
	}
	else {
		return false;
	}

	}

}
