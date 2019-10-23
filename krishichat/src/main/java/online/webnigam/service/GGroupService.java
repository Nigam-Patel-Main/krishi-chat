package online.webnigam.service;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.webnigam.dao.GGroupDAO;
import online.webnigam.dto.GGroupDTO;
import online.webnigam.entity.GGroup;
import online.webnigam.entity.Notification;

@Service
public class GGroupService {

	@Autowired
	GGroupDAO groupDAO;

	@Autowired
	ServletContext servletContext;

	@Autowired
	GroupMemberService memberService;

	@Autowired
	ImageService imageService;

	@Autowired
	UserService userService;

	@Autowired
	ChatService chatService;

	public void addGroup(GGroup group) {
		groupDAO.addGroup(group);
	}

	public void editGroupDetail(GGroupDTO groupDTO) {

		System.out.println("GROUP DTO is " + groupDTO);
		GGroup group = new GGroup();
		System.out.println("File value is " + groupDTO.getFile());
		if (groupDTO.getFile() != null) {
			String location = servletContext.getRealPath("/") + File.separator + "resources" + File.separator
					+ "profileImage";

			String path = imageService.storeImage(groupDTO.getFile(), location);
			System.out.println("Store new image with " + path);
			group.setProfileImagePath(path);
		} else {
			String path = groupDAO.getProfileImageById(groupDTO.getGroupId());
			group.setProfileImagePath(path);
		}
		group.setId(groupDTO.getGroupId());
		group.setName(groupDTO.getName());
		group.setStatus(groupDTO.getStatus());
		groupDAO.updateGroup(group);

		List<String> groupMemberEmails = memberService.getGroupMemberEmials(group,
				userService.buildUserFromId(groupDTO.getLeaderId()));
		String fromEmail = userService.getEmailById(groupDTO.getLeaderId());
		for (String memberEmail : groupMemberEmails) {
			chatService.addNotification("", memberEmail, " change group detail of " + group.getName() + " Group",
					Notification.MEMBEROFGROUP, fromEmail);
		}
	}
}
