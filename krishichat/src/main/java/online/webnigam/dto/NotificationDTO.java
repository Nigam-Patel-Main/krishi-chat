package online.webnigam.dto;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class NotificationDTO {

	private String id;
	

	
	
	
	private String name;
	private String message;

	public NotificationDTO(String id, String name, String message, Date createdAt, boolean isReaded, String purpose) {
		super();
		this.id = id;
		this.name = name;
		this.message = message;
		this.createdAt = createdAt;
		this.isReaded = isReaded;
		this.purpose = purpose;
	}

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss a",timezone="Asia/Kolkata")
	private Date createdAt = new Date();
	
	private boolean isReaded = false;

	private String purpose;
	
	

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isReaded() {
		return isReaded;
	}

	public void setReaded(boolean isReaded) {
		this.isReaded = isReaded;
	}

	
	
}
