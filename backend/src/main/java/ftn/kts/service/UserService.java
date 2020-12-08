package ftn.kts.service;

import ftn.kts.dto.UserDTO;
import ftn.kts.dto.UserTokenStateDTO;
import ftn.kts.model.Authority;
import ftn.kts.model.RegisteredUser;
import ftn.kts.model.User;
import ftn.kts.repository.UserRepository;
import ftn.kts.security.CustomUserDetailsService;
import ftn.kts.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    private UserRepository userRepository;
    private TokenUtils tokenUtils;
    private AuthenticationManager authenticationManager;
    private CustomUserDetailsService userDetailsService;
    private AuthorityService authorityService;

    @Autowired
    public UserService(UserRepository userRepository, TokenUtils tokenUtils, AuthenticationManager authenticationManager,
                       CustomUserDetailsService userDetailsService, AuthorityService authorityService) {
        this.userRepository = userRepository;
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.authorityService = authorityService;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void create(UserDTO dto) {
        RegisteredUser user = toEntity(dto);
        user.setPassword(userDetailsService.encodePassword(dto.getPassword()));
        ArrayList<Authority> auth = new ArrayList<>();
        auth.add(authorityService.findByName("REGISTERED_USER"));
        user.setAuthorities(auth);
        userRepository.save(user);
    }

    public UserTokenStateDTO getLoggedIn(String username, String password) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // create token
        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();
        return new UserTokenStateDTO(jwt, expiresIn, user.getRole());
    }

    private UserDTO toDTO(User entity) {
        UserDTO dto = new UserDTO(entity.getId(), entity.getName(), entity.getUsername(), entity.getPassword());
        return dto;
    }

    private RegisteredUser toEntity(UserDTO dto) {
        RegisteredUser user = new RegisteredUser(dto.getName(), dto.getUsername(), dto.getPassword());
        return user;
    }
}
