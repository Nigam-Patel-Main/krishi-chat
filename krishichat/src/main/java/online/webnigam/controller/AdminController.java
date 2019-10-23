package online.webnigam.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import online.webnigam.service.AuthenticationLogService;
import online.webnigam.service.UserService;

@Controller
public class AdminController {
	@Autowired
	AuthenticationLogService authenticationLogService;

	@Autowired
	UserService userService;

	@RequestMapping("/log")
	public ModelAndView showLog() {
		return new ModelAndView("log", "logs", authenticationLogService.list());
	}

	@RequestMapping("/showUsers")
	public ModelAndView showkUsers(Principal principle) {
		return new ModelAndView("blockAndUnblockUser", "users", userService.list(principle.getName()));
	}

	@RequestMapping("/blockUser")
	public String blockUser(@RequestParam("id") String id) {
		userService.blockUser(id);
		return "redirect:/showUsers";
	}

	@RequestMapping("/unblockUser")
	public String unblockUser(@RequestParam("id") String id) {
		userService.unblockUser(id);
		return "redirect:/showUsers";
	}
}
