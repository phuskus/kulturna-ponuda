package ftn.kts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
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
    public ResponseEntity<Page<ReviewDTO>> getAllReviews(@RequestParam(defaultValue = "0") Integer pageNo,
                                                         @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "datePosted") String sortBy,
                                                         @RequestParam(defaultValue = "true") String descending) {
        Pageable paging;
        if (descending.equals("true"))
            paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
        else
            paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        Page<ReviewDTO> reviews = service.getAllDTO(paging);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable("id") long id) {
        return new ResponseEntity<>(service.getOneDTO(id), HttpStatus.OK);
    }

    @GetMapping("/offer/{id}")
    public ResponseEntity<Page<ReviewDTO>> getForCulturalOffer(@PathVariable("id") long id, @RequestParam(defaultValue = "0") Integer pageNo,
                                                               @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "datePosted") String sortBy,
                                                               @RequestParam(defaultValue = "true") String descending) {

        Pageable paging;
        if (descending.equals("true"))
            paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
        else
            paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        Page<ReviewDTO> reviews = service.getForCulturalOffer(id, paging);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ReviewDTO>> searchReview(@RequestParam("query") String query, @RequestParam(defaultValue = "0") Integer pageNo,
                                                        @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
                                                        @RequestParam(defaultValue = "true") String descending) {
        Pageable paging;
        if (descending.equals("true"))
            paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
        else
            paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        Page<ReviewDTO> reviews = service.search(query, paging);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }


    @PostMapping
    @Transactional
//    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Object> addReview(@Valid @RequestParam String review, @RequestParam(value = "files", required = false) MultipartFile[] files) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ReviewDTO dto = mapper.readValue(review, ReviewDTO.class);
        return new ResponseEntity<>(service.create(dto, files), HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
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
