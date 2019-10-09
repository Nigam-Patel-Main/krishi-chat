package online.webnigam.dto;

import java.util.Date;

public class LogDTO {
	private int id;
	private String name;
	private String email;
	private Date loginTime;
	private Date logoutTime;

	public LogDTO(int id, String name, String email, Date loginTime, Date logoutTime) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.loginTime = loginTime;
		this.logoutTime = logoutTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	@Override
	public String toString() {
		return "LogDTO [id=" + id + ", name=" + name + ", email=" + email + ", loginTime=" + loginTime + ", logoutTime="
				+ logoutTime + "]";
	}

}
