package ftn.kts.controller;

import ftn.kts.dto.PostDTO;
import ftn.kts.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/posts")
public class PostController {

	private PostService service;

	@Autowired
	public PostController(PostService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<PostDTO>> getAllPosts() {
		List<PostDTO> posts = service.getAllDTO();
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> getPost(@PathVariable("id") long id) {
		return new ResponseEntity<>(service.getOneDTO(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<String> addPost(@Valid @RequestBody PostDTO dto) {
		service.create(dto);
		return new ResponseEntity<>("Successfully added post!", HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO dto, @PathVariable("id") Long id) {
		PostDTO updated = service.update(dto, id);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable("id") long id) {
		service.delete(id);
		return new ResponseEntity<>("Successfully deleted post!", HttpStatus.OK);
	}

}
