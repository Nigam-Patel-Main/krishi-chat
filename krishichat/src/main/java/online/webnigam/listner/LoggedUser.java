package online.webnigam.listner;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import online.webnigam.dto.ActiveUserStore;
import online.webnigam.service.ChatService;

public class LoggedUser implements HttpSessionBindingListener {
	
	
	private String email;
	private ActiveUserStore activeUserStore;
	

	public LoggedUser(String email, ActiveUserStore activeUserStore) {
		this.email = email;
		this.activeUserStore = activeUserStore;
	}

	public LoggedUser() {
	}

	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		List<String> users = activeUserStore.getUsers();
		LoggedUser user = (LoggedUser) event.getValue();
		if (users != null) {
			if (!users.contains(user.getEmail()))
				users.add(user.getEmail());
		}
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		List<String> users = activeUserStore.getUsers();
		LoggedUser user = (LoggedUser) event.getValue();

		if (users != null&&user!=null) {
			if (users.contains(user.getEmail()))
				users.remove(user.getEmail());
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ActiveUserStore getActiveUserStore() {
		return activeUserStore;
	}

	public void setActiveUserStore(ActiveUserStore activeUserStore) {
		this.activeUserStore = activeUserStore;
	}

}
