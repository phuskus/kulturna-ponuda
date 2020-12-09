package ftn.kts.controller;

import ftn.kts.dto.UserDTO;
import ftn.kts.dto.UserTokenStateDTO;
import ftn.kts.model.User;
import ftn.kts.security.auth.JwtAuthenticationRequest;
import ftn.kts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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

    @PostMapping("/login")
    public ResponseEntity<Object> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                                                       HttpServletResponse response) {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        User user = userService.findByUsername(username);
        if (user == null) {
            return new ResponseEntity<>("User with username " + user.getUsername() + " doesn't exist!", HttpStatus.BAD_REQUEST);
        }
        if (!user.isEnabled()) {
            return new ResponseEntity<>("Your account hasn't been activated yet. Please check your email!", HttpStatus.BAD_REQUEST);
        }
        try {
            UserTokenStateDTO token = userService.getLoggedIn(username, password);
            // redirection to change-password on frontend
            if (user.getLastPasswordResetDate() == null)
                return new ResponseEntity<>(token, HttpStatus.TEMPORARY_REDIRECT);
            else
                return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Oh no! Something bad happened!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/change-password")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Object> changePassword(@RequestBody PasswordChanger passwordChanger) {
        if (passwordChanger.newPassword.length() < 5) {
            return new ResponseEntity<>("New password must be at least 5 characters!", HttpStatus.BAD_REQUEST);
        }
        String user = userService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
        if (user.equals("error")) {
            return new ResponseEntity<>("Server error has occurred. Please try again later.", HttpStatus.BAD_REQUEST);
        }
        if (user.equals("pass")) {
            return new ResponseEntity<>("Incorrect old password. Please try again.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    static class PasswordChanger {
        public String oldPassword;
        public String newPassword;
    }

}
