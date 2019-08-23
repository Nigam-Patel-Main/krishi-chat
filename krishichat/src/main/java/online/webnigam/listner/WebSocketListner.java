package online.webnigam.listner;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import online.webnigam.entity.ParticipantRepository;
import online.webnigam.service.ChatService;
import online.webnigam.service.UserService;

@Component
public class WebSocketListner {

	@Autowired
	ParticipantRepository participantRepository;

	@Autowired
	UserService userService;

	@Autowired
	ChatService chatService;

	@Autowired
	SimpMessagingTemplate messageTemplet;

	@EventListener
	private void handleSessionConnected(SessionConnectEvent event) {
	}

	@EventListener
	private void handleSessionDisconnect(SessionDisconnectEvent event) throws ParseException {
	}

}
