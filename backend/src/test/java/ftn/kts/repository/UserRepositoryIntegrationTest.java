package ftn.kts.repository;

import static ftn.kts.constants.UserConstants.DB_NO_REGISTRATION_KEY;
import static ftn.kts.constants.UserConstants.DB_REGISTRATION_KEY;
import static ftn.kts.constants.UserConstants.DB_USER_NO_SUCH_USERNAME;
import static ftn.kts.constants.UserConstants.DB_USER_USERNAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserRepositoryIntegrationTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void testFindUserByUsername() {
		User found = userRepository.findByUsername(DB_USER_USERNAME);
		assertEquals(DB_USER_USERNAME, found.getUsername());
	}
	
	@Test
	public void testFindNullByUsername() {
		User notFound = userRepository.findByUsername(DB_USER_NO_SUCH_USERNAME);
		assertNull(notFound);
	}
	
	@Test
	public void testFindUserByKey() {
		User notActivated = userRepository.findByKey(DB_REGISTRATION_KEY);
		assertEquals(notActivated.getKey(), DB_REGISTRATION_KEY);
	}
	
	@Test
	public void testFindNullByKey() {
		User alreadyActivated = userRepository.findByKey(DB_NO_REGISTRATION_KEY);;
		assertNull(alreadyActivated);
	}
	
}
