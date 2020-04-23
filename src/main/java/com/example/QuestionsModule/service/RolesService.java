package com.example.QuestionsModule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.QuestionsModule.dao.RolesDAO;
import com.example.QuestionsModule.exception.DBException;
import com.example.QuestionsModule.exception.ServiceException;
import com.example.QuestionsModule.model.Level;
import com.example.QuestionsModule.model.Roles;

@Service
public class RolesService {
	@Autowired
	RolesDAO rolesDAO;
	public List<Roles> get() throws ServiceException{
		List<Roles> roles;
		roles = rolesDAO.findAll();
		if (roles == null) {
			throw new ServiceException("Unable to fetch roles");
		}
		return roles;
	}
	
}
