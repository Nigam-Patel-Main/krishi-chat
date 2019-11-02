package online.webnigam.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "groupmessagetransactiondetail")
public class GroupMessageTransactionDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "groupMessageTransactionId")
	private GroupMessageTransaction groupMessageTransaction;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User userId;

	@Column(name = "is_readed")
	private boolean isReaded;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Column(name = "created_at")
	private Date createdAt = new Date();

	public GroupMessageTransaction getGroupMessageTransaction() {
		return groupMessageTransaction;
	}

	public void setGroupMessageTransaction(GroupMessageTransaction groupMessageTransaction) {
		this.groupMessageTransaction = groupMessageTransaction;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public boolean isReaded() {
		return isReaded;
	}

	public void setReaded(boolean isReaded) {
		this.isReaded = isReaded;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
