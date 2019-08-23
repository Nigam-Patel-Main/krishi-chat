package online.webnigam.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import online.webnigam.entity.Message;

@Repository
public class MessageDAO {

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public void add(Message message) {
		entityManager.persist(message);
	}

	@Transactional
	public Message findById(int id) {
		return entityManager.find(Message.class, id);
	}

}
