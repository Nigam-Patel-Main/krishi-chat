package online.webnigam.dto;

public class HomeDTO {
	private Long totalPersonalMessageCount;
	private Long totalNotificationCount;

	

	public HomeDTO(Long totalPersonalMessageCount, Long totalNotificationCount) {
		super();
		this.totalPersonalMessageCount = totalPersonalMessageCount;
		this.totalNotificationCount = totalNotificationCount;
	}

	public Long getTotalNotificationCount() {
		return totalNotificationCount;
	}

	public void setTotalNotificationCount(Long totalNotificationCount) {
		this.totalNotificationCount = totalNotificationCount;
	}

	public Long getTotalPersonalMessageCount() {
		return totalPersonalMessageCount;
	}

	public void setTotalPersonalMessageCount(Long totalPersonalMessageCount) {
		this.totalPersonalMessageCount = totalPersonalMessageCount;
	}

}
