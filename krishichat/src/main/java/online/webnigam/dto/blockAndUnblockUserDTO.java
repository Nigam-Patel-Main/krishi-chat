package online.webnigam.dto;

public class blockAndUnblockUserDTO {
	private String id;
	private String name;
	private String email;
	private boolean isBlock;

	public blockAndUnblockUserDTO(String id, String name, String email, boolean isBlock) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.isBlock = isBlock;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getIsBlock() {
		return isBlock;
	}

	public void setIsBlock(boolean isBlock) {
		this.isBlock = isBlock;
	}

	@Override
	public String toString() {
		return "blockAndUnblockUserDTO [id=" + id + ", name=" + name + ", email=" + email + ", isBlock=" + isBlock
				+ "]";
	}

}
