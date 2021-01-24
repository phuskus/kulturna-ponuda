package ftn.kts.controller;

import ftn.kts.dto.SubscriptionDTO;
import ftn.kts.model.RegisteredUser;
import ftn.kts.repository.RegisteredUserRepository;
import ftn.kts.service.RegisteredUserService;
import ftn.kts.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.List;

@RestController
@Validated
@RequestMapping("/subscriptions")
public class SubscriptionController {

	private SubscriptionService service;
	private RegisteredUserRepository registeredUserRepository;

	@Autowired
	public SubscriptionController(SubscriptionService subscriptionService) {
		this.service = subscriptionService;
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<Page<SubscriptionDTO>> getAllSubscriptions(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "true") String descending) {
		Pageable paging;
		if (descending.equals("true"))
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
		Page<SubscriptionDTO> subscriptions = service.getAllDTO(paging);
		return new ResponseEntity<>(subscriptions, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<SubscriptionDTO> getSubscription(@PathVariable("id") long id) {
		return new ResponseEntity<>(service.getOneDTO(id), HttpStatus.OK);
	}

	@GetMapping("/offer/{id}")
	@PreAuthorize("hasAnyRole('USER')")
	public ResponseEntity<Boolean> getSubscribedToOffer(@PathVariable("id") long offerId) {
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String username = currentUser.getName();
		return new ResponseEntity<>(service.isSubscribedToOffer(username, offerId), HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> addSubscription(@Valid @RequestBody SubscriptionDTO dto) {
		service.create(dto);
		return new ResponseEntity<>("Successfully added subscription!", HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<? extends Object> updateSubscription(@Valid @RequestBody SubscriptionDTO dto,
			@PathVariable long id) {
		try {
			SubscriptionDTO updated = service.update(dto, id);
			return new ResponseEntity<>(updated, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> deleteSubscription(@PathVariable("id") long id) {
		service.delete(id);
		return new ResponseEntity<>("Successfully deleted subscription!", HttpStatus.OK);
	}
}
