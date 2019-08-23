package online.webnigam.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import online.webnigam.entity.GGroup;

@Repository
public class GGroupDAO {

	@PersistenceContext
	EntityManager entityManager;
	
	@Transactional
	public void addGroup(GGroup group)
	{
		entityManager.persist(group);
	}

	@Transactional
	public void updateGroup(GGroup group) {
		entityManager.merge(group);
	}

	public String getProfileImageById(String groupId) {
		String profileImagePath = (String)entityManager.createQuery("select profileImagePath from GGroup where id=:groupId").setParameter("groupId", groupId).getSingleResult();
		return profileImagePath;
	}
}
