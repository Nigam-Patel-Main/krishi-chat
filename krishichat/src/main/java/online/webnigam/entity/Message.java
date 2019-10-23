package online.webnigam.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "message")
public class Message {
	public final static String Text = "TEXT";
	public static final String Media = "Media";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 2000)
	private String message;

	@Column(length = 200)
	private String path;

	@Column(length = 30)
	private String type;

	@OneToMany(mappedBy = "fromId")
	private List<PersonalMessageTransaction> fromMessages = new ArrayList<>();

	@OneToMany(mappedBy = "toId")
	private List<PersonalMessageTransaction> toMessages = new ArrayList<>();

	@OneToMany(mappedBy = "groupId")
	private List<GroupMessageTransaction> groupMessageTransactions = new ArrayList<>();

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<PersonalMessageTransaction> getFromMessages() {
		return fromMessages;
	}

	public void setFromMessages(List<PersonalMessageTransaction> fromMessages) {
		this.fromMessages = fromMessages;
	}

	public List<PersonalMessageTransaction> getToMessages() {
		return toMessages;
	}

	public void setToMessages(List<PersonalMessageTransaction> toMessages) {
		this.toMessages = toMessages;
	}

	public List<GroupMessageTransaction> getGroupMessageTransactions() {
		return groupMessageTransactions;
	}

	public void setGroupMessageTransactions(List<GroupMessageTransaction> groupMessageTransactions) {
		this.groupMessageTransactions = groupMessageTransactions;
	}

}
