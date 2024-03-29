package online.webnigam.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import online.webnigam.dto.blockAndUnblockUserDTO;
import online.webnigam.entity.User;

@Repository
public class UserDAO {

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public void addUser(User user) {

		entityManager.persist(user);
	}

	@Transactional
	public void deleteUser(User user) {
		entityManager.remove(user);
	}

	@Transactional
	public void updateUser(User user) {
		entityManager.merge(user);
	}

	@Transactional
	public User findById(String id) {
		return entityManager.find(User.class, id);
	}

	@Transactional
	public User findByEmail(String email) {
		User user = null;
		try {
			user = entityManager.unwrap(Session.class).byNaturalId(User.class).using("email", email).load();
		} catch (Exception e) {
			return user;
		}
		return user;
	}

	public String getIdByEmail(String email) {
		return (String) entityManager.createQuery("select id from User where email=:email").setParameter("email", email)
				.getSingleResult();
	}

	public String getEmailById(String Id) {
		return (String) entityManager.createQuery("select email from User where id=:id").setParameter("id", Id)
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<blockAndUnblockUserDTO> list(String email) {
		return (List<blockAndUnblockUserDTO>) entityManager.createQuery(
				"select new online.webnigam.dto.blockAndUnblockUserDTO(u.id,u.name,u.email,u.isBlock) From User u where u.email !=:email")
				.setParameter("email", email).getResultList();

	}

	@Transactional
	public void forgotPassword(String email, String password) {
		entityManager.createQuery("update User set password=:password where email=:email")
				.setParameter("password", password).setParameter("email", email).executeUpdate();

	}

	@Transactional
	public void updateTime(String email, Date date) {
		entityManager.createQuery("update User set activeTime=:date where email=:email").setParameter("date", date)
				.setParameter("email", email).executeUpdate();
	}

	@Transactional
	public void updateProfileImagePath(String userId, String path) {
		entityManager.createQuery("update User set profileImagePath=:path where id=:id").setParameter("id", userId)
				.setParameter("path", path).executeUpdate();

	}

	public String getNameByEmail(String fromEmail) {
		return (String) entityManager.createQuery("select name from User where email=:email")
				.setParameter("email", fromEmail).getSingleResult();
	}

	@Transactional
	public void blockUser(String id) {
		entityManager.createQuery("update User set isBlock=true where id=:id").setParameter("id", id).executeUpdate();
	}

	@Transactional
	public void unblockUser(String id) {
		entityManager.createQuery("update User set isBlock=false where id=:id").setParameter("id", id).executeUpdate();
	}

}
