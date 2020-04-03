package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.RolesDAO;
import com.example.demo.exception.DBException;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.Level;
import com.example.demo.model.Roles;

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
