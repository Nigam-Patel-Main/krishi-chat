package online.webnigam.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import online.webnigam.entity.VerificationToken;

@Repository
public class VerificationTokenDAO {

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public void addVerificationToken(VerificationToken verifivationToken) {
		entityManager.persist(verifivationToken);
	}

	@Transactional
	public VerificationToken findById(int id) {
		return entityManager.find(VerificationToken.class, id);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public VerificationToken findByTokenAndPurpose(String token, String purpose) {
		return (VerificationToken) entityManager
				.createQuery("from VerificationToken v where v.token=:token and purpose=:purpose")
				.setParameter("token", token).setParameter("purpose", purpose).getResultList().stream().findFirst()
				.orElse(null);
	}

	@Transactional
	public void deleteToken(String token) {
		entityManager.createQuery("delete from VerificationToken  where token=:token").setParameter("token", token)
				.executeUpdate();
	}

}
