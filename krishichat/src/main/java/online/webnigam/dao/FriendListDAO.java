package online.webnigam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import online.webnigam.dto.ApplicationUserDTO;
import online.webnigam.dto.ChatPeopleDTO;
import online.webnigam.entity.FriendList;
import online.webnigam.entity.User;

@Repository
public class FriendListDAO {

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public void addRequest(User fromUser, User toUser) {
		FriendList friend = new FriendList();
		friend.setFromId(fromUser);
		friend.setToId(toUser);
		entityManager.persist(friend);
	}

	public List<ApplicationUserDTO> getRequestableUser(int page, User user) {

		String query = "select new online.webnigam.dto.ApplicationUserDTO(u.id,u.name,u.email,u.profileImagePath) from User u where u.id not like :userId and (((select count(id) from FriendList where u.id like fromId.id and :userId like toId)<1)and(select count(id) from FriendList where u.id=toId and :userId like fromId)<1))";
		@SuppressWarnings("unchecked")
		List<ApplicationUserDTO> applicationUserDTOs = entityManager.createQuery(query)
				.setParameter("userId", user.getId()).getResultList();
		return applicationUserDTOs;
	}

	public List<ApplicationUserDTO> getFriendPendingUser(int page, User user) {
		String query = "select new online.webnigam.dto.ApplicationUserDTO(u.id,u.name,u.email,u.profileImagePath,f.createdAt) from User u left join FriendList f on u.id=f.toId where f.fromId like :fromId and enable=false";
		@SuppressWarnings("unchecked")
		List<ApplicationUserDTO> applicationUserDTOs = entityManager.createQuery(query).setParameter("fromId", user)
				.getResultList();
		return applicationUserDTOs;

	}

	public List<ApplicationUserDTO> getFriendRequestUser(int page, User user) {
		String query = "select new online.webnigam.dto.ApplicationUserDTO(u.id,u.name,u.email,u.profileImagePath,f.createdAt) from User u left join FriendList f on u.id=f.fromId where f.toId like :fromId and enable=false";
		@SuppressWarnings("unchecked")
		List<ApplicationUserDTO> applicationUserDTOs = entityManager.createQuery(query).setParameter("fromId", user)
				.getResultList();
		return applicationUserDTOs;
	}

	@Transactional
	public void acceptFriendRequest(User fromUser, User toUser) {
		entityManager.createQuery("update FriendList set enable=true where fromId=:fromUser and toId=:toUser")
				.setParameter("fromUser", fromUser).setParameter("toUser", toUser).executeUpdate();
	}

	public List<ApplicationUserDTO> getFriendList(User user) {
		String query = "select new online.webnigam.dto.ApplicationUserDTO(u.id,u.name,u.email,u.profileImagePath,f.createdAt) from User u left join FriendList f on u.id=f.fromId or u.id=f.toId where (f.fromId like :user or f.toId like :user) and enable=true and  u.id!=:userId";
		@SuppressWarnings("unchecked")
		List<ApplicationUserDTO> applicationUserDTOs = entityManager.createQuery(query)
				.setParameter("userId", user.getId()).setParameter("user", user).getResultList();
		return applicationUserDTOs;
	}

	@Transactional
	public void rejectFriendRequest(User fromUser, User toUser) {
		entityManager.createQuery(
				"delete from FriendList  where (fromId=:fromUser and toId=:toUser) or (fromId=:toUser and toId=:fromUser)")
				.setParameter("fromUser", fromUser).setParameter("toUser", toUser).executeUpdate();

		entityManager.createQuery(
				"delete from PersonalMessageTransaction  where (fromId=:fromUser and toId=:toUser) or (fromId=:toUser and toId=:fromUser)")
				.setParameter("fromUser", fromUser).setParameter("toUser", toUser).executeUpdate();

	}

	@Transactional
	public List<ChatPeopleDTO> getChatFriendList(User user) {
		String query = "select new online.webnigam.dto.ChatPeopleDTO(u.id,u.name,u.email,u.profileImagePath,u.activeTime) from User u left join FriendList f on u.id=f.fromId or u.id=f.toId  where (f.fromId like :user or f.toId like :user) and enable=true and  u.id!=:userId order by u.activeTime desc";
		@SuppressWarnings("unchecked")
		List<ChatPeopleDTO> chatPeopleDTOs = entityManager.createQuery(query).setParameter("userId", user.getId())
				.setParameter("user", user).getResultList();

		User fromUser = new User();
		for (ChatPeopleDTO chatPeopleDTO : chatPeopleDTOs) {
			String query2 = "select count(*) From PersonalMessageTransaction p where p.fromId=:fromUser and p.toId=:toUser and p.isReaded=false";
			fromUser.setId(chatPeopleDTO.getId());
			Long messageCount = (Long) entityManager.createQuery(query2).setParameter("fromUser", fromUser)
					.setParameter("toUser", user).getSingleResult();
			chatPeopleDTO.setCountMessage(messageCount);
		}
		return chatPeopleDTOs;
	}

	public boolean hasRequest(User fromUser, User toUser) {
		String query = "select count(*) from FriendList f where (f.fromId=:toUser and f.toId=:fromUser) or (f.fromId=:fromUser and f.toId=:toUser)";
		Long size = (Long) entityManager.createQuery(query).setParameter("fromUser", fromUser)
				.setParameter("toUser", toUser).getSingleResult();
		if (size != 0) {
			return true;
		}
		return false;
	}
	
	

}
