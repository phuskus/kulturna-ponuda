package ftn.kts.controller;

import ftn.kts.dto.SubscriptionDTO;
import ftn.kts.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private SubscriptionService service;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.service = subscriptionService;
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionDTO>> getAllSubscriptions() {
        List<SubscriptionDTO> subscriptions = service.getAllDTO();
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> getSubscription(@PathVariable("id") long id) {
        return new ResponseEntity<>(service.getOneDTO(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addSubscription(@Valid @RequestBody SubscriptionDTO dto) {
        service.create(dto);
        return new ResponseEntity<>("Successfully added subscription!", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> updateSubscription(@Valid @RequestBody SubscriptionDTO dto, @PathVariable long id) {
        SubscriptionDTO updated = service.update(dto, id);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubscription(@PathVariable("id") long id) {
        service.delete(id);
        return new ResponseEntity<>("Successfully deleted subscription!", HttpStatus.OK);
    }
}
