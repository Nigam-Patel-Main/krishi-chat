package online.webnigam.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
	public boolean isImageValidForProfile(MultipartFile file) {

		List<String> extentions = new ArrayList<>();
		extentions.add("image/jpeg");
		extentions.add("image/jpg");
		extentions.add("image/png");

		if (!extentions.contains(file.getContentType())) {
			return false;
		}
		return true;
	}

	public String storeImage(MultipartFile file, String pathToStore) {
		// create folder profile Image if not exist
		File folderFile = new File(pathToStore);
		if (!folderFile.exists())
			folderFile.mkdir();

		// Storing file
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());

		File file2 = new File(
				folderFile.getAbsolutePath() + File.separator + UUID.randomUUID().toString() + "." + extension);
		try {
			if (!file2.exists())
				file2.createNewFile();
			OutputStream outputStream = new FileOutputStream(file2);
			outputStream.write(file.getBytes());
			outputStream.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error in storing image in imageService");
		}
		return file2.getName();
	}

	public String storeImage(String base64String, String pathToStore) {

		File folderFile = new File(pathToStore);
		if (!folderFile.exists())
			folderFile.mkdir();

		String[] strings = base64String.split(",");
		String extension = "jpg";
		switch (strings[0]) {
		case "data:image/jpeg;base64":
			extension = "jpeg";
			break;
		case "data:image/png;base64":
			extension = "png";
			break;
		case "data:image/gif;base64":
			extension = "gif";
			break;
		case "data:audio/mp3;base64":
			extension = "mp3";
			break;
		case "data:video/mp4;base64":
			extension = "mp4";
			break;
		default:
			extension = "jpg";
			break;
		}
		byte[] data = null;
		if (strings.length > 1) {
			data = DatatypeConverter.parseBase64Binary(strings[1]);
		} else {
			data = DatatypeConverter.parseBase64Binary("jkADSCVC");
		}

		File file2 = new File(
				folderFile.getAbsolutePath() + File.separator + UUID.randomUUID().toString() + "." + extension);

		try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file2))) {
			outputStream.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file2.getName();
	}

	public void deleteImage(String imageName, String location) throws IOException {
		File folderFile = new File(location);
		if (!folderFile.exists())
			folderFile.mkdir();

		File file = new File(folderFile.getAbsolutePath() + File.separator + imageName);
		if (!imageName.equals("defaultProfile.png")) {
			Files.deleteIfExists(file.toPath());
		}

	}
}
