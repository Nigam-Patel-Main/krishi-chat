package online.webnigam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import online.webnigam.dto.NotificationDTO;
import online.webnigam.entity.Notification;
import online.webnigam.entity.User;

@Repository
public class NotificationDAO {
	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public void add(Notification notification) {
		entityManager.persist(notification);
	}

	public Long getTotalUnreadNotificationCount(User user) {
		String qry = "select count(*) from Notification where user=:user and isReaded=false";
		return (Long) entityManager.createQuery(qry).setParameter("user", user).getSingleResult();
	}

	@Transactional
	public void changeNotificationStatusAllReaded(User user, boolean isReaded) {
		entityManager.createQuery("update Notification set isReaded=:isReaded where user=:user")
				.setParameter("user", user).setParameter("isReaded", isReaded).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<NotificationDTO> getAllNotifications(User user) {
		String qry = "select new online.webnigam.dto.NotificationDTO(u.id,u.name,n.message,n.createdAt,n.isReaded,n.purpose) From User u left join Notification n on  u.id=n.user  where n.user=:user  order by n.createdAt desc";
		return (List<NotificationDTO>) entityManager.createQuery(qry).setParameter("user", user).setMaxResults(5)
				.getResultList();
	}
}
