package ftn.kts.controller;

import static ftn.kts.constants.AuthenticationConstants.USER_EMAIL;
import static ftn.kts.constants.AuthenticationConstants.USER_PASSWORD;
import static ftn.kts.constants.AuthenticationConstants.ADMIN_EMAIL;
import static ftn.kts.constants.AuthenticationConstants.ADMIN_PASSWORD;
import static ftn.kts.constants.PictureConstants.DB_ELEMENT_NUM;
import static ftn.kts.constants.PictureConstants.DB_INSERT_FILE_CONTENT;
import static ftn.kts.constants.PictureConstants.DB_INSERT_FILE_NAME;
import static ftn.kts.constants.PictureConstants.DB_INSERT_FILE_PARAM_NAME;
import static ftn.kts.constants.PictureConstants.GET_NULL_ID;
import static ftn.kts.constants.PictureConstants.GET_ONE_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

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

	@Autowired
	private WebApplicationContext webApplicationContext;

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
	public void getPictures_NoParams_ReturnsList() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeaders(USER_EMAIL, USER_PASSWORD));

		ResponseEntity<PictureDTO[]> responseEntity = restTemplate.exchange("/pictures", HttpMethod.GET, httpEntity,
				PictureDTO[].class);
		PictureDTO[] pictures = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_ELEMENT_NUM, pictures.length);
	}

	@Test
	public void getPicture_ExistsID_ReturnsObject() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeaders(USER_EMAIL, USER_PASSWORD));

		ResponseEntity<PictureDTO> responseEntity = restTemplate.exchange("/pictures/" + GET_ONE_ID, HttpMethod.GET,
				httpEntity, PictureDTO.class);
		PictureDTO picture = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(GET_ONE_ID, picture.getId());
	}

	@Test
	public void getPicture_NotExistsID_ReturnsNotFound() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeaders(USER_EMAIL, USER_PASSWORD));

		ResponseEntity<ErrorMessage> responseEntity = restTemplate.exchange("/pictures/" + GET_NULL_ID, HttpMethod.GET,
				httpEntity, ErrorMessage.class);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Picture with id " + GET_NULL_ID + " doesn't exist!", responseEntity.getBody().getMessage());
	}

	@Test
	public void addPicture_MultipartDataAsUser_ReturnsObject() throws Exception {
		int size = pictureService.getAll().size();

		MockMultipartFile file = new MockMultipartFile(DB_INSERT_FILE_PARAM_NAME, DB_INSERT_FILE_NAME, "image/jpeg",
				DB_INSERT_FILE_CONTENT.getBytes());

		HttpHeaders headers = getAuthHeaders(USER_EMAIL, USER_PASSWORD);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/pictures").file(file).headers(headers))
				.andReturn();

		ObjectMapper mapper = new ObjectMapper();
		PictureDTO picture = mapper.readValue(result.getResponse().getContentAsByteArray(), PictureDTO.class);
		String originalContent = new String(Base64.getDecoder().decode(picture.getImage().split("base64,")[1]));

		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
		assertNotNull(picture);
		assertEquals(DB_INSERT_FILE_CONTENT, originalContent);

		List<Picture> pictures = pictureService.getAll();
		assertEquals(size + 1, pictures.size());
		assertEquals(picture.getId(), pictures.get(pictures.size() - 1).getId());

		pictureService.delete(picture.getId());
	}
	
	@Test
	public void addPicture_MultipartDataAsAdmin_ReturnsObject() throws Exception {
		int size = pictureService.getAll().size();

		MockMultipartFile file = new MockMultipartFile(DB_INSERT_FILE_PARAM_NAME, DB_INSERT_FILE_NAME, "image/jpeg",
				DB_INSERT_FILE_CONTENT.getBytes());

		HttpHeaders headers = getAuthHeaders(ADMIN_EMAIL, ADMIN_PASSWORD);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/pictures").file(file).headers(headers))
				.andReturn();

		ObjectMapper mapper = new ObjectMapper();
		PictureDTO picture = mapper.readValue(result.getResponse().getContentAsByteArray(), PictureDTO.class);
		String originalContent = new String(Base64.getDecoder().decode(picture.getImage().split("base64,")[1]));

		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
		assertNotNull(picture);
		assertEquals(DB_INSERT_FILE_CONTENT, originalContent);

		List<Picture> pictures = pictureService.getAll();
		assertEquals(size + 1, pictures.size());
		assertEquals(picture.getId(), pictures.get(pictures.size() - 1).getId());

		pictureService.delete(picture.getId());
	}

	@Test
	public void addPicture_NoData_ReturnsBadRequest() throws Exception {
		HttpHeaders headers = getAuthHeaders(USER_EMAIL, USER_PASSWORD);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/pictures").headers(headers)).andReturn();

		ObjectMapper mapper = new ObjectMapper();
		ErrorMessage message = mapper.readValue(result.getResponse().getContentAsByteArray(), ErrorMessage.class);
		assertEquals("Required request part 'file' is not present", message.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
	}

	@Test
	public void addPicture_PlainTextData_ReturnsBadRequest() throws Exception {
		HttpHeaders headers = getAuthHeaders(USER_EMAIL, USER_PASSWORD);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/pictures").content(DB_INSERT_FILE_CONTENT).headers(headers))
				.andReturn();

		ObjectMapper mapper = new ObjectMapper();
		ErrorMessage message = mapper.readValue(result.getResponse().getContentAsByteArray(), ErrorMessage.class);
		assertEquals("Current request is not a multipart request", message.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
	}

	@Test
	public void deletePicture_ExistsIDAsUser_ReturnsOK() throws Exception {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeaders(USER_EMAIL, USER_PASSWORD));
		MockMultipartFile file = new MockMultipartFile(DB_INSERT_FILE_PARAM_NAME, DB_INSERT_FILE_NAME, "image/jpeg",
				DB_INSERT_FILE_CONTENT.getBytes());
		PictureDTO picture = pictureService.add(file);
		int size = pictureService.getAll().size();

		ResponseEntity<Void> responseEntity = restTemplate.exchange("/pictures/" + picture.getId(), HttpMethod.DELETE,
				httpEntity, Void.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		assertEquals(size - 1, pictureService.getAll().size());
	}
	
	@Test
	public void deletePicture_ExistsIDAsAdmin_ReturnsOK() throws Exception {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeaders(ADMIN_EMAIL, ADMIN_PASSWORD));
		MockMultipartFile file = new MockMultipartFile(DB_INSERT_FILE_PARAM_NAME, DB_INSERT_FILE_NAME, "image/jpeg",
				DB_INSERT_FILE_CONTENT.getBytes());
		PictureDTO picture = pictureService.add(file);
		int size = pictureService.getAll().size();

		ResponseEntity<Void> responseEntity = restTemplate.exchange("/pictures/" + picture.getId(), HttpMethod.DELETE,
				httpEntity, Void.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		assertEquals(size - 1, pictureService.getAll().size());
	}	

	@Test
	public void deletePicture_NotExistsID_ReturnsNotFound() throws Exception {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeaders(USER_EMAIL, USER_PASSWORD));

		ResponseEntity<ErrorMessage> responseEntity = restTemplate.exchange("/pictures/" + GET_NULL_ID, HttpMethod.DELETE,
				httpEntity, ErrorMessage.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

}
