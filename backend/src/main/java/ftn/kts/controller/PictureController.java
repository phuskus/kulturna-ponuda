package ftn.kts.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ftn.kts.dto.PictureDTO;
import ftn.kts.service.PictureService;

@RestController
@RequestMapping("/pictures")
public class PictureController {
	private PictureService service;

	@Autowired
	public PictureController(PictureService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<PictureDTO>> getAllPictures() throws FileNotFoundException, IOException {
		List<PictureDTO> offers = service.getAllDTO();
		return new ResponseEntity<>(offers, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PictureDTO> getPicture(@PathVariable("id") long id) throws FileNotFoundException, IOException {
		return new ResponseEntity<>(service.getOneDTO(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Object> addPicture(@RequestParam(name = "file") MultipartFile file) throws IOException {
		service.add(file);
		return new ResponseEntity<>("Successfully added picture!", HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletePicture(@PathVariable("id") long id) throws IOException {
		service.delete(id);
		return new ResponseEntity<>("Successfully deleted picture!", HttpStatus.OK);
	}

}
