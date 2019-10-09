package online.webnigam.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author ait_0221
 *
 */
@Entity
@Table(name = "authenticationlog")
public class AuthenticationLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date loginTime = new Date();;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date logoutTime = new Date();;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date createdAt = new Date();;

	@ManyToOne
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "AuthenticationLog [id=" + id + ", loginTime=" + loginTime + ", logoutTime=" + logoutTime
				+ ", createdAt =" + createdAt + "]";
	}

}
