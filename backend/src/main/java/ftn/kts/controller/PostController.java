package ftn.kts.controller;

import ftn.kts.dto.PostDTO;
import ftn.kts.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<Page<PostDTO>> getAllPosts(@RequestParam(defaultValue = "0") Integer pageNo,
													 @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
													 @RequestParam(defaultValue = "true") String descending) {
		Pageable paging;
		if (descending.equals("true"))
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
		Page<PostDTO> posts = service.getAllDTO(paging);
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<PostDTO> getPost(@PathVariable("id") long id) {
		return new ResponseEntity<>(service.getOneDTO(id), HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> addPost(@Valid @RequestBody PostDTO dto) {
		service.create(dto);
		return new ResponseEntity<>("Successfully added post!", HttpStatus.OK);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO dto, @PathVariable("id") Long id) {
		PostDTO updated = service.update(dto, id);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deletePost(@PathVariable("id") long id) {
		service.delete(id);
		return new ResponseEntity<>("Successfully deleted post!", HttpStatus.OK);
	}

}
