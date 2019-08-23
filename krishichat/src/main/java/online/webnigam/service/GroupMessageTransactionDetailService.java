package online.webnigam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.event.PublicInvocationEvent;
import org.springframework.stereotype.Service;

import online.webnigam.dao.GroupMessageTransactionDetailDAO;
import online.webnigam.entity.GroupMessageTransaction;
import online.webnigam.entity.GroupMessageTransactionDetail;

@Service
public class GroupMessageTransactionDetailService {

	@Autowired
	GroupMessageTransactionDetailDAO groupMessageTransactionDetailDAO;
	
	@Autowired
	UserService userService;
	
	public int add(String fromId, GroupMessageTransaction messageTransaction, boolean isReaded) {
		GroupMessageTransactionDetail groupMessageTransactionDetail=new GroupMessageTransactionDetail();
		groupMessageTransactionDetail.setUserId(userService.buildUserFromId(fromId));
		groupMessageTransactionDetail.setGroupMessageTransaction(messageTransaction);
		groupMessageTransactionDetail.setReaded(isReaded);
		
		groupMessageTransactionDetailDAO.add(groupMessageTransactionDetail);
		
		return groupMessageTransactionDetail.getId(); 
		
	}

	
	public void changeMessageStatusNotReaded(int messageId) {

		groupMessageTransactionDetailDAO.changeMessageStatusNotReaded(messageId);
	}
	
}
