package online.webnigam.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.marlonlom.utilities.timeago.TimeAgo;

public class ApplicationUserDTO {
	private String id;
	private String name;
	private String email;
	private String profileImagePath;
	private Date sentTime;
	private boolean flag=false;

	private String timeAgo;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");

	public ApplicationUserDTO(String id, String name, String email, String profileImagePath) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.profileImagePath = profileImagePath;
	}
	public ApplicationUserDTO(String id, String name, String email, String profileImagePath,boolean isLeader) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.profileImagePath = profileImagePath;
		this.flag=isLeader;
	}
	public ApplicationUserDTO(String id, String email) {
		super();
		this.id = id;
		this.email=email;
	}

	public ApplicationUserDTO(String id, String name, String email, String profileImagePath, Date sentTime)
			throws ParseException {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.profileImagePath = profileImagePath;
		this.sentTime = sentTime;

		Date date = sdf.parse(sentTime.toString());
		this.timeAgo = TimeAgo.using(date.getTime());
	}

	public String getTimeAgo() {
		return timeAgo;
	}

	public void setTimeAgo(String timeAgo) {
		this.timeAgo = timeAgo;
	}

	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public Date getSentTime() {
		return sentTime;
	}

	public void setSentTime(Date sentTime) {
		this.sentTime = sentTime;
	}

	public String getProfileImagePath() {
		return profileImagePath;
	}

	public void setProfileImagePath(String profileImagePath) {
		this.profileImagePath = profileImagePath;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
