package online.webnigam.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageDTO {
	public String image;


	@JsonCreator
	public ImageDTO(@JsonProperty("image")String image) {
		super();
		this.image = image;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	@Override
	public String toString() {
		return "ImageDTO [image=" + image + "]";
	}


	
	
	
}
