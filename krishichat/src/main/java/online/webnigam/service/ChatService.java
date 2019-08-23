package online.webnigam.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import online.webnigam.dao.GroupMessageTransactionDAO;
import online.webnigam.dao.PersonalMessageTransactionDAO;
import online.webnigam.dto.ActiveUserStore;
import online.webnigam.dto.ApplicationUserDTO;
import online.webnigam.dto.ChatPeopleDTO;
import online.webnigam.dto.GGroupDTO;
import online.webnigam.dto.HomeDTO;
import online.webnigam.dto.LoadMessagesDTO;
import online.webnigam.entity.GGroup;
import online.webnigam.entity.GroupMember;
import online.webnigam.entity.GroupMessageTransaction;
import online.webnigam.entity.Message;
import online.webnigam.entity.Notification;
import online.webnigam.entity.PersonalMessageTransaction;
import online.webnigam.entity.User;

@Service
public class ChatService {

	@Autowired
	FriendListService friendListService;

	@Autowired
	UserService userService;

	@Autowired
	ActiveUserStore activeUserStore;

	@Autowired
	MessageService messageService;

	@Autowired
	NotificationService notificationService;

	@Autowired
	PersonalMessageTransactionDAO personalMessageTransactionDAO;

	@Autowired
	GroupMessageTransactionDAO groupMessageTransactionDAO;

	@Autowired
	GGroupService groupService;

	@Autowired
	GroupMemberService groupMemberService;

	@Autowired
	GroupMessageTransactionService groupMessageTransactionService;

	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;

	public List<ChatPeopleDTO> getGroupAndFriendWithMessaggeCount(User user) throws ParseException {
		List<ChatPeopleDTO> mergedList = new ArrayList<>();
		mergedList.addAll(friendListService.getChatFriendList(user));
		mergedList.addAll(groupMemberService.getGroupList(user));
		return mergedList;

	}

	public void sendToAllFriendOnlineMessage(String fromEmail) throws ParseException {
		User fromUser = userService.buildUserFromEmail(fromEmail);
		List<String> users = activeUserStore.getUsers();
		List<ChatPeopleDTO> chatFriendList = friendListService.getChatFriendList(fromUser);
		for (ChatPeopleDTO chatPeopleDTO : chatFriendList) {
			if (users.contains(chatPeopleDTO.getEmail())) {
				simpMessagingTemplate.convertAndSendToUser(chatPeopleDTO.getEmail(), "/broker/setUserOnline",
						fromUser.getId());
				simpMessagingTemplate.convertAndSendToUser(fromEmail, "/broker/setUserOnline", chatPeopleDTO.getId());
			}
		}
	}

	public void sendToAllFriendOflineMessage(String fromEmail) throws ParseException {
		User fromUser = userService.findByEmail(fromEmail);

		// Get currently online sessions
		List<String> users = activeUserStore.getUsers();

		// Get all Friend
		List<ChatPeopleDTO> chatFriendList = friendListService.getChatFriendList(fromUser);

		// create own ChatPeopleDTO for time ago
		ChatPeopleDTO fromDTO = new ChatPeopleDTO(fromUser.getId(), fromUser.getName(), fromUser.getEmail(),
				fromUser.getEmail(), fromUser.getActiveTime(), 0L);

		// send to all active user i am offline
		for (ChatPeopleDTO chatPeopleDTO : chatFriendList) {
			if (users.contains(chatPeopleDTO.getEmail())) {
				simpMessagingTemplate.convertAndSendToUser(chatPeopleDTO.getEmail(), "/broker/setUserOffline", fromDTO);
			}
		}
	}

	public List<LoadMessagesDTO> getAllPersonalChat(String fromId, String toId) {

		// create User from email and id
		User fromUser = userService.buildUserFromId(fromId);
		if (toId.substring(0, 4).equals("USER")) {
			User toUser = userService.buildUserFromId(toId);
			return personalMessageTransactionDAO.getAllPersonalChat(fromUser, toUser);
		} else {
			GGroup group = new GGroup();
			group.setId(toId);
			return groupMessageTransactionDAO.getAllGroupChat(fromUser, group);
		}

	}

