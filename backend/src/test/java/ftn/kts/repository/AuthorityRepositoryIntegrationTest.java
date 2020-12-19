package ftn.kts.repository;

import ftn.kts.model.Authority;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import static ftn.kts.constants.AuthorityConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthorityRepositoryIntegrationTest {

	@Autowired
	private AuthorityRepository authorityRepository;

	@Test
	public void testFindUserByName() {
		Authority found = authorityRepository.findByName(DB_AUTHORITY_USER_NAME);
		assertEquals(DB_AUTHORITY_USER_NAME, found.getName());
	}

	@Test
	public void testFindAdminByName() {
		Authority found = authorityRepository.findByName(DB_AUTHORITY_ADMIN_NAME);
		assertEquals(DB_AUTHORITY_ADMIN_NAME, found.getName());
	}

	@Test
	public void testFindNullByName() {
		Authority found = authorityRepository.findByName(DB_AUTHORITY_NULL);
		assertNull(found);
	}
}
