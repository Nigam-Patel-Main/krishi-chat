package online.webnigam.dto;

import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class GGroupDTO {
	private String leaderId;
	private String groupId;
	private String name;
	private String status;
	private String friends;
	private String file;
	
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getLeaderId() {
		return leaderId;
	}
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFriends() {
		return friends;
	}
	public void setFriends(String friends) {
		this.friends = friends;
	}

	@Override
	public String toString() {
		return "GGroupDTO [leaderId=" + leaderId + ", groupId=" + groupId + ", name=" + name + ", status=" + status
				+ ", friends=" + friends + ", file=" + file + "]";
	}
	
	
	
}
