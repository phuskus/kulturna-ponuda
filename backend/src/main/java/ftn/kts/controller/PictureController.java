package ftn.kts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ftn.kts.service.PictureService;

@RestController
@RequestMapping("/pictures")
public class PictureController {
	private PictureService service;

	@Autowired
	public PictureController(PictureService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Object> addPicture(@RequestParam(name = "file") MultipartFile file) {
		try {
			service.addPicture(file);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
