package ftn.kts.controller;

import ftn.kts.dto.UserDTO;
import ftn.kts.model.User;
import ftn.kts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid  @RequestBody UserDTO user) {
        try {
            User existUser = this.userService.findByUsername(user.getUsername());
            if (existUser != null) {
                return new ResponseEntity<>("User with that username already exists!", HttpStatus.BAD_REQUEST);
            }
            userService.create(user);
            return new ResponseEntity<>("User successfully registered!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Please provide all the required fields!", HttpStatus.BAD_REQUEST);
        }

    }


}
