package online.webnigam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import online.webnigam.dto.LoadMessagesDTO;
import online.webnigam.entity.GGroup;
import online.webnigam.entity.GroupMessageTransaction;
import online.webnigam.entity.User;

@Repository
public class GroupMessageTransactionDAO {

	@PersistenceContext
	EntityManager entityManager;

	public List<LoadMessagesDTO> getAllGroupChat(User fromUser, GGroup group) {
		String query = "select new online.webnigam.dto.LoadMessagesDTO(m.id,m.message,m.path,g.createdAt,g.fromId,g.groupId,u.email) from GroupMessageTransaction g left join User u on g.fromId=u left join Message m  on g.message=m  where g.groupId like :group order by g.createdAt asc";

		@SuppressWarnings("unchecked")
		List<LoadMessagesDTO> messageDTOs = entityManager.createQuery(query).setParameter("group", group)
				.getResultList();
		return messageDTOs;
	}

	@Transactional
	public void add(GroupMessageTransaction groupMessageTransaction) {
		entityManager.persist(groupMessageTransaction);
	}

	@Transactional
	public void changeAllGroupMessageStatusAllReaded(GGroup group, User user) {
		entityManager.createQuery("update GroupMessageTransactionDetail gd set isReaded=true where gd.userId=:userId and gd.groupMessageTransaction in (select g.id from GroupMessageTransaction g where g.groupId like :group)")
				.setParameter("userId", user).setParameter("group", group).executeUpdate();
	}

	public Long getTotalUnreadMessageCount(User fromUser) {
		String query = "select count(*) from GroupMessageTransactionDetail gd  where (gd.userId like :fromUser) and gd.isReaded=false";
		return (Long) entityManager.createQuery(query).setParameter("fromUser", fromUser).getSingleResult();
	}

}
