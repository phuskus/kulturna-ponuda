package ftn.kts.controller;

import ftn.kts.dto.AdminDTO;
import ftn.kts.exceptions.ErrorMessage;
import ftn.kts.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static ftn.kts.constants.AdminConstants.*;

import static ftn.kts.util.ControllerUtil.getAuthHeadersAdmin;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static ftn.kts.util.ControllerUtil.getAuthHeadersUser;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class AdminControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AdminService adminService;

    @Test
    public void getAll_ReturnsAllAdmins() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(getAuthHeadersUser(restTemplate));
        ResponseEntity<AdminDTO[]> responseEntity = restTemplate
                .exchange("/admins", HttpMethod.GET, httpEntity, AdminDTO[].class);
        AdminDTO[] admins = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, admins.length);
    }

    @Test
    public void getOne_ExistentId_ReturnsOkAndAdmin() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(getAuthHeadersAdmin(restTemplate));
        ResponseEntity<AdminDTO> responseEntity = restTemplate
                .exchange("/admins/" + EXISTENT_ID, HttpMethod.GET, httpEntity, AdminDTO.class);
        AdminDTO admin = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(EXISTENT_NAME, admin.getName());
    }

    @Test
    public void getOne_NonexistentId_ReturnsNotFound() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(getAuthHeadersAdmin(restTemplate));
        ResponseEntity<AdminDTO> responseEntity = restTemplate
                .exchange("/admins/" + NONEXISTENT_ID, HttpMethod.GET, httpEntity, AdminDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void add_ValidAdmin_ReturnsCreatedAndAdmin() {
        int size = adminService.getAllDTO().size();
        AdminDTO dto = new AdminDTO("name", "user", "pass");
        HttpEntity<Object> httpEntity = new HttpEntity<>(dto, getAuthHeadersAdmin(restTemplate));
        ResponseEntity<AdminDTO> responseEntity = restTemplate
                .exchange("/admins", HttpMethod.POST, httpEntity, AdminDTO.class);

        AdminDTO admin = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(size + 1, adminService.getAllDTO().size());

        // delete admin
        adminService.delete(admin.getId());
    }

    @Test
    public void add_NullAdmin_ReturnsBadRequest() {
        int size = adminService.getAllDTO().size();
        HttpEntity<Object> httpEntity = new HttpEntity<>(null, getAuthHeadersAdmin(restTemplate));
        ResponseEntity<AdminDTO> responseEntity = restTemplate
                .exchange("/admins", HttpMethod.POST, httpEntity, AdminDTO.class);

        AdminDTO admin = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(size, adminService.getAllDTO().size());
    }

    @Test
    public void update_ExistentId_ReturnsOk() {
        AdminDTO oldAdmin = adminService.getOneDTO(EXISTENT_ID);
        assertEquals(EXISTENT_NAME, oldAdmin.getName());
        oldAdmin.setName(NEW_NAME);
        HttpEntity<Object> httpEntity = new HttpEntity<>(oldAdmin, getAuthHeadersAdmin(restTemplate));
        ResponseEntity<AdminDTO> responseEntity = restTemplate
                .exchange("/admins/" + EXISTENT_ID, HttpMethod.PUT, httpEntity, AdminDTO.class);

        AdminDTO newAdmin = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(NEW_NAME, newAdmin.getName());

        // update to old name
        newAdmin.setName(EXISTENT_NAME);
        adminService.update(newAdmin, newAdmin.getId());
    }
}
