package ftn.kts.service;

import ftn.kts.dto.UserDTO;
import ftn.kts.dto.UserTokenStateDTO;
import ftn.kts.exceptions.UserException;
import ftn.kts.exceptions.PasswordNotChangedException;
import ftn.kts.exceptions.UniqueConstraintViolationException;
import ftn.kts.model.Authority;
import ftn.kts.model.RegisteredUser;
import ftn.kts.model.User;
import ftn.kts.repository.UserRepository;
import ftn.kts.security.CustomUserDetailsService;
import ftn.kts.security.TokenUtils;
import ftn.kts.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private UserRepository userRepository;
    private TokenUtils tokenUtils;
    private AuthenticationManager authenticationManager;
    private CustomUserDetailsService userDetailsService;
    private AuthorityService authorityService;
    private MailSenderService mailSenderService;

    @Autowired
    public UserService(UserRepository userRepository, TokenUtils tokenUtils, AuthenticationManager authenticationManager,
                       CustomUserDetailsService userDetailsService, AuthorityService authorityService,
                       MailSenderService mailSenderService) {
        this.userRepository = userRepository;
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.authorityService = authorityService;
        this.mailSenderService = mailSenderService;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByKey(String key) {
        return userRepository.findByKey(key);
    }

    public UserDTO create(UserDTO dto) throws UniqueConstraintViolationException {
        checkUnique(dto);
        RegisteredUser user = toEntity(dto);
        user.setPassword(userDetailsService.encodePassword(dto.getPassword()));
        ArrayList<Authority> auth = new ArrayList<>();
        auth.add(authorityService.findByName("REGISTERED_USER"));
        user.setAuthorities(auth);
        String generatedKey = RandomUtil.buildAuthString(30);
        user.setKey(generatedKey);
        mailSenderService.confirmRegistration(user.getUsername(), generatedKey);
        save(user);
        return toDTO(user);

    }
    
    public User save(User user) {
    	return userRepository.save(user);    		
    }
    
    public void delete(String username) throws NoSuchElementException {
		User user = getOne(username);
		userRepository.delete(user);    		
    	
	}

    public UserTokenStateDTO getLoggedIn(String username, String password) throws DisabledException, PasswordNotChangedException, UserException {
        User existUser = null;
        try {
        	existUser = getOne(username);        	        	
        } catch (NoSuchElementException e) {
        	throw new UserException("No such element!", "username", "User with this username doesn't exist.");
        }
       
        if (!existUser.isEnabled()) {
            throw new DisabledException("Your account hasn't been activated yet. Please check your email!");
        }
        
        Authentication authentication = null;
        try {
            authentication =
            	authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
        	throw new UserException("Bad credentials exception!", "password", "Incorrect password.");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // create token
        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

		/*
		 * if (existUser.getLastPasswordResetDate() == null) { throw new
		 * PasswordNotChangedException("Please change your password!", jwt); }
		 */

        return new UserTokenStateDTO(jwt, expiresIn, user.getRole());
    }

    public UserDTO changePassword(String oldPassword, String newPassword) {
        String user = userDetailsService.changePassword(oldPassword, newPassword);
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user);
        return userDTO;
    }

    public UserDTO confirmRegistration(String key) throws NoSuchElementException {
        User user = userRepository.findByKey(key);
        if (user == null) {
            throw new NoSuchElementException("User already activated or doesn't exist!");
        }
        user.setEnabled(true);
        user.setKey(null);
        save(user);
        return toDTO(user);
    }

    public User getOne(String username) throws NoSuchElementException {
        User user = findByUsername(username);
        if (user == null) {
            throw new NoSuchElementException("User with this username doesn't exist!");
        }
        return user;
    }

    private RegisteredUser toEntity(UserDTO dto) {
        RegisteredUser user = new RegisteredUser(dto.getName(), dto.getSurname(), dto.getUsername(), dto.getPassword());
        return user;
    }
    
    private UserDTO toDTO(User user) {
    	UserDTO dto = new UserDTO();
    	dto.setId(user.getId());
    	dto.setUsername(user.getUsername());
    	dto.setName(user.getName());
    	dto.setSurname(user.getSurname());
    	return dto;
    }

    private void checkUnique(UserDTO dto) throws UniqueConstraintViolationException {
        User user = userRepository.findByUsername(dto.getUsername());
        if (user != null) {
            // register
            if (dto.getId() == null) {
                throw new UniqueConstraintViolationException("Unique key constraint violated!", "username",
                        "User with this username already exists!");
            } else {
                // update profile
                if (!user.getId().equals(dto.getId()))
                    throw new UniqueConstraintViolationException("Unique key constraint violated!", "username",
                            "User with this username already exists!");
            }
        }
    }
}
