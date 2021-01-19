package ftn.kts.controller;

import ftn.kts.dto.ReviewDTO;
import ftn.kts.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewService service;

    @Autowired
    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<ReviewDTO>> getAllReviews(@RequestParam(defaultValue = "0") Integer pageNo,
                                                         @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
                                                         @RequestParam(defaultValue = "true") String descending) {
        Pageable paging;
        if (descending.equals("true"))
            paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
        else
            paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        Page<ReviewDTO> reviews = service.getAllDTO(paging);
        return new ResponseEntity<>(reviews.getContent(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable("id") long id) {
        return new ResponseEntity<>(service.getOneDTO(id), HttpStatus.OK);
    }

    @GetMapping("/search/{query}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<ReviewDTO>> searchReview(@PathVariable("query") String query, @RequestParam(defaultValue = "0") Integer pageNo,
                                                         @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
                                                         @RequestParam(defaultValue = "true") String descending) {
        Pageable paging;
        if (descending.equals("true"))
            paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
        else
            paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        Page<ReviewDTO> reviews = service.search(query, paging);
        return new ResponseEntity<>(reviews.getContent(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Object> addReview(@Valid @RequestBody ReviewDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Object> updateReview(@Valid @RequestBody ReviewDTO dto, @PathVariable long id) {
        ReviewDTO updated = service.update(dto, id);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Object> deleteReview(@PathVariable("id") long id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
