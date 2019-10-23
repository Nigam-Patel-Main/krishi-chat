package online.webnigam.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.webnigam.dao.GroupMemberDAO;
import online.webnigam.dto.ApplicationUserDTO;
import online.webnigam.dto.ChatPeopleDTO;
import online.webnigam.dto.GGroupDTO;
import online.webnigam.entity.GGroup;
import online.webnigam.entity.GroupMember;
import online.webnigam.entity.Notification;
import online.webnigam.entity.User;

@Service
public class GroupMemberService {

	@Autowired
	GroupMemberDAO memberDAO;

	@Autowired
	UserService userService;

	@Autowired
	FriendListService friendListService;

	@Autowired
	ChatService chatService;

	public void addMember(GroupMember groupMember) {
		memberDAO.addMember(groupMember);
	}

	public List<ChatPeopleDTO> getGroupList(User user) {
		return memberDAO.getGroups(user);
	}

	public List<ApplicationUserDTO> getGroupMemberList(String groupId, String fromId) {
		GGroup group = new GGroup();
		group.setId(groupId);
		User user = userService.buildUserFromId(fromId);

		return memberDAO.getGroupMemberList(group, user);
	}

	public List<ApplicationUserDTO> getFriendListExcludeGroupMember(String email, String groupId) {
		User user = new User();
		user.setId(userService.getIdByEmail(email));
		user.setEmail(email);

		GGroup group = new GGroup();
		group.setId(groupId);

		List<String> groupMemberIds = memberDAO.getGroupMemberIds(group, user);
		List<ApplicationUserDTO> friendList = friendListService.getFriendList(user);
		for (ApplicationUserDTO friend : friendList) {
			if (groupMemberIds.contains(friend.getId())) {
				friend.setFlag(true);
			}
		}
		return friendList;
	}

	public void editGroupMember(GGroupDTO gGroupDTO) {
		GGroup group = new GGroup();
		group.setId(gGroupDTO.getGroupId());
		GroupMember groupMember = null;
		if (!gGroupDTO.getFriends().equals("")) {
			List<String> friends = new ArrayList<String>(Arrays.asList(gGroupDTO.getFriends().split(",")));
			String fromEmail = userService.getEmailById(gGroupDTO.getLeaderId());
			for (String friend : friends) {
				String[] split = friend.trim().split(" ");
				groupMember = new GroupMember();
				groupMember.setEnable(true);
				groupMember.setGroupId(group);
				groupMember.setToId(userService.buildUserFromId(split[0]));
				addMember(groupMember);
				chatService.addNotification("", split[1], " added to you in " + group.getName() + " Group",
						Notification.MEMBEROFGROUP, fromEmail);
			}
		}

	}

	public void deleteGroupMember(GGroupDTO gGroupDTO) {
		GGroup group = new GGroup();
		group.setId(gGroupDTO.getGroupId());

		// Status as a id of member
		User user = userService.buildUserFromId(gGroupDTO.getStatus());

		memberDAO.deleteGroupMember(group, user);
		// friends as a member email
		chatService.addNotification("", gGroupDTO.getFriends(), " removed  you from " + group.getName() + " Group",
				Notification.MEMBEROFGROUP, gGroupDTO.getLeaderId());
	}

	public GGroup getGroupInfo(String groupId) {
		GGroup group = memberDAO.getGroupInfo(groupId);
		return group;
	}

	public List<String> getGroupMemberIds(GGroup group, User user) {
		return memberDAO.getGroupMemberIds(group, user);
	}

	public List<String> getGroupMemberEmials(GGroup group, User user) {
		return memberDAO.getGroupMemberEmails(group, user);
	}

	public List<ApplicationUserDTO> getGroupMember(String groupId) {
		GGroup group = new GGroup();
		group.setId(groupId);
		return memberDAO.getGroupMember(group);

	}
}
