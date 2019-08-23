package online.webnigam.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import online.webnigam.entity.GroupMessageTransactionDetail;

@Repository
public class GroupMessageTransactionDetailDAO {

	@PersistenceContext
	EntityManager entityManager;
	
	@Transactional
	public void add(GroupMessageTransactionDetail groupMessageTransactionDetail) {
		entityManager.persist(groupMessageTransactionDetail);
	}
	@Transactional
	public void changeMessageStatusNotReaded(int id) {
		entityManager.createQuery("update GroupMessageTransactionDetail gd set gd.isReaded=false where gd.id=:id")
		.setParameter("id", id).executeUpdate();
	}

}
