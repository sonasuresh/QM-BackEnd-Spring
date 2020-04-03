package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.DBException;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.Roles;
import com.example.demo.service.RolesService;

@RestController
@RequestMapping("/role")
@CrossOrigin(origins = "http://localhost:4200")
public class RolesController {
	@Autowired
	private RolesService rolesService;

    	
		@GetMapping("/")
		@PreAuthorize ("hasRole('ADMIN')")
		public List<Roles> getRoles() throws ServiceException {
			return rolesService.get();
		}

//		@GetMapping("/{id}")
//		public Roles getRolesById(@PathVariable Long id) throws ServiceException {
//			return rolesService.get(id);
//
//		}
//
//		@PostMapping("/")
//		@PreAuthorize ("hasRole('ADMIN')")
//		public Roles save(@RequestBody Roles role) throws DBException {
//			rolesService.save(role);
//			return role;
//		}
//
//		@PutMapping("/")
//		@PreAuthorize ("hasRole('ADMIN')")
//		public Roles update(@RequestBody Roles role) throws DBException {
//			rolesService.save(role);
//			return role;
//
//		}
//
//		@DeleteMapping("/{id}")
//		@PreAuthorize ("hasRole('ADMIN')")
//		public String deleteRole(@PathVariable Long id) throws ServiceException {
//			rolesService.delete(id);
//			return "Role Deleted with id:" + id;
//		}



}
