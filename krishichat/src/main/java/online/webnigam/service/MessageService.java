package online.webnigam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.webnigam.dao.MessageDAO;
import online.webnigam.entity.Message;

@Service
public class MessageService {

	@Autowired
	MessageDAO messageDAO;

	public void add(Message message) {
		messageDAO.add(message);
	}

	public Message findById(int id) {
		return messageDAO.findById(id);
	}
}
