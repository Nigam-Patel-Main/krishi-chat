package online.webnigam.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "groupmessagetransaction")
public class GroupMessageTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "group_id")
	private GGroup groupId;

	@ManyToOne
	@JoinColumn(name = "from_id")
	private User fromId;

	@ManyToOne
	private Message message;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Column(name = "created_at")
	private Date createdAt = new Date();

	@JsonIgnore
	@OneToMany(mappedBy = "fromId")
	private List<GroupMessageTransaction> groupMessageTransactions = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public GGroup getGroupId() {
		return groupId;
	}

	public void setGroupId(GGroup groupId) {
		this.groupId = groupId;
	}

	public List<GroupMessageTransaction> getGroupMessageTransactions() {
		return groupMessageTransactions;
	}

	public void setGroupMessageTransactions(List<GroupMessageTransaction> groupMessageTransactions) {
		this.groupMessageTransactions = groupMessageTransactions;
	}

	public User getFromId() {
		return fromId;
	}

	public void setFromId(User fromId) {
		this.fromId = fromId;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
