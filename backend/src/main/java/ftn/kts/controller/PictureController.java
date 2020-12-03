package ftn.kts.controller;

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
	public ResponseEntity<List<PictureDTO>> getAllPictures() {
		try {
			List<PictureDTO> offers = service.getAll();
			return new ResponseEntity<>(offers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<PictureDTO> getPicture(@PathVariable("id") long id) {
		try {
			return new ResponseEntity<>(service.getOne(id), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping
	public ResponseEntity<Object> addPicture(@RequestParam(name = "file") MultipartFile file) {
		try {
			service.add(file);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletePicture(@PathVariable("id") long id) {
		try {
			service.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
