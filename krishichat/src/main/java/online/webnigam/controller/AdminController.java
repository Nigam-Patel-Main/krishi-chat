package online.webnigam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import online.webnigam.service.AuthenticationLogService;

@Controller
public class AdminController {
	@Autowired
	AuthenticationLogService authenticationLogService;

	@RequestMapping("/log")
	public ModelAndView showLog() {
		System.out.println("log called");
		return new ModelAndView("log", "logs", authenticationLogService.list());
	}
}
