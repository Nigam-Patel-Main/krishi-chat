package online.webnigam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import online.webnigam.dto.ApplicationUserDTO;
import online.webnigam.dto.ChatPeopleDTO;
import online.webnigam.entity.GGroup;
import online.webnigam.entity.GroupMember;
import online.webnigam.entity.User;

@Repository
public class GroupMemberDAO {

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public void addMember(GroupMember groupMember) {
		entityManager.persist(groupMember);
	}

	
	public List<ChatPeopleDTO> getGroups(User user) {
		String query = "select new online.webnigam.dto.ChatPeopleDTO(g.id,g.name,g.profileImagePath,m.isLeader) from GGroup g left join GroupMember m on g.id=m.groupId where m.toId like :user and m.enable=true";
		@SuppressWarnings("unchecked")
		List<ChatPeopleDTO> chatPeopleDTOs = entityManager.createQuery(query).setParameter("user", user)
				.getResultList();

		User toUser = new User();
		GGroup group=new GGroup();
		for (ChatPeopleDTO chatPeopleDTO : chatPeopleDTOs) {
			String query2 = "select count(*) From GroupMessageTransaction g left join GroupMessageTransactionDetail gd on g.id=gd.groupMessageTransaction where g.groupId=:groupId and gd.userId=:toUser and gd.isReaded=false";
			toUser.setId(user.getId());
			group.setId(chatPeopleDTO.getId());
			
			Long messageCount = (Long) entityManager.createQuery(query2).setParameter("toUser", toUser)
					.setParameter("groupId", group).getSingleResult();
			chatPeopleDTO.setCountMessage(messageCount);
		}
		return chatPeopleDTOs;
	}
	public List<ApplicationUserDTO> getGroupMemberList(GGroup group,User user) {
		String query = "select new online.webnigam.dto.ApplicationUserDTO(u.id,u.email) from User u left join GroupMember m on u.id=m.toId where m.groupId like :group and m.enable=true and u.id!=:userId";
		@SuppressWarnings("unchecked")
		List<ApplicationUserDTO> groupMembers  = entityManager.createQuery(query).setParameter("group", group).setParameter("userId",user.getId())
				.getResultList();
		return groupMembers;
	}
	
	public List<String> getGroupMemberIds(GGroup group,User user) {
		String query = "select u.id from User u left join GroupMember m on u.id=m.toId where m.groupId like :group and m.enable=true and u.id!=:userId";
		@SuppressWarnings("unchecked")
		List<String> groupMembersIds  = entityManager.createQuery(query).setParameter("group", group).setParameter("userId",user.getId())
				.getResultList();
		return groupMembersIds;
	}
	
	public List<String> getGroupMemberEmails(GGroup group,User user) {
		String query = "select u.email from User u left join GroupMember m on u.id=m.toId where m.groupId like :group and m.enable=true and u.id!=:userId";
		@SuppressWarnings("unchecked")
		List<String> groupMembersIds  = entityManager.createQuery(query).setParameter("group", group).setParameter("userId",user.getId())
				.getResultList();
		return groupMembersIds;
	}

	@Transactional
	public void deleteGroupMember(GGroup group, User user) {
		entityManager.createQuery("delete from GroupMember where groupId=:group and toId=:user").setParameter("group", group).setParameter("user", user).executeUpdate();
	}


	public GGroup getGroupInfo(String groupId) {
		return  entityManager.find(GGroup.class, groupId);
	}


	public List<ApplicationUserDTO> getGroupMember(GGroup group) {
		String query = "select new online.webnigam.dto.ApplicationUserDTO(u.id,u.name,u.email,u.profileImagePath,m.isLeader)  from User u left join GroupMember m on u.id=m.toId where m.groupId like :group and m.enable=true";
		@SuppressWarnings("unchecked")
		List<ApplicationUserDTO> groupMembers = entityManager.createQuery(query).setParameter("group", group)
				.getResultList();
		return groupMembers;
	}


	


}
