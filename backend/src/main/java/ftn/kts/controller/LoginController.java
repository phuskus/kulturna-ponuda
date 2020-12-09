package ftn.kts.controller;

import ftn.kts.dto.UserDTO;
import ftn.kts.dto.UserTokenStateDTO;
import ftn.kts.exceptions.PasswordNotChangedException;
import ftn.kts.exceptions.UniqueConstraintViolationException;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
    public ResponseEntity<Object> register(@Valid  @RequestBody UserDTO user) throws UniqueConstraintViolationException {
        userService.create(user);
        return new ResponseEntity<>("User successfully registered!", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                                                       HttpServletResponse response) throws PasswordNotChangedException {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        UserTokenStateDTO token = userService.getLoggedIn(username, password);
        return new ResponseEntity<>(token, HttpStatus.OK);

    }

    @PostMapping("/change-password")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Object> changePassword(@Valid @RequestBody PasswordChanger passwordChanger) {
        String user = userService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    static class PasswordChanger {
        @NotBlank(message = "Old password is a required field!")
        public String oldPassword;
        @NotBlank(message = "New password is a required field!")
        @Size(min = 5, message = "New password must be at least 5 characters!")
        public String newPassword;
    }

}
