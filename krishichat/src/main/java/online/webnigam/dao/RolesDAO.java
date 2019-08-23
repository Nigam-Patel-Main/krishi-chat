package online.webnigam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import online.webnigam.entity.Roles;

@Repository
public class RolesDAO {

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public void addRole(Roles roles) {
		entityManager.persist(roles);
	}

	@Transactional
	public void deleteRole(Roles roles) {
		entityManager.remove(roles);
	}

	@Transactional
	public void updateRole(Roles roles) {
		entityManager.merge(roles);
	}

	@Transactional
	public Roles findById(int id) {
		return entityManager.find(Roles.class, id);
	}

	@Transactional
	public List<Roles> list() {
		return (List<Roles>) entityManager.createQuery("From Roles").getResultList();

	}
}
