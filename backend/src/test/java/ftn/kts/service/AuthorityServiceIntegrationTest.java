package ftn.kts.service;

import static ftn.kts.constants.AuthorityConstants.DB_AUTHORITY_USER_NAME;
import static ftn.kts.constants.AuthorityConstants.DB_AUTHORITY_ADMIN_NAME;
import static ftn.kts.constants.AuthorityConstants.DB_AUTHORITY_NULL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.model.Authority;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorityServiceIntegrationTest {
	@Autowired
	private AuthorityService authService;
		
	@Test
	public void findByName_UserRoleExists_UserRoleReturned() {
		Authority found = authService.findByName(DB_AUTHORITY_USER_NAME);
		assertEquals(DB_AUTHORITY_USER_NAME, found.getName());
	}
	
	@Test
	public void findByName_AdminRoleExists_AdminRoleReturned() {
		Authority found = authService.findByName(DB_AUTHORITY_ADMIN_NAME);
		assertEquals(DB_AUTHORITY_ADMIN_NAME, found.getName());
	}
	
	@Test
	public void findByName_RoleNull_NullReturned() {
		Authority found = authService.findByName(DB_AUTHORITY_NULL);
		assertNull(found);
	}
	
}