	public int addMessage(String fromId, String toId, String message, String path, boolean isReaded) {

		User fromUser = userService.buildUserFromId(fromId);
		User toUser = userService.buildUserFromId(toId);

		Message message2 = new Message();
		message2.setMessage(message);
		message2.setType(Message.Text);
		message2.setPath(path);
		messageService.add(message2);

		PersonalMessageTransaction messageTransaction = new PersonalMessageTransaction();
		messageTransaction.setFromId(fromUser);
		messageTransaction.setToId(toUser);
		messageTransaction.setReaded(isReaded);
		messageTransaction.setMessage(message2);

		personalMessageTransactionDAO.add(messageTransaction);
		return messageTransaction.getId();
	}

	public GroupMessageTransaction addMessageInGroup(String fromId, String toId, String message, String path) {

		User fromUser = userService.buildUserFromId(fromId);
		GGroup group = new GGroup();
		group.setId(toId);

		Message message2 = new Message();
		message2.setMessage(message);
		message2.setType(Message.Text);
		message2.setPath(path);
		messageService.add(message2);

		GroupMessageTransaction groupMessageTransaction = new GroupMessageTransaction();
		groupMessageTransaction.setGroupId(group);
		groupMessageTransaction.setFromId(fromUser);
		groupMessageTransaction.setMessage(message2);

		groupMessageTransactionService.add(groupMessageTransaction);

		return groupMessageTransaction;
	}

	public void changePersonalMessageStatusNotReaded(int messageId) {
		personalMessageTransactionDAO.changePersonalMessageStatus(messageId);
	}

	public void changePersonalMessageStatusAllReaded(String fromId, String toId) {
		User fromUser = userService.buildUserFromId(fromId);
		User toUser = userService.buildUserFromId(toId);
		personalMessageTransactionDAO.changePersonalMessageStatusAllReaded(fromUser, toUser);
	}

	public HomeDTO getHomeDTO(String email) {
		User fromUser = userService.buildUserFromEmail(email);
		Long totalUnreadPersonalMessageCount = personalMessageTransactionDAO.getTotalUnreadMessageCount(fromUser);
		Long totalUnreadGroupMessageCount = groupMessageTransactionDAO.getTotalUnreadMessageCount(fromUser);
		Long totalUnreadNotificationCount = notificationService.getTotalUnreadNotificationCount(fromUser);
		HomeDTO homeDTO = new HomeDTO(totalUnreadGroupMessageCount + totalUnreadPersonalMessageCount,
				totalUnreadNotificationCount);
		return homeDTO;
	}

	public void changeStatusNotificationsReaded(String email) {
		User fromUser = userService.buildUserFromEmail(email);
		notificationService.changeNotificationStatusAllReaded(fromUser, true);
	}

	public void addNotification(String id, String email, String message, String purpose, String fromEmail) {
		User user = userService.buildUserFromEmail(email);
		String name = userService.getNameByEmail(fromEmail);

		Notification notification = new Notification();
		notification.setMessage(name + message);
		notification.setUser(user);
		notification.setPurpose(purpose);

		notificationService.add(notification);

		simpMessagingTemplate.convertAndSendToUser(email, "/broker/receiveNotification", notification);
	}

	public List<ApplicationUserDTO> getFriendList(String email) {
		User user = userService.buildUserFromEmail(email);
		return friendListService.getFriendList(user);
	}

	public GGroup addGroupWithMember(GGroupDTO gGroupDTO) {
		GGroup group = new GGroup();
		group.setName(gGroupDTO.getName());
		group.setStatus(gGroupDTO.getStatus());
		groupService.addGroup(group);
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
				groupMemberService.addMember(groupMember);
				addNotification("", split[1], " added to you in " + group.getName() + " Group",
						Notification.MEMBEROFGROUP, fromEmail);
			}
		}
		groupMember = new GroupMember();
		groupMember.setEnable(true);
		groupMember.setIsLeader(true);
		groupMember.setGroupId(group);

		User user = userService.buildUserFromId(gGroupDTO.getLeaderId());
		groupMember.setToId(user);
		groupMemberService.addMember(groupMember);

		return group;
	}

	public void changeAllGroupMessageStatusAllReaded(String groupId, String fromId) {
		groupMessageTransactionService.changeAllGroupMessageStatusAllReaded(groupId, fromId);
	}

}
