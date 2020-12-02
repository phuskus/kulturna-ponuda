package ftn.kts.controller;

import ftn.kts.dto.RegisteredUserDTO;
import ftn.kts.service.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/registered_users")
public class RegisteredUserController {

    private RegisteredUserService service;

    @Autowired
    public RegisteredUserController(RegisteredUserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RegisteredUserDTO>> getAllUsers() {
        List<RegisteredUserDTO> users = service.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegisteredUserDTO> getUser(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(service.getOne(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@Valid @RequestBody RegisteredUserDTO dto) {
        try {
            service.create(dto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody RegisteredUserDTO dto, @PathVariable long id) {
        try {
            RegisteredUserDTO updated = service.update(dto, id);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RegisteredUserDTO> deleteOffer(@PathVariable("id") long id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
