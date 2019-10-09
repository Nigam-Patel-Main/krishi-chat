package online.webnigam.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.webnigam.dao.AuthenticationLogDAO;
import online.webnigam.dto.LogDTO;
import online.webnigam.entity.AuthenticationLog;
import online.webnigam.entity.User;

@Service
public class AuthenticationLogService {

	@Autowired
	AuthenticationLogDAO authenticationDao;

	public List<LogDTO> list() {
		return authenticationDao.list();
	}

	public void add(AuthenticationLog log) {
		authenticationDao.add(log);
	}

	public void changeLogoutTime(User user) {
		authenticationDao.changeLogoutTime(user);
	}

	public AuthenticationLog buildPOJOObject() {
		AuthenticationLog authenticationLog = new AuthenticationLog();
		authenticationLog.setCreatedAt(new Date());
		authenticationLog.setLoginTime(new Date());
		authenticationLog.setLogoutTime(new Date());
		return authenticationLog;
	}

}
