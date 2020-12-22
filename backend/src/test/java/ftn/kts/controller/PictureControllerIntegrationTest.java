package ftn.kts.controller;

import static ftn.kts.constants.AuthenticationConstants.USER_EMAIL;
import static ftn.kts.constants.AuthenticationConstants.USER_PASSWORD;
import static ftn.kts.constants.PictureConstants.DB_ELEMENT_NUM;
import static ftn.kts.constants.PictureConstants.DB_INSERT_FILE_CONTENT;
import static ftn.kts.constants.PictureConstants.DB_INSERT_FILE_NAME;
import static ftn.kts.constants.PictureConstants.GET_NULL_ID;
import static ftn.kts.constants.PictureConstants.GET_ONE_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import ftn.kts.dto.PictureDTO;
import ftn.kts.dto.UserTokenStateDTO;
import ftn.kts.exceptions.ErrorMessage;
import ftn.kts.model.Picture;
import ftn.kts.security.auth.JwtAuthenticationRequest;
import ftn.kts.service.PictureService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PictureControllerIntegrationTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private PictureService pictureService;

	private String accessToken;

	public HttpHeaders getAuthHeaders(String username, String password) {
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/login",
				new JwtAuthenticationRequest(username, password), UserTokenStateDTO.class);
		accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", accessToken);
		List<MediaType> acceptList = new ArrayList<>();
		acceptList.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptList);

		return headers;
	}

	@Test
	public void testGetAllPictures() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeaders(USER_EMAIL, USER_PASSWORD));

		ResponseEntity<PictureDTO[]> responseEntity = restTemplate.exchange("/pictures", HttpMethod.GET, httpEntity,
				PictureDTO[].class);
		PictureDTO[] pictures = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_ELEMENT_NUM, pictures.length);
	}

	@Test
	public void testGetPicture() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeaders(USER_EMAIL, USER_PASSWORD));

		ResponseEntity<PictureDTO> responseEntity = restTemplate.exchange("/pictures/" + GET_ONE_ID, HttpMethod.GET,
				httpEntity, PictureDTO.class);
		PictureDTO picture = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(GET_ONE_ID, picture.getId());
	}

	@Test
	public void testGetPictureNull() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeaders(USER_EMAIL, USER_PASSWORD));

		ResponseEntity<ErrorMessage> responseEntity = restTemplate.exchange("/pictures/" + GET_NULL_ID, HttpMethod.GET,
				httpEntity, ErrorMessage.class);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Picture with id " + GET_NULL_ID + " doesn't exist!", responseEntity.getBody().getMessage());
	}

}
