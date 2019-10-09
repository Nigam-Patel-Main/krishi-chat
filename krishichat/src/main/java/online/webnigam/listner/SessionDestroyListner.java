package online.webnigam.listner;

import java.text.ParseException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

import online.webnigam.dto.ActiveUserStore;
import online.webnigam.service.AuthenticationLogService;
import online.webnigam.service.ChatService;
import online.webnigam.service.UserService;

@Component
public class SessionDestroyListner {

	@Autowired
	UserService userService;
	@Autowired
	ActiveUserStore activeUserStore;
	@Autowired
	ChatService chatService;

	@Autowired
	AuthenticationLogService authenticationLogService;

	@EventListener
	public void sessionCreatelistner(HttpSessionCreatedEvent event) {
	}

	@EventListener
	public void sessionDestroylistner(HttpSessionDestroyedEvent event) throws ParseException {
		HttpSession session = event.getSession();
		LoggedUser loggedUser = (LoggedUser) session.getAttribute("loggedUser");
		if (loggedUser != null) {
			userService.updateTime(loggedUser.getEmail());
			activeUserStore.getUsers().remove(loggedUser.getEmail());

			// user log
			authenticationLogService.changeLogoutTime(userService.findByEmail(loggedUser.getEmail()));
			System.out.println("logout log successfull");

			chatService.sendToAllFriendOflineMessage(loggedUser.getEmail());
		}
	}

}
