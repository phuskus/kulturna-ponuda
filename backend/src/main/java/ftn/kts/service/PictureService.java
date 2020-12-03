package ftn.kts.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ftn.kts.model.Picture;
import ftn.kts.repository.PictureRepository;

@Service
public class PictureService {
	private final String fileFolder = "\\pictures\\";
	
    private PictureRepository pictureRepository;

    @Autowired
    public PictureService(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }
    
    public void addPicture(MultipartFile file) throws IOException {
		byte[] data = file.getBytes();
		String fullPath = fileFolder + file.getOriginalFilename();
		Path path = Paths.get(System.getProperty("user.dir") + fullPath);
		Files.write(path,  data);
		
		Picture picture = new Picture(fullPath, file.getOriginalFilename());
		pictureRepository.save(picture);
    }

	public Picture getOne(Long id) {
		return pictureRepository.findById(id).get();
	}

	public List<Picture> getAll() {
		return pictureRepository.findAll();
	}
}
