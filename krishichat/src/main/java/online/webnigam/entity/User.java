package online.webnigam.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DynamicUpdate
@Component
@Table(name = "user")
public class User {

	@Id
	@GenericGenerator(name = "sequence_user_id", strategy = "online.webnigam.service.CustomUserIdGenrator")
	@GeneratedValue(generator = "sequence_user_id")
	@Size(max = 100)
	private String id;

	@Size(min = 2, max = 50, message = "Name length should between 2-50 charecter")
	private String name;

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date birthdate;

	@Size(min = 2, max = 10, message = "Gender length should between 2-10 charecter")
	private String gender;

	@Size(min = 2, max = 100, message = "Status length should between 2-100 charecter")
	private String status;

	@Size(min = 10, max = 255, message = "Address length should between 10-255 charecter")
	private String address;

	@JsonIgnore
	@Transient
	MultipartFile file;

	@Column(name = "profile_image_path")
	private String profileImagePath = "defaultProfile.png";

	@NaturalId
	@Size(min = 2, max = 50, message = "Email length should between 2-100 charecter")
	@Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", message = "Please enter valid email address")
	private String email;

	@JsonIgnore
	@Size(min = 5, max = 100, message = "Password  length should between 5-100 charecter")
	private String password;

	@JsonIgnore
	private boolean enabled;

	@JsonIgnore
	@Column(name = "is_block")
	private boolean isBlock;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "active_time")
	private Date activeTime = new Date();

	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Column(name = "created_at")
	private Date createdAt = new Date();

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<VerificationToken> verificationTokens;

	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Roles> roles = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "fromId")
	private List<PersonalMessageTransaction> personalMessageTransactions = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "fromId")
	private List<GroupMessageTransaction> groupMessageTransactions = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "fromId")
	private List<FriendList> friendLists = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "groupId")
	private List<GroupMember> groupMembers = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Notification> notification = new ArrayList<>();

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getProfileImagePath() {
		return profileImagePath;
	}

	public void setProfileImagePath(String profileImagePath) {
		this.profileImagePath = profileImagePath;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public boolean getIsBlock() {
		return isBlock;
	}

	public void setIsBlock(boolean isBlock) {
		this.isBlock = isBlock;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public List<VerificationToken> getVerificationTokens() {
		return verificationTokens;
	}

	public void setVerificationTokens(List<VerificationToken> verificationTokens) {
		this.verificationTokens = verificationTokens;
	}

	public List<Roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

	public List<GroupMessageTransaction> getGroupMessageTransactions() {
		return groupMessageTransactions;
	}

	public void setGroupMessageTransactions(List<GroupMessageTransaction> groupMessageTransactions) {
		this.groupMessageTransactions = groupMessageTransactions;
	}

	public List<PersonalMessageTransaction> getPersonalMessageTransactions() {
		return personalMessageTransactions;
	}

	public void setPersonalMessageTransactions(List<PersonalMessageTransaction> personalMessageTransactions) {
		this.personalMessageTransactions = personalMessageTransactions;
	}

	public List<FriendList> getFriendLists() {
		return friendLists;
	}

	public void setFriendLists(List<FriendList> friendLists) {
		this.friendLists = friendLists;
	}

	public List<GroupMember> getGroupMembers() {
		return groupMembers;
	}

	public void setGroupMembers(List<GroupMember> groupMembers) {
		this.groupMembers = groupMembers;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthdate=" + birthdate + ", gender=" + gender + ", status="
				+ status + ", address=" + address + ", profileImagePath=" + profileImagePath + ", email=" + email
				+ ", password=" + password + ", enabled=" + enabled + ", isBlock=" + isBlock + ", activeTime="
				+ activeTime + ", createdAt=" + createdAt + "]";
	}

}
