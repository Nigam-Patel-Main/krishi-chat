package online.webnigam.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.marlonlom.utilities.timeago.TimeAgo;

import online.webnigam.entity.GGroup;
import online.webnigam.entity.User;

public class LoadMessagesDTO {
	private Integer id;
	private String message;
	private String file;
	private String chatTimeAgo;
	@JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss a",timezone="Asia/Kolkata")
	private Date chatTime;
	private String fromId;
	private String fromEmail;
	private String toId;
	@JsonIgnore
	private User fromUser;
	@JsonIgnore
	private User toUser;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");

	public LoadMessagesDTO(int id, String message,String file, Date chatTime, User fromUser, GGroup group,String fromEmail) throws ParseException {
		super();
		this.id = id;
		this.message = message;
		this.file=file;
		this.chatTime = chatTime;
		this.fromId = fromUser.getId();
		this.toId = group.getId();
		this.fromEmail=fromEmail;

		Date date = sdf.parse(chatTime.toString());
		this.chatTimeAgo = TimeAgo.using(date.getTime());
	}

	public LoadMessagesDTO(int id, String message,String file, Date chatTime, User fromUser, User toUser) throws ParseException {
		super();
		this.id = id;
		this.message = message;
		this.file=file;
		this.chatTime = chatTime;
		this.fromId = fromUser.getId();
		this.toId = toUser.getId();

		Date date = sdf.parse(chatTime.toString());
		this.chatTimeAgo = TimeAgo.using(date.getTime());
	}
	

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getToId() {
		return toId;
	}

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public void setToId(String toId) {
		this.toId = toId;
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

	public String getChatTimeAgo() {
		return chatTimeAgo;
	}

	public void setChatTimeAgo(String chatTimeAgo) {
		this.chatTimeAgo = chatTimeAgo;
	}

	public Date getChatTime() {
		return chatTime;
	}

	public void setChatTime(Date chatTime) {
		this.chatTime = chatTime;
	}

}
