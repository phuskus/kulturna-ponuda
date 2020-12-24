package ftn.kts.service;

import static ftn.kts.constants.UserConstants.DB_ADMIN_PASSWORD_FAILED;
import static ftn.kts.constants.UserConstants.DB_ADMIN_USERNAME_FAILED;
import static ftn.kts.constants.UserConstants.DB_CREATED_USER_ID;
import static ftn.kts.constants.UserConstants.DB_NEW_USER_NAME;
import static ftn.kts.constants.UserConstants.DB_NEW_USER_PASSWORD;
import static ftn.kts.constants.UserConstants.DB_NO_REGISTRATION_KEY;
import static ftn.kts.constants.UserConstants.DB_REGISTRATION_KEY;
import static ftn.kts.constants.UserConstants.DB_USER_NO_SUCH_USERNAME;
import static ftn.kts.constants.UserConstants.DB_USER_PASSWORD;
import static ftn.kts.constants.UserConstants.DB_USER_PASSWORD_DISABLED;
import static ftn.kts.constants.UserConstants.DB_USER_USERNAME;
import static ftn.kts.constants.UserConstants.DB_USER_USERNAME_DISABLED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.dto.UserDTO;
import ftn.kts.dto.UserTokenStateDTO;
import ftn.kts.exceptions.PasswordNotChangedException;
import ftn.kts.exceptions.UniqueConstraintViolationException;
import ftn.kts.model.RegisteredUser;
import ftn.kts.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceIntegrationTest {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void findByUsername_UsernameExists_OneEntityReturned() {
		User found = userService.findByUsername(DB_USER_USERNAME);
		assertEquals(DB_USER_USERNAME, found.getUsername());
	}
	
	@Test
	public void findByUsername_UsernameNotExists_NullReturned() {
		User notFound = userService.findByUsername(DB_USER_NO_SUCH_USERNAME);
		assertNull(notFound);
	}
	
	@Test
	public void findByKey_AccountNotActivated_OneEntityReturned() {
		User notActivated = userService.findByKey(DB_REGISTRATION_KEY);
		assertEquals(notActivated.getKey(), DB_REGISTRATION_KEY);
	}
	
	@Test
	public void findByKey_AccountActivatedOrKeyNotExists_NullReturned() {
		User alreadyActivated = userService.findByKey(DB_NO_REGISTRATION_KEY);;
		assertNull(alreadyActivated);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void createAndDelete_UsernameNotExists_EntitySavedAndDeleted() throws UniqueConstraintViolationException {
		UserDTO newUser = new UserDTO(DB_NEW_USER_NAME, DB_USER_NO_SUCH_USERNAME, DB_NEW_USER_PASSWORD);
		User user = userService.create(newUser);
		assertEquals(user.getId(), DB_CREATED_USER_ID);
		
		//cleanup
		userService.delete(user.getUsername());
		userService.getOne(user.getUsername());
	}
	
	@Test(expected = UniqueConstraintViolationException.class)
	public void create_UsernameExists_UniqueConstraintViolationExceptionTrown() throws UniqueConstraintViolationException {
		UserDTO newUser = new UserDTO(DB_NEW_USER_NAME, DB_USER_USERNAME, DB_NEW_USER_PASSWORD);
		userService.create(newUser);
	}
	
	@Test
	public void getLoggedIn_AccountExists_JWTTokenReturned() throws DisabledException, PasswordNotChangedException {
		UserTokenStateDTO tokenDTO = userService.getLoggedIn(DB_USER_USERNAME, DB_USER_PASSWORD);
		assertNotNull(tokenDTO);
	}
	
	@Test(expected = DisabledException.class)
	public void getLoggedIn_AccountDisabled_DisabledExceptionThrown() throws DisabledException, PasswordNotChangedException {
		userService.getLoggedIn(DB_USER_USERNAME_DISABLED, DB_USER_PASSWORD_DISABLED);
	}
	
	@Test(expected = PasswordNotChangedException.class)
	public void getLoggedIn_AdminAccountPasswordNotChanged_PasswordNotChangedExceptionThrown() throws DisabledException, PasswordNotChangedException {
		userService.getLoggedIn(DB_ADMIN_USERNAME_FAILED, DB_ADMIN_PASSWORD_FAILED);
	}
	
	@Test
	public void confirmRegistration_AccountExistsNotEnabled_AccountEnabled() {
		User user = userService.confirmRegistration(DB_REGISTRATION_KEY);
		assertEquals(user.isEnabled(), true);
		assertEquals(user.getKey(), "");
		//clean up
		user.setKey(DB_REGISTRATION_KEY);
		user.setEnabled(false);
		userService.save(user);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void confirmRegistration_AccountEnabled_NoSuchElementExceptionThrown() {
		userService.confirmRegistration(DB_NO_REGISTRATION_KEY);
	}
	
	@Test
	public void save_UsernameNotExists_OneEntityReturned() {
		RegisteredUser newUser = new RegisteredUser(DB_NEW_USER_NAME, DB_USER_NO_SUCH_USERNAME, DB_NEW_USER_PASSWORD);
		User user = userService.save(newUser);
		assertNotNull(user);
		assertEquals(user.getName(), DB_NEW_USER_NAME);
		//clean up
		userService.delete(user.getUsername());
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	public void save_UsernameExists_DataIntegrityViolationExceptionThrown() {
		RegisteredUser newUser = new RegisteredUser(DB_USER_USERNAME, DB_USER_USERNAME, DB_NEW_USER_PASSWORD);
		userService.save(newUser);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void delete_UsernameNotExists_NoSuchElementExceptionThrown() {
		userService.delete(DB_USER_NO_SUCH_USERNAME);
	}
	
	@Test
	public void getOne_UsernameExists_OneEntityReturned() {
		User found = userService.getOne(DB_USER_USERNAME);
		assertNotNull(found);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void getOne_UsernameNotExists_NoSuchElementExceptionThrown() {
		userService.getOne(DB_USER_NO_SUCH_USERNAME);
	}
}
