package ftn.kts.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

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
    	String fileName = new Date().getTime() + "-" + pictureRepository.getNextSeriesId();
    	
		byte[] data = file.getBytes();
		String fullPath = fileFolder + fileName + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
		Path path = Paths.get(projectFolder + fullPath);
		Files.write(path,  data);
		
		Picture picture = new Picture(fullPath, file.getOriginalFilename());
		pictureRepository.save(picture);
    }

	public PictureDTO getOneDTO(Long id) throws FileNotFoundException, IOException {
		Picture picture = getOne(id);
		PictureDTO dto = toDTO(picture);
		return dto;
	}

	public List<PictureDTO> getAllDTO() throws FileNotFoundException, IOException {
		List<Picture> pictures = pictureRepository.findAll();
		List<PictureDTO> dtos = new ArrayList<PictureDTO>();
		for (Picture p : pictures) {
			dtos.add(toDTO(p));
		}		
		return dtos;
	}

	public void delete(Long id) throws IOException {
		Picture picture = getOne(id);
		Files.delete(Paths.get(projectFolder + picture.getPath()));
		pictureRepository.delete(picture);
	}
	
	public Picture getOne(long id) {
		return pictureRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Picture with id " + id + " doesn't exist!"));
	}

	public List<Picture> getAll(long id) {
		return pictureRepository.findAll();
	}
	
	private PictureDTO toDTO(Picture entity) throws IOException {
		String encodedData = getEncodedPicture(entity.getPath());
		PictureDTO dto = new PictureDTO(entity.getId(), entity.getPlaceholder(), encodedData);
		return dto;
	}

	public Set<PictureDTO> convertToDTO(Set<Picture> pictures) {
		Set<PictureDTO> pics = new HashSet<>();
		try {
			for (Picture p : pictures)
				pics.add(toDTO(p));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pics;
	}
	
	private String getEncodedPicture(String path) throws IOException {
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
