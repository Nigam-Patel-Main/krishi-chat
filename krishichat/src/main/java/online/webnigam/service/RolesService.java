package online.webnigam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.webnigam.dao.RolesDAO;
import online.webnigam.entity.Roles;

@Service
public class RolesService {

	@Autowired
	RolesDAO rolesDAO;

	public void add(Roles roles) {
		rolesDAO.addRole(roles);
	}

}
