package online.webnigam.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.marlonlom.utilities.timeago.TimeAgo;

public class ChatPeopleDTO {
	private String id;
	private String name;
	private String email;
	private String profileImagePath;
	private Date activeTime;
	private String activeTimeAgo;
	private Long countMessage;
	private boolean isOnline;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");

	public ChatPeopleDTO(String id, String name, String email, String profileImagePath, Date activeTime,
			Long countMessage) throws ParseException {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.profileImagePath = profileImagePath;
		this.activeTime = activeTime;
		this.countMessage = countMessage;

		Date date = sdf.parse(activeTime.toString());
		this.activeTimeAgo = TimeAgo.using(date.getTime());
	}

	public ChatPeopleDTO(String id, String name, String email, String profileImagePath, Date activeTime)
			throws ParseException {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.profileImagePath = profileImagePath;
		this.activeTime = activeTime;

		Date date = sdf.parse(activeTime.toString());
		this.activeTimeAgo = TimeAgo.using(date.getTime());
	}

	// for group
	public ChatPeopleDTO(String id, String name, String profileImagePath,Boolean isLeaded) {
		super();
		this.id = id;
		this.name = name;
		this.profileImagePath = profileImagePath;
		this.isOnline=isLeaded;
	}

	public void setIsOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public boolean getIsOnline() {
		return isOnline;
	}

	public void setCountMessage(Long countMessage) {
		this.countMessage = countMessage;
	}

	public Long getCountMessage() {
		return countMessage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getProfileImagePath() {
		return profileImagePath;
	}

	public void setProfileImagePath(String profileImagePath) {
		this.profileImagePath = profileImagePath;
	}

	public Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	public String getActiveTimeAgo() {
		return activeTimeAgo;
	}

	public void setActiveTimeAgo(String activeTimeAgo) {
		this.activeTimeAgo = activeTimeAgo;
	}

	@Override
	public String toString() {
		return "ChatPeopleDTO [id=" + id + ", name=" + name + ", email=" + email + ", profileImagePath="
				+ profileImagePath + ", activeTime=" + activeTime + ", activeTimeAgo=" + activeTimeAgo
				+ ", countMessage=" + countMessage + ", isOnline=" + isOnline + "]";
	}
	
}
