package ftn.kts.util;

import static ftn.kts.constants.AuthenticationConstants.USER_EMAIL;
import static ftn.kts.constants.AuthenticationConstants.USER_PASSWORD;
import static ftn.kts.constants.AuthenticationConstants.ADMIN_EMAIL;
import static ftn.kts.constants.AuthenticationConstants.ADMIN_PASSWORD;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import ftn.kts.dto.UserTokenStateDTO;
import ftn.kts.security.auth.JwtAuthenticationRequest;


public class ControllerUtil {
	public static HttpHeaders getAuthHeadersUser(TestRestTemplate restTemplate) {
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/login",
				new JwtAuthenticationRequest(USER_EMAIL, USER_PASSWORD), UserTokenStateDTO.class);
		String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", accessToken);
		/*List<MediaType> acceptList = new ArrayList<>();
		acceptList.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptList);*/

		return headers;
	}

	public static HttpHeaders getAuthHeadersAdmin(TestRestTemplate restTemplate) {
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/login",
				new JwtAuthenticationRequest(ADMIN_EMAIL, ADMIN_PASSWORD), UserTokenStateDTO.class);
		String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", accessToken);
		/*List<MediaType> acceptList = new ArrayList<>();
		acceptList.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptList);*/

		return headers;
	}
}
