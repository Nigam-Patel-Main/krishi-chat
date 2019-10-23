package online.webnigam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.webnigam.dao.NotificationDAO;
import online.webnigam.dto.NotificationDTO;
import online.webnigam.entity.Notification;
import online.webnigam.entity.User;

@Service
public class NotificationService {

	@Autowired
	NotificationDAO notificationDAO;
	
	@Autowired
	UserService userService;
	public void add(Notification notification)
	{
		notificationDAO.add(notification);
	}
	
	public Long getTotalUnreadNotificationCount(User user) {
		
		return notificationDAO.getTotalUnreadNotificationCount(user);
	}
	
	public void changeNotificationStatusAllReaded(User user, boolean isReaded) {
		notificationDAO.changeNotificationStatusAllReaded(user, true);
	}

	public List<NotificationDTO> getAllNotifications(String email) {
		User user=userService.buildUserFromEmail(email);
		return notificationDAO.getAllNotifications(user);
	}
}
