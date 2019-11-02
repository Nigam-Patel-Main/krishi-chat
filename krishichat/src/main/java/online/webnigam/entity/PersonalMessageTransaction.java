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
@Table(name = "personalmessagetransaction")
public class PersonalMessageTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "from_id")
	private User fromId;

	@ManyToOne
	@JoinColumn(name = "to_id")
	private User toId;

	@ManyToOne
	private Message message;

	@Column(name = "is_readed")
	private boolean isReaded = false;

	public boolean isReaded() {
		return isReaded;
	}

	public void setReaded(boolean isReaded) {
		this.isReaded = isReaded;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Column(name = "created_at")
	private Date createdAt = new Date();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getFromId() {
		return fromId;
	}

	public void setFromId(User fromId) {
		this.fromId = fromId;
	}

	public User getToId() {
		return toId;
	}

	public void setToId(User toId) {
		this.toId = toId;
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
