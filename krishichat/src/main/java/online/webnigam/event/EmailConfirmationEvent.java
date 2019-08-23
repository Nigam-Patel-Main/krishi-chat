package online.webnigam.event;

import org.springframework.context.ApplicationEvent;

import online.webnigam.entity.User;

public class EmailConfirmationEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	private User user;
	private String contextPath;
	private String purpose;

	public EmailConfirmationEvent(Object source, User user, String purpose, String contextPath) {
		super(source);
		this.user = user;
		this.contextPath = contextPath;
		this.purpose = purpose;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

}
