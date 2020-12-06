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
        List<SubscriptionDTO> subscriptions = service.getAll();
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> getSubscription(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(service.getOne(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addSubscription(@Valid @RequestBody SubscriptionDTO dto) {
        try {
            service.create(dto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSubscription(@Valid @RequestBody SubscriptionDTO dto, @PathVariable long id) {
        try {
            SubscriptionDTO updated = service.update(dto, id);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> deleteSubscription(@PathVariable("id") long id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
