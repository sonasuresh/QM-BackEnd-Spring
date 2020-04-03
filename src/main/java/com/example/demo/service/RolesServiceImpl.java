//package com.example.demo.service;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.demo.exception.DBException;
//import com.example.demo.exception.ServiceException;
//import com.example.demo.dao.RolesDAO;
//import com.example.demo.model.Roles;
//import com.example.demo.util.RolesMessage;
//
//import org.springframework.transaction.annotation.Transactional;
//@Service
//public class RolesServiceImpl  implements RolesService {
//	@Autowired
//	private RolesDAO rolesDao;
//	
//	@Transactional
//	@Override
//	public List<Roles> get() throws ServiceException {
//		List<Roles> list=new ArrayList<Roles>();
//		try {
//			list=rolesDao.get();
//			if(list.isEmpty()) {
//				throw new ServiceException(RolesMessage.NO_RECORD);
//			}
//		}catch(DBException e) {
//			System.out.println(e.getMessage());
//			
//		}
//		return list;
//	}
//	@Transactional
//	@Override
//	public Roles get(Long id) throws ServiceException {
//		Roles role = new Roles();
//		try {
//			role = rolesDao.get(id);
//			if(role == null) {
//				throw new ServiceException(RolesMessage.UNABLE_TO_FIND_ROLE);
//			}
//		}catch( DBException e) {
//			System.out.println(e.getMessage());
//		}
//		
//		return role;
//	}
//	@Transactional
//	@Override
//	public void save(Roles role) throws DBException {
//		try {
//			String name = role.getName();
//			if(name == null) {
//				throw new DBException(RolesMessage.UNABLE_TO_INSERT);
//			}
//			rolesDao.save(role);
//			}catch (DBException e) {
//				System.out.println(e.getMessage());
//			}
//		
//
//	}
//	@Transactional
//	@Override
//	public void delete(Long id) throws ServiceException {
//		Roles role = new Roles();
//		try {
//			role=rolesDao.get(id);
//			if(role != null) {
//				rolesDao.delete(id);
//			}
//			else {
//				throw new ServiceException(RolesMessage.UNABLE_TO_DELETE_ROLE);
//			}
//		}catch( DBException e) {
//			System.out.println(e.getMessage());
//		}
//
//	}
//	
//}
