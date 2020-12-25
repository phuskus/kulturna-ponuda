package ftn.kts.controller;

import static ftn.kts.constants.UserConstants.DB_ADMIN_PASSWORD_FAILED;
import static ftn.kts.constants.UserConstants.DB_ADMIN_USERNAME_FAILED;
import static ftn.kts.constants.UserConstants.DB_CREATE_USERNAME;
import static ftn.kts.constants.UserConstants.DB_NEW_USER_NAME;
import static ftn.kts.constants.UserConstants.DB_NEW_USER_PASSWORD;
import static ftn.kts.constants.UserConstants.DB_REGISTRATION_KEY;
import static ftn.kts.constants.UserConstants.DB_USER_PASSWORD;
import static ftn.kts.constants.UserConstants.DB_USER_PASSWORD_DISABLED;
import static ftn.kts.constants.UserConstants.DB_USER_USERNAME;
import static ftn.kts.constants.UserConstants.DB_USER_USERNAME_DISABLED;
import static ftn.kts.constants.UserConstants.DB_USER_WRONG_USERNAME;
import static ftn.kts.constants.UserConstants.INVALID_PASSWORD;
import static ftn.kts.util.ControllerUtil.getAuthHeadersUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.controller.LoginController.PasswordChanger;
import ftn.kts.dto.UserDTO;
import ftn.kts.dto.UserTokenStateDTO;
import ftn.kts.model.User;
import ftn.kts.security.auth.JwtAuthenticationRequest;
import ftn.kts.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private UserService userService;
		
	@Test
	public void register_UsernameNotExists_Success() {
		UserDTO dto = new UserDTO(DB_NEW_USER_NAME, DB_CREATE_USERNAME, DB_NEW_USER_PASSWORD);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto);
		ResponseEntity<UserDTO> responseEntity = restTemplate.exchange("/auth/register", HttpMethod.POST, 
				httpEntity, UserDTO.class);
		
		UserDTO registered = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(registered.getId());	
	}
	
	@Test
	public void register_UsernameExists_BadRequestReturned() {
		UserDTO dto = new UserDTO(DB_NEW_USER_NAME, DB_USER_USERNAME, DB_NEW_USER_PASSWORD);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto);
		ResponseEntity<UserDTO> responseEntity = restTemplate.exchange("/auth/register", HttpMethod.POST, 
				httpEntity, UserDTO.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void register_InvalidUsername_BadRequestReturned() {
		UserDTO dto = new UserDTO(DB_NEW_USER_NAME, DB_USER_WRONG_USERNAME, DB_NEW_USER_PASSWORD);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto);
		ResponseEntity<UserDTO> responseEntity = restTemplate.exchange("/auth/register", HttpMethod.POST, 
				httpEntity, UserDTO.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void register_ShortPassword_BadRequestReturned() {
		UserDTO dto = new UserDTO(DB_NEW_USER_NAME, DB_CREATE_USERNAME, INVALID_PASSWORD);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto);
		ResponseEntity<UserDTO> responseEntity = restTemplate.exchange("/auth/register", HttpMethod.POST, 
				httpEntity, UserDTO.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void register_NoName_BadRequestReturned() {
		UserDTO dto = new UserDTO("", DB_CREATE_USERNAME, DB_NEW_USER_PASSWORD);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto);
		ResponseEntity<UserDTO> responseEntity = restTemplate.exchange("/auth/register", HttpMethod.POST, 
				httpEntity, UserDTO.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void register_NoUsername_BadRequestReturned() {
		UserDTO dto = new UserDTO(DB_NEW_USER_NAME, "", DB_NEW_USER_PASSWORD);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto);
		ResponseEntity<UserDTO> responseEntity = restTemplate.exchange("/auth/register", HttpMethod.POST, 
				httpEntity, UserDTO.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void register_NoPassword_BadRequestReturned() {
		UserDTO dto = new UserDTO(DB_NEW_USER_NAME, DB_CREATE_USERNAME, "");
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto);
		ResponseEntity<UserDTO> responseEntity = restTemplate.exchange("/auth/register", HttpMethod.POST, 
				httpEntity, UserDTO.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void confirmRegistration_AccountNotEnabled_OneEntityReturned() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		ResponseEntity<UserDTO> responseEntity = restTemplate.exchange("/auth/register/test-key", 
					HttpMethod.GET, httpEntity, UserDTO.class);			
		
		UserDTO dto = responseEntity.getBody();
		User enabledAccount = userService.getOne(dto.getUsername());
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNull(enabledAccount.getKey());
		assertTrue(enabledAccount.isEnabled());
		
		//clean up
		enabledAccount.setKey(DB_REGISTRATION_KEY);
		enabledAccount.setEnabled(false);
		userService.save(enabledAccount);
	}
	
	@Test
	public void confirmRegistration_AccountAlreadyEnabledOrKeyNotExists_NotFoundReturned() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		ResponseEntity<UserDTO> responseEntity = restTemplate.exchange("/auth/register/no-key", 
					HttpMethod.GET, httpEntity, UserDTO.class);
				
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void createAuthenticationToken_ValidCredentials_JwtTokenReturned() {
		JwtAuthenticationRequest dto = new JwtAuthenticationRequest(DB_USER_USERNAME, DB_USER_PASSWORD);
		HttpEntity<JwtAuthenticationRequest> httpEntity = new HttpEntity<JwtAuthenticationRequest>(dto);
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.exchange("/auth/login", 
					HttpMethod.POST, httpEntity, UserTokenStateDTO.class);
		UserTokenStateDTO token = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(token);
	}
	
	@Test
	public void createAuthenticationToken_UsernameNotExists_NotFoundReturned() {
		JwtAuthenticationRequest dto = new JwtAuthenticationRequest("test11@gmail.com", "test1");
		HttpEntity<JwtAuthenticationRequest> httpEntity = new HttpEntity<JwtAuthenticationRequest>(dto);
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.exchange("/auth/login", 
					HttpMethod.POST, httpEntity, UserTokenStateDTO.class);
		UserTokenStateDTO token = responseEntity.getBody();
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNull(token.getAccessToken());
	}
	
	@Test
	public void createAuthenticationToken_WrongPassword_BadRequestReturned() {
		JwtAuthenticationRequest dto = new JwtAuthenticationRequest(DB_USER_USERNAME, "test111");
		HttpEntity<JwtAuthenticationRequest> httpEntity = new HttpEntity<JwtAuthenticationRequest>(dto);
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.exchange("/auth/login", 
					HttpMethod.POST, httpEntity, UserTokenStateDTO.class);
		UserTokenStateDTO token = responseEntity.getBody();
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNull(token.getAccessToken());
	}
	
	@Test
	public void createAuthenticationToken_AccountNotActivated_UnauthorizedReturned() {
		JwtAuthenticationRequest dto = new JwtAuthenticationRequest(DB_USER_USERNAME_DISABLED, DB_USER_PASSWORD_DISABLED);
		HttpEntity<JwtAuthenticationRequest> httpEntity = new HttpEntity<JwtAuthenticationRequest>(dto);
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.exchange("/auth/login", 
					HttpMethod.POST, httpEntity, UserTokenStateDTO.class);
		UserTokenStateDTO token = responseEntity.getBody();
		
		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
		assertNull(token.getAccessToken());
	}
	
	@Test
	public void createAuthenticationToken_AdminNotChangedPassword_JwtTokenReturned() {
		JwtAuthenticationRequest dto = new JwtAuthenticationRequest(DB_ADMIN_USERNAME_FAILED, DB_ADMIN_PASSWORD_FAILED);
		HttpEntity<JwtAuthenticationRequest> httpEntity = new HttpEntity<JwtAuthenticationRequest>(dto);
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.exchange("/auth/login", 
					HttpMethod.POST, httpEntity, UserTokenStateDTO.class);
		UserTokenStateDTO token = responseEntity.getBody();
		
		assertEquals(HttpStatus.TEMPORARY_REDIRECT, responseEntity.getStatusCode());
		assertNotNull(token);
	}
	
	@Test
	public void createAuthenticationToken_AdminChangedPassword_JwtTokenReturned() {
		JwtAuthenticationRequest dto = new JwtAuthenticationRequest("admin1@gmail.com", "admin1");
		HttpEntity<JwtAuthenticationRequest> httpEntity = new HttpEntity<JwtAuthenticationRequest>(dto);
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.exchange("/auth/login", 
					HttpMethod.POST, httpEntity, UserTokenStateDTO.class);
		UserTokenStateDTO token = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(token);
	}
	
	@Test
	public void changePassword_ValidCredentials_Success() {
		PasswordChanger passwords = new PasswordChanger();
		passwords.oldPassword = "test1";
		passwords.newPassword = "test2";
		HttpEntity<PasswordChanger> httpEntity = new HttpEntity<PasswordChanger>(passwords, getAuthHeadersUser(restTemplate));
		ResponseEntity<UserDTO> responseEntity = restTemplate.exchange("/auth/change-password", HttpMethod.POST, httpEntity, 
				UserDTO.class);
		UserDTO dto = responseEntity.getBody();
	
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(dto.getUsername(), DB_USER_USERNAME);
		
		//clean up
		User user = userService.getOne(DB_USER_USERNAME);
		user.setPassword("test1");
		userService.save(user);
	}
	
	@Test
	public void changePassword_WrongPassword_BadRequestReturned() {
		PasswordChanger passwords = new PasswordChanger();
		passwords.oldPassword = "test111";
		passwords.newPassword = "test2";
		HttpEntity<PasswordChanger> httpEntity = new HttpEntity<PasswordChanger>(passwords, getAuthHeadersUser(restTemplate));
		ResponseEntity<UserDTO> responseEntity = restTemplate.exchange("/auth/change-password", HttpMethod.POST, httpEntity, 
				UserDTO.class);
		UserDTO dto = responseEntity.getBody();
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNull(dto.getUsername());
	}
	
	@Test
	public void changePassword_InvalidNewPassword_BadRequestReturned() {
		PasswordChanger passwords = new PasswordChanger();
		passwords.oldPassword = "test1";
		passwords.newPassword = "te1";
		HttpEntity<PasswordChanger> httpEntity = new HttpEntity<PasswordChanger>(passwords, getAuthHeadersUser(restTemplate));
		ResponseEntity<UserDTO> responseEntity = restTemplate.exchange("/auth/change-password", HttpMethod.POST, httpEntity, 
				UserDTO.class);
		UserDTO dto = responseEntity.getBody();
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNull(dto.getUsername());
	}
	
	@Test
	public void changePassword_NoNewPassword_BadRequestReturned() {
		PasswordChanger passwords = new PasswordChanger();
		passwords.oldPassword = "test1";
		passwords.newPassword = "";
		HttpEntity<PasswordChanger> httpEntity = new HttpEntity<PasswordChanger>(passwords, getAuthHeadersUser(restTemplate));
		ResponseEntity<UserDTO> responseEntity = restTemplate.exchange("/auth/change-password", HttpMethod.POST, httpEntity, 
				UserDTO.class);
		UserDTO dto = responseEntity.getBody();
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNull(dto.getUsername());
	}
	
	

}
