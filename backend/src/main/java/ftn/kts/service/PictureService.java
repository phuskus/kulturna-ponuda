package ftn.kts.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ftn.kts.dto.PictureDTO;
import ftn.kts.model.Picture;
import ftn.kts.repository.PictureRepository;

@Service
public class PictureService {
	private final String projectFolder = System.getProperty("user.dir");
	private final String fileFolder = "\\pictures\\";
	
    private PictureRepository pictureRepository;

    @Autowired
    public PictureService(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }
    
    public void add(MultipartFile file) throws IOException {
		byte[] data = file.getBytes();
		String fullPath = fileFolder + file.getOriginalFilename();
		Path path = Paths.get(projectFolder + fullPath);
		Files.write(path,  data);
		
		Picture picture = new Picture(fullPath, file.getOriginalFilename());
		pictureRepository.save(picture);
    }

	public PictureDTO getOne(Long id) throws FileNotFoundException, IOException {
		Picture picture = pictureRepository.findById(id).get();
		PictureDTO dto = toDTO(picture);
		return dto;
	}

	public List<PictureDTO> getAll() throws FileNotFoundException, IOException {
		List<Picture> pictures = pictureRepository.findAll();
		List<PictureDTO> dtos = new ArrayList<PictureDTO>();
		for (Picture p : pictures) {
			dtos.add(toDTO(p));
		}		
		return dtos;
	}
	
	public void delete(Long id) throws IOException {
		Picture picture = pictureRepository.findById(id).get();
		Files.delete(Paths.get(projectFolder + picture.getPath()));
		pictureRepository.delete(picture);
	}
	
	private PictureDTO toDTO(Picture entity) throws FileNotFoundException, IOException {
		String encodedData = getEncodedPicture(entity.getPath());
		PictureDTO dto = new PictureDTO(entity.getId(), entity.getPlaceholder(), encodedData);
		return dto;
	}
	
	private String getEncodedPicture(String path) throws FileNotFoundException, IOException {
		String filePath = projectFolder + path;
		File f = new File(filePath);
		FileInputStream fis = new FileInputStream(f);
		byte byteArray[] = new byte[(int)f.length()];
		fis.read(byteArray);
		String imageString = Base64.encodeBase64String(byteArray);
		String ext = path.substring(path.lastIndexOf(".") + 1);
		imageString = "data:image/" + ext + ";base64," + imageString;
		fis.close();
		return imageString;
	}
}
