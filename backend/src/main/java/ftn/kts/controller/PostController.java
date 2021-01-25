package ftn.kts.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ftn.kts.dto.CulturalOfferDTO;
import ftn.kts.dto.PostDTO;
import ftn.kts.service.PostService;

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
	public ResponseEntity<Page<PostDTO>> getAllPosts(@RequestParam(defaultValue = "0") Integer pageNo,
													 @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "datePosted") String sortBy,
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
	public ResponseEntity<PostDTO> getPost(@PathVariable("id") long id) {
		return new ResponseEntity<>(service.getOneDTO(id), HttpStatus.OK);
	}

	@GetMapping("/offer/{id}")
	public ResponseEntity<Page<PostDTO>> getPostsForOffer(@PathVariable("id") long offerId, @RequestParam(defaultValue = "0") Integer pageNo,
			 @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "datePosted") String sortBy,
			 @RequestParam(defaultValue = "true") String descending) {
		Pageable paging;
		if (descending.equals("true"))
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
		Page<PostDTO> posts = service.getForOffer(offerId, paging);
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<Page<PostDTO>> filterPosts(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "datePosted") String sortBy, @RequestParam(defaultValue = "true") String descending, @RequestParam(defaultValue = "") String query) {
		Pageable paging;
		if (descending.equals("true"))
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Direction.DESC, sortBy));
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Direction.ASC, sortBy));
		Page<PostDTO> page = service.filter(query, paging);
		return new ResponseEntity<>(page, HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PostDTO> addPost(@Valid @RequestParam String post, @RequestParam(value = "files", required = false) MultipartFile[] files) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		PostDTO dto = mapper.readValue(post, PostDTO.class);
		PostDTO created = service.create(dto, files);
		return new ResponseEntity<>(created, HttpStatus.OK);
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
