package online.webnigam.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import online.webnigam.dto.LogDTO;
import online.webnigam.entity.AuthenticationLog;
import online.webnigam.entity.User;

@Repository
public class AuthenticationLogDAO {

	@PersistenceContext
	EntityManager entityManager;

	public List<LogDTO> list() {
		String query = "select new online.webnigam.dto.LogDTO(a.id,u.name,u.email,a.loginTime,a.logoutTime) from AuthenticationLog a left join User u on a.user=u order by a.createdAt desc";
		@SuppressWarnings("unchecked")
		List<LogDTO> logs = entityManager.createQuery(query).getResultList();
		return logs;
	}

	@Transactional
	public void add(AuthenticationLog log) {
		entityManager.persist(log);
	}

	@Transactional
	public void changeLogoutTime(User user) {
		int id = getMaxIdByUser(user);

		entityManager.createQuery("update AuthenticationLog  set logoutTime=:logoutTime where id =:id")
				.setParameter("id", id).setParameter("logoutTime", new Date()).executeUpdate();
	}

	public int getMaxIdByUser(User user) {
		return (int) entityManager.createQuery("select max(id) from AuthenticationLog where user=:user")
				.setParameter("user", user).getSingleResult();
	}
}
