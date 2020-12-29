package ftn.kts.service;

import static ftn.kts.constants.MailSenderConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MailSenderServiceIntegrationTest {

	@Autowired
	MailSenderService mailSenderService;
	
	@Test
	public void confirmRegistration_Success() throws InterruptedException, ExecutionException {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(USERNAME);
		Future<SimpleMailMessage> messagePromise = mailSenderService.confirmRegistration(USERNAME, KEY);
		
		String[] mails = messagePromise.get().getTo();
		String to = mails[0];
		String subject = messagePromise.get().getSubject();
		String content = messagePromise.get().getText();
		
		assertNotNull(mails);
		assertNotNull(to);
		assertEquals(to, USERNAME);
		assertEquals(subject, SUBJECT);
		assertTrue(content.contains(KEY));
	}
}
