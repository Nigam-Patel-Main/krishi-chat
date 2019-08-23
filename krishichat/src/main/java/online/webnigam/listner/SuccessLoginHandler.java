package online.webnigam.listner;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import online.webnigam.dto.ActiveUserStore;
import online.webnigam.entity.User;
import online.webnigam.service.ChatService;
import online.webnigam.service.UserService;

@Component
public class SuccessLoginHandler implements AuthenticationSuccessHandler {

	@Autowired
	UserService userService;
	@Autowired
	ActiveUserStore activeUserStore;

	@Autowired
	ChatService chatService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		LoggedUser loggedUser = null;
		User user = null;

		HttpSession session = request.getSession(false);

		if (session != null) {
			if (!activeUserStore.getUsers().contains(authentication.getName())) {
				user = userService.findByEmail(authentication.getName());
				loggedUser = new LoggedUser(authentication.getName(), activeUserStore);
				session.setAttribute("loggedUser", loggedUser);
				session.setAttribute("userSession", user);
				response.sendRedirect(request.getContextPath() + "/home");
			} else {
				request.setAttribute("message", "You are allready logged in..");
				request.getRequestDispatcher("/login").forward(request, response);
			}

		}
		try {
			chatService.sendToAllFriendOnlineMessage(authentication.getName());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
