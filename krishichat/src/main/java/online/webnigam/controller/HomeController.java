package online.webnigam.controller;

import java.text.ParseException;
import java.util.Set;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.google.api.plus.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import online.webnigam.dto.ActiveUserStore;
import online.webnigam.entity.Roles;
import online.webnigam.entity.User;
import online.webnigam.entity.VerificationToken;
import online.webnigam.event.EmailConfirmationEvent;
import online.webnigam.listner.LoggedUser;
import online.webnigam.service.AuthenticationService;
import online.webnigam.service.ChatService;
import online.webnigam.service.RolesService;
import online.webnigam.service.UserService;
import online.webnigam.service.VerificationTokenService;

@Controller
public class HomeController {

	@Autowired
	UserService userService;

	@Autowired
	VerificationTokenService tokenService;

	@Autowired
	ApplicationEventPublisher eventPublisher;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	AuthenticationService authService;

	@Autowired
	ActiveUserStore activeUserStore;

	@Autowired
	RolesService rolesService;

	@Autowired
	ChatService chatService;

	@Autowired
	BCryptPasswordEncoder encoder;

	@RequestMapping(value = { "/home", "/" })
	public String home() {
		return "home";
	}

	@RequestMapping("/login")
	public String showLogin() {
		return "login";
	}

	@RequestMapping("/googlelogin")
	public RedirectView showGoogleLogin() {
		RedirectView view = new RedirectView();
		String url = authService.genrateUrl();
		view.setUrl(url);
		return view;
	}

	@GetMapping("/loginlogic")
	public String afterLogin(@RequestParam("code") String code) {
		String authToken = authService.getAuthenticationToken(code);
		return "redirect:/showHome/" + authToken;
	}

	@GetMapping("/showHome/{authToken:.+}")
	public String showUser(@PathVariable("authToken") String authToken, HttpServletRequest request) {

		Person person = authService.fetchUserFromGoogle(authToken);
		// Modify Name in Title Case
		String name = person.getGivenName() + " " + person.getFamilyName();
		name = Stream.of(name.split(" ")).map(w -> w.toUpperCase().charAt(0) + w.toLowerCase().substring(1))
				.reduce((s, s2) -> s + " " + s2).orElse("");
		Set<String> emailAddresses = person.getEmailAddresses();
		if (emailAddresses.size() > 0) {
			Object[] array = emailAddresses.toArray();

			User user = userService.findByEmail(array[0].toString());

			if (user == null) {

				User googleUser = new User();
				googleUser.setName(name);
				googleUser.setBirthdate(person.getBirthday());
				googleUser.setEmail(array[0].toString());
				googleUser.setProfileImagePath(person.getImageUrl());
				googleUser.setPassword("hjjkfkjdghkjgnjvkgdummy");
				googleUser.setEnabled(true);

				userService.add(googleUser);

				Roles roles = new Roles();
				roles.setRole("USER");
				roles.setUser(user);
				rolesService.add(roles);
				autoLogin(request, googleUser);
			} else {

				if (user.getBirthdate() == null) {
					user.setBirthdate(person.getBirthday());
				}
				if (user.getProfileImagePath().trim().equals("defaultProfile.png")) {
					user.setProfileImagePath(person.getImageUrl());
				}
				if (user.getGender() == null) {
					user.setAddress(person.getGender());
				}
				userService.updateUser(user);
				autoLogin(request, user);

			}

		}
		// check if person is there
		return "redirect:/home";
	}

	@RequestMapping(value = "/registration")
	public ModelAndView showRegistration() {
		return new ModelAndView("registration", "user", new User());
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView handleRegistration(@ModelAttribute("user") @Valid User user, BindingResult result,
			HttpServletRequest request, RedirectAttributes flashModel, Model model) {

		if (result.hasErrors()) {
			return new ModelAndView("registration", "user", user);
		}
		if (userService.findByEmail(user.getEmail()) != null) {
			model.addAttribute("error", "Your email is allready registred...");
			return new ModelAndView("registration", "user", user);
		}

		// add User
		userService.add(user);
//		 Comment if you want enable auto login
		userService.approveUser(user);

		// Set user role
		Roles roles = new Roles();
		roles.setRole("USER");
		roles.setUser(user);
		rolesService.add(roles);

		// Send email for verification
		eventPublisher.publishEvent(
				new EmailConfirmationEvent(this, user, VerificationToken.EMAILVERIFICATION, request.getContextPath()));

		flashModel.addFlashAttribute("message", "For email verification confirm link on your email account...");
		return new ModelAndView("redirect:/registration");
	}

	@RequestMapping("/confirmRegistration")
	public String confirmRegistration(@RequestParam("token") String token, RedirectAttributes model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		User user = tokenService.isValid(token, VerificationToken.EMAILVERIFICATION);
		if (user == null) {
			model.addFlashAttribute("error", "Your token is invalid please try again");
			return "redirect:/login";
		}
		userService.approveUser(user);
		tokenService.deleteToken(token);
		// Automatically login
		autoLogin(request, user);
		return "redirect:/home";
	}

	public boolean autoLogin(HttpServletRequest request, User user) {
		try {

			LoggedUser loggedUser = null;
			System.out.println("Email is :" + user.getEmail());
			UserDetails userDetails = userService.loadUserByUsername(user.getEmail());
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,
					userDetails.getPassword(), userDetails.getAuthorities());
			if (auth.isAuthenticated() && user.getEnabled()) {
				SecurityContextHolder.getContext().setAuthentication(auth);
				HttpSession session = request.getSession(false);

				if (session != null) {
					loggedUser = new LoggedUser(user.getEmail(), activeUserStore);
					session.setAttribute("loggedUser", loggedUser);
					session.setAttribute("userSession", user);
				}
				try {
					chatService.sendToAllFriendOnlineMessage(user.getEmail());
				} catch (ParseException e) {
					e.printStackTrace();
				}

				return true;
			}
		} catch (Exception e) {
			System.out.println("Problem authenticating user" + user.getEmail() + e);
			return false;
		}
		return false;
	}

	@RequestMapping("/showAddEmail")
	public String showAddEmai() {
		return "addEmail";
	}

	@RequestMapping(value = "/sendforgotPasswordEmailToken", method = RequestMethod.POST)
	public String sendforgotPasswordEmailToken(@RequestParam("email") String email, RedirectAttributes model,
			HttpServletRequest request) {
		User user = userService.findByEmail(email);
		if (user == null) {
			model.addFlashAttribute("error", "Email address is not valid");
			return "redirect:/showAddEmail";
		}
		eventPublisher.publishEvent(
				new EmailConfirmationEvent(this, user, VerificationToken.FORGOTPASSWORD, request.getContextPath()));
		model.addFlashAttribute("message", "please check and verify your email  ..");
		return "redirect:/showAddEmail";
	}

	@RequestMapping("/showForgotPassword")
	public String showForgotPassword(@RequestParam("token") String token, RedirectAttributes flashModel, Model model) {

		User user = tokenService.isValid(token, VerificationToken.FORGOTPASSWORD);
		if (user == null) {
			flashModel.addFlashAttribute("error", "Your token is invalid please try again");
			return "redirect:/login";
		}
		tokenService.deleteToken(token);
		model.addAttribute("email", user.getEmail());
		return "forgotPassword";
	}

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	public String forgotPassword(HttpServletRequest request, @RequestParam("email") String email,
			@RequestParam("password") String password, RedirectAttributes model) {
		User user = userService.forgotPassword(email, password);

		autoLogin(request, user);
		return "redirect:/home";
	}
}
