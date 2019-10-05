package online.webnigam.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import online.webnigam.dao.UserDAO;
import online.webnigam.entity.Roles;
import online.webnigam.entity.User;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	UserDAO userDAO;

	public User findByEmail(String email) {
		return userDAO.findByEmail(email);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("user is logged with email :" + username);

		User user = findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return getGrantedUser(user);
	}

	// for create user of Spring Security type

	public org.springframework.security.core.userdetails.User getGrantedUser(User user) {
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				user.getEnabled(), true, true, true, getUserAuthority(user.getRoles()));
	}

	// create collection of granted authority
	public List<GrantedAuthority> getUserAuthority(List<Roles> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (Roles role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
		}
		return authorities;
	}

	public void add(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		userDAO.addUser(user);
	}

	public void approveUser(User user) {
		user.setEnabled(true);
		userDAO.updateUser(user);
	}

	public User forgotPassword(String email, String password) {
		User user = findByEmail(email);
		user.setPassword(encoder.encode(password));
		updateUser(user);
		return user;
	}

	public User buildUserFromId(String id) {
		User user = new User();
		user.setId(id);
		return user;
	}

	public User buildUserFromEmail(String email) {
		User user = new User();
		user.setId(getIdByEmail(email));
		user.setEmail(email);
		return user;
	}

	public String getIdByEmail(String email) {
		return userDAO.getIdByEmail(email);
	}

	public String getEmailById(String Id) {
		return userDAO.getEmailById(Id);
	}

	public void updateUser(User user) {
		userDAO.updateUser(user);
	}

	public User findById(String id) {
		return userDAO.findById(id);
	}

	public void updateTime(String email) {
		userDAO.updateTime(email, new Date());

	}

	public void updateProfileImage(String userId, String path) {
		userDAO.updateProfileImagePath(userId, path);
	}

	public String getNameByEmail(String fromEmail) {
		return userDAO.getNameByEmail(fromEmail);
	}

}
