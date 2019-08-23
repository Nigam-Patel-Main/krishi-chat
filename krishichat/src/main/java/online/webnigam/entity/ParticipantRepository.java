package online.webnigam.entity;

import java.util.Map;

import javax.persistence.Table;

import org.springframework.stereotype.Component;

import online.webnigam.dto.ChatPeopleDTO;

@Component
@Table(name="participantrepository")
public class ParticipantRepository {
	private Map<String, ChatPeopleDTO> chatPeopleDTOs;

	public void addChatPeopleDTO(String email, ChatPeopleDTO chatPeopleDTO) {
		chatPeopleDTOs.put(email, chatPeopleDTO);
	}

	public void removeChatPeopleDTOByEmail(String email) {
		chatPeopleDTOs.remove(email);
	}

	public ChatPeopleDTO geChatPeopleDTOByEmail(String email) {
		return chatPeopleDTOs.get(email);
	}

	public Map<String, ChatPeopleDTO> getChatPeopleDTOs() {
		return chatPeopleDTOs;
	}

	public void setChatPeopleDTOs(Map<String, ChatPeopleDTO> chatPeopleDTOs) {
		this.chatPeopleDTOs = chatPeopleDTOs;
	}

}
