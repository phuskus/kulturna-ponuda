package ftn.kts.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.kts.dto.AccountDTO;
import ftn.kts.dto.ResetPasswordDTO;
import ftn.kts.dto.UserDTO;
import ftn.kts.dto.UserTokenStateDTO;
import ftn.kts.exceptions.PasswordNotChangedException;
import ftn.kts.exceptions.UniqueConstraintViolationException;
import ftn.kts.exceptions.UserException;
import ftn.kts.security.auth.JwtAuthenticationRequest;
import ftn.kts.service.UserService;

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
        UserDTO created = userService.create(user);
        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @GetMapping("/register/{key}")
    public ResponseEntity<UserDTO> confirmRegistration(@PathVariable("key") String key) {
        userService.confirmRegistration(key);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    

    @PostMapping("/login")
    public ResponseEntity<UserTokenStateDTO> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                                                       HttpServletResponse response) throws PasswordNotChangedException, DisabledException, UserException {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        UserTokenStateDTO token = userService.getLoggedIn(username, password);
        return new ResponseEntity<>(token, HttpStatus.OK);

    }

    @PostMapping("/change-password")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserDTO> changePassword(@Valid @RequestBody PasswordChanger passwordChanger) {
        UserDTO user = userService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @PostMapping("/forgot-password")
    public ResponseEntity<UserDTO> forgotPassword(@Valid @RequestBody String username) {
        userService.forgotPassword(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<UserTokenStateDTO> resetPassword(@Valid @RequestBody ResetPasswordDTO dto) throws UserException {
    	UserTokenStateDTO user = userService.resetPassword(dto);
    	return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @GetMapping("/account/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<AccountDTO> getProfile(@PathVariable("id") Long id) {
    	AccountDTO dto = userService.getAccount(id);
    	return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    
    @PutMapping("/account/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Object> updateProfile(@PathVariable("id") Long id, @Valid @RequestBody AccountDTO account) {
    	userService.update(id, account);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    

    static class PasswordChanger {
        @NotBlank(message = "Old password is a required field!")
        public String oldPassword;
        @NotBlank(message = "New password is a required field!")
        @Size(min = 5, message = "New password must be at least 5 characters!")
        public String newPassword;
    }

}
