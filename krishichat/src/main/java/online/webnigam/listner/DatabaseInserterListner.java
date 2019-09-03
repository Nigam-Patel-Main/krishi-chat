package online.webnigam.listner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import online.webnigam.entity.Roles;
import online.webnigam.entity.User;
import online.webnigam.service.RolesService;
import online.webnigam.service.UserService;

@Component
public class DatabaseInserterListner implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	UserService userService;

	@Autowired
	RolesService roleService;

	@Autowired
	BCryptPasswordEncoder encoder;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {

		if (userService.findByEmail("nigampatel44@gmail.com") == null) {

			// Admin Insert

			User user = new User();
			user.setName("Nigam Admin");
			user.setEmail("nigampatel44@gmail.com");
			user.setPassword("nigamnigam");
			user.setEnabled(true);

			Roles role1 = new Roles();
			role1.setRole("USER");
			role1.setUser(user);

			Roles role2 = new Roles();
			role2.setRole("ADMIN");
			role2.setUser(user);

			userService.add(user);

			roleService.add(role1);
			roleService.add(role2);

		}

	}

}
