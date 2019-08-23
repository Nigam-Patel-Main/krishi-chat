package online.webnigam.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import online.webnigam.entity.User;
import online.webnigam.service.FriendListService;
import online.webnigam.service.ImageService;
import online.webnigam.service.UserService;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	ServletContext servletContext;

	@Autowired
	ImageService imageService;

	@Autowired
	FriendListService friendListService;

	@RequestMapping("/showProfile")
	public ModelAndView showProfile(@SessionAttribute("userSession") User user, Model model) {
		return new ModelAndView("profile", "user", user);
	}

	@RequestMapping(value = "/updateProfileImage", method = RequestMethod.POST)
	public void updateProfileImage(@RequestParam("userId") String userId, @RequestParam("file") String file,
			HttpServletRequest request, @SessionAttribute("userSession") User user) throws IOException {
		String path = null;

		String location = servletContext.getRealPath("/") + File.separator + "resources" + File.separator
				+ "profileImage";

		path = imageService.storeImage(file, location);
		imageService.deleteImage(user.getProfileImagePath(), location);
		userService.updateProfileImage(userId, path);
		user.setProfileImagePath(path);
		request.getSession(false).setAttribute("userSession", user);
	}

	@RequestMapping(value = "/handleEditProfile", method = RequestMethod.POST)
	public ModelAndView handleEditProfile(@ModelAttribute("user") @Valid User user, BindingResult result, Model model,
			HttpServletRequest request, RedirectAttributes flashModel,
			@SessionAttribute("userSession") User userSession) {

		if (result.hasErrors()) {
			model.addAttribute("error", "Error in profile updation please check it..");
			return new ModelAndView("profile", "user", user);
		}
		user.setProfileImagePath(userSession.getProfileImagePath());
		userService.updateUser(user);
		request.getSession(false).setAttribute("userSession", user);
		flashModel.addFlashAttribute("message", "Profile successfully changed");
		return new ModelAndView("redirect:/showProfile");

	}

	@RequestMapping("/getUserProfile")
	public @ResponseBody User getUserProfile(@RequestParam("id") String id) {
		return userService.findById(id);
	}

	@RequestMapping("/discoverUser")
	public ModelAndView applicationUsers(@RequestParam(value = "page", defaultValue = "1") int page,
			@SessionAttribute("userSession") User user) {
		return new ModelAndView("discoverUser", "users", friendListService.getRequestableUser(page, user));
	}

	@RequestMapping("/friendPendingRequest")
	public ModelAndView getFriendPendingRequest(@RequestParam(value = "page", defaultValue = "1") int page,
			@SessionAttribute("userSession") User user) throws ParseException {
		return new ModelAndView("friendPendingRequest", "users", friendListService.getFriendPendingUser(page, user));
	}

	@RequestMapping("/friendRequest")
	public ModelAndView getFriendRequest(@RequestParam(value = "page", defaultValue = "1") int page,
			@SessionAttribute("userSession") User user) throws ParseException {
		return new ModelAndView("friendRequest", "users", friendListService.getFriendRequestUser(page, user));
	}

	@RequestMapping("/friendList")
	public ModelAndView getFriendList(@SessionAttribute("userSession") User user) {

		return new ModelAndView("friendList", "users", friendListService.getFriendList(user));
	}

}
