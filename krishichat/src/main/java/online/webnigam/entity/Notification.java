package online.webnigam.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="notification")
public class Notification {
	public static final String SENDREQUEST="SENDREQUEST";
	public static final String ACCEPTREQUEST="ACCEPTREQUEST";
	public static final String REJECTREQUEST="REJECTREQUEST";
	public static final String UNFRIEND="UNFRIEND";
	public static final String MEMBEROFGROUP="MEMBEROFGROUP";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Size(min=5,max=100,message="message  length should between 5-100 charecter")
	private String message;
	
	@Size(max=20,message="purpose size less than 20 charecter")
	private String purpose;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss a",timezone="Asia/Kolkata")
	private Date createdAt = new Date();
	
	private boolean isReaded = false;
	
	@ManyToOne
	@JsonIgnore
	private User user;

	
	public boolean isReaded() {
		return isReaded;
	}

	public void setReaded(boolean isReaded) {
		this.isReaded = isReaded;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
