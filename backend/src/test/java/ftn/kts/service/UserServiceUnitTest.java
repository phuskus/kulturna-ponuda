package ftn.kts.service;

import static ftn.kts.constants.UserConstants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.dto.UserDTO;
import ftn.kts.exceptions.UniqueConstraintViolationException;
import ftn.kts.model.RegisteredUser;
import ftn.kts.model.User;
import ftn.kts.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceUnitTest {
	
	@Autowired
	private UserService userService;
	
	@MockBean
	private UserRepository userRepository;
	
	@Test
	public void create_UsernameNotExists_UserReturned() {
		UserDTO dto = new UserDTO(DB_NEW_USER_NAME, DB_USER_SURNAME, DB_USER_NO_SUCH_USERNAME, DB_NEW_USER_PASSWORD);
		
		given(userRepository.findByUsername(DB_USER_NO_SUCH_USERNAME)).willReturn(null);
		given(userRepository.save(any())).willReturn(new RegisteredUser(DB_NEW_USER_NAME, DB_USER_SURNAME, DB_USER_NO_SUCH_USERNAME, DB_NEW_USER_PASSWORD));
		
		try {
			UserDTO created = userService.create(dto);
			verify(userRepository, times(1)).findByUsername(DB_USER_NO_SUCH_USERNAME);
			verify(userRepository, times(1)).save(any());
			assertEquals(created.getUsername(), dto.getUsername());
		} catch (UniqueConstraintViolationException e) {
			fail();
		}
	}
	
	@Test
	public void create_UsernameExists_UniqueConstraintViolationExceptionThrown() {
		UserDTO dto = new UserDTO(DB_NEW_USER_NAME, DB_USER_SURNAME, DB_USER_USERNAME, DB_NEW_USER_PASSWORD);
		User existingUser = new RegisteredUser(DB_USER_NAME, DB_USER_SURNAME, DB_USER_USERNAME, DB_USER_PASSWORD);
		
		given(userRepository.findByUsername(DB_USER_USERNAME)).willReturn(existingUser);
		
		assertThrows(UniqueConstraintViolationException.class, () -> userService.create(dto));
		verify(userRepository, times(1)).findByUsername(DB_USER_USERNAME);
	}

}
