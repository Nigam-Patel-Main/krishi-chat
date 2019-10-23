package online.webnigam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import online.webnigam.dto.LoadMessagesDTO;
import online.webnigam.entity.PersonalMessageTransaction;
import online.webnigam.entity.User;

@Repository
public class PersonalMessageTransactionDAO {

	@PersistenceContext
	EntityManager entityManager;

	public List<LoadMessagesDTO> getAllPersonalChat(User fromUser, User toUser) {
		String query = "select new online.webnigam.dto.LoadMessagesDTO(m.id,m.message,m.path,p.createdAt,p.fromId,p.toId) from PersonalMessageTransaction p left join Message m  on p.message=m  where (p.fromId like :fromUser and p.toId like :toUser) or (p.fromId like :toUser and p.toId like :fromUser) order by p.createdAt asc";

		@SuppressWarnings("unchecked")
		List<LoadMessagesDTO> messageDTOs = entityManager.createQuery(query).setParameter("fromUser", fromUser)
				.setParameter("toUser", toUser).getResultList();
		return messageDTOs;

	}

	@Transactional
	public void add(PersonalMessageTransaction messageTransaction) {
		entityManager.persist(messageTransaction);
	}

	@Transactional
	public void changePersonalMessageStatus(int id) {
		entityManager.createQuery("update PersonalMessageTransaction p set p.isReaded=false where p.id=:id")
				.setParameter("id", id).executeUpdate();
	}

	@Transactional
	public void changePersonalMessageStatusAllReaded(User fromUser, User toUser) {
		entityManager.createQuery(
				"update PersonalMessageTransaction p set isReaded=true where  (p.fromId like :fromUser and p.toId like :toUser) or (p.fromId like :toUser and p.toId like :fromUser) )")
				.setParameter("fromUser", fromUser).setParameter("toUser", toUser).executeUpdate();

	}

	public Long getTotalUnreadMessageCount(User fromUser) {
		String query = "select count(*) from PersonalMessageTransaction p  where (p.toId like :fromUser) and p.isReaded=false";
		return (Long) entityManager.createQuery(query).setParameter("fromUser", fromUser).getSingleResult();
	}
}
