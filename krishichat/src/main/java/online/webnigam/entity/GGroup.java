package online.webnigam.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ggroup")
@DynamicUpdate
public class GGroup {

	@Id
	@GenericGenerator(name = "sequence_user_id", strategy = "online.webnigam.service.CustomGroupIdGenrator")
	@GeneratedValue(generator = "sequence_user_id")
	@Size(max = 20)
	private String id;

	@Size(min = 2, max = 50, message = "Name  length should between 2-50 charecter")
	private String name;

	@Size(min = 2, max = 100, message = "Image Path length should between 2-100 charecter")
	@Column(name = "profile_image_path")
	private String profileImagePath = "defaultProfile.png";

	@Size(min = 2, max = 100, message = "Status  length should between 2-100 charecter")
	private String status;

	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Column(name = "created_at")
	private Date createdAt = new Date();

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfileImagePath() {
		return profileImagePath;
	}

	public void setProfileImagePath(String profileImagePath) {
		this.profileImagePath = profileImagePath;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
