package ftn.kts.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import ftn.kts.dto.UserTokenStateDTO;
import ftn.kts.security.auth.JwtAuthenticationRequest;

public class ControllerUtil {
	public static HttpHeaders getAuthHeaders(TestRestTemplate restTemplate, String username, String password) {
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/login",
				new JwtAuthenticationRequest(username, password), UserTokenStateDTO.class);
		String accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", accessToken);
		List<MediaType> acceptList = new ArrayList<>();
		acceptList.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptList);

		return headers;
	}
}
