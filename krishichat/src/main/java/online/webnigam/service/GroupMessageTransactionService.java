package online.webnigam.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.webnigam.dao.GroupMessageTransactionDAO;
import online.webnigam.entity.GGroup;
import online.webnigam.entity.GroupMessageTransaction;
import online.webnigam.entity.User;

@Service
public class GroupMessageTransactionService {

	@Autowired
	GroupMessageTransactionDAO groupMessageTransactionDAO;
	
	@Autowired
	UserService userService;
	
	public void add(GroupMessageTransaction groupMessageTransaction) {
		
		groupMessageTransactionDAO.add(groupMessageTransaction);
	}

	@Transactional
	public void changeAllGroupMessageStatusAllReaded(String groupId, String fromId) {
		GGroup group=new GGroup();
		group.setId(groupId);
		
		User user = userService.buildUserFromId(fromId);
		groupMessageTransactionDAO.changeAllGroupMessageStatusAllReaded(group,user);
	}

}
