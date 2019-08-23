package online.webnigam.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageDTO {
	private String toEmail;
	private String fromEmail;
	private String fromId;
	private int messageId;
	private String message;
	private String file;
	@JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss a", timezone = "Asia/Kolkata")
	private Date messageTime;

	@JsonCreator
	public MessageDTO(@JsonProperty("message") String message, @JsonProperty("toEmail") String toEmail,
			@JsonProperty("fromId") String fromId, @JsonProperty("file") String file) {
		super();
		this.toEmail = toEmail;
		this.message = message;
		this.fromId = fromId;
		this.file = file;
		messageTime = new Date();
	}

	public MessageDTO() {

	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "MessageDTO [toEmail=" + toEmail + ", fromId=" + fromId + ", messageId=" + messageId + ", message="
				+ message + ", messageTime=" + messageTime + "]";
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public Date getMessageTime() {
		return messageTime;
	}

	public void setMessageTime(Date messageTime) {
		this.messageTime = messageTime;
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
