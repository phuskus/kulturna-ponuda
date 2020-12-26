package ftn.kts.controller;

import ftn.kts.dto.CategoryDTO;
import ftn.kts.exceptions.UniqueConstraintViolationException;
import ftn.kts.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;

import static ftn.kts.util.ControllerUtil.getAuthHeadersAdmin;
import static ftn.kts.util.ControllerUtil.getAuthHeadersUser;

import static ftn.kts.constants.CategoryConstants.*;
import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Transactional
public class CategoryControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void getAll_ReturnsOkAndCategories() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(getAuthHeadersUser(restTemplate));
        ResponseEntity<CategoryDTO[]> responseEntity = restTemplate
                .exchange("/categories", HttpMethod.GET, httpEntity, CategoryDTO[].class);
        CategoryDTO[] admins = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(NUM_ITEMS, admins.length);
    }

    @Test
    public void getOne_ExistentId_ReturnsOkAndCategory() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(getAuthHeadersAdmin(restTemplate));
        ResponseEntity<CategoryDTO> responseEntity = restTemplate
                .exchange("/categories/" + EXISTENT_ID, HttpMethod.GET, httpEntity, CategoryDTO.class);
        CategoryDTO category = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(CATEGORY_NAME, category.getName());
    }

    @Test
    public void getOne_NonexistentId_ReturnsNotFound() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(getAuthHeadersAdmin(restTemplate));
        ResponseEntity<CategoryDTO> responseEntity = restTemplate
                .exchange("/categories/" + NONEXISTENT_ID, HttpMethod.GET, httpEntity, CategoryDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void add_ValidCategory_ReturnsOkAndCategory() {
        int size = categoryService.getAllDTO().size();
        CategoryDTO dto = new CategoryDTO(NONEXISTENT_CATEGORY_NAME);
        HttpEntity<Object> httpEntity = new HttpEntity<>(dto, getAuthHeadersAdmin(restTemplate));
        ResponseEntity<CategoryDTO> responseEntity = restTemplate
                .exchange("/categories", HttpMethod.POST, httpEntity, CategoryDTO.class);

        CategoryDTO category = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(size + 1, categoryService.getAllDTO().size());

        // delete admin
        categoryService.delete(category.getId());
    }

    @Test
    public void add_ExistentCategory_ReturnsBadRequest() {
        CategoryDTO dto = new CategoryDTO(CATEGORY_NAME);
        HttpEntity<Object> httpEntity = new HttpEntity<>(dto, getAuthHeadersAdmin(restTemplate));
        ResponseEntity<CategoryDTO> responseEntity = restTemplate
                .exchange("/categories", HttpMethod.POST, httpEntity, CategoryDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void add_NullCategory_ReturnsBadRequest() {
        int size = categoryService.getAllDTO().size();
        HttpEntity<Object> httpEntity = new HttpEntity<>(null, getAuthHeadersAdmin(restTemplate));
        ResponseEntity<CategoryDTO> responseEntity = restTemplate
                .exchange("/categories", HttpMethod.POST, httpEntity, CategoryDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(size, categoryService.getAllDTO().size());
    }

    @Test
    public void update_ValidNewCategory_ReturnsOk() {
        CategoryDTO oldCategory = categoryService.getOneDTO(EXISTENT_ID);
        assertEquals(CATEGORY_NAME, oldCategory.getName());
        oldCategory.setName(NONEXISTENT_CATEGORY_NAME);

        HttpEntity<Object> httpEntity = new HttpEntity<>(oldCategory, getAuthHeadersAdmin(restTemplate));
        ResponseEntity<CategoryDTO> responseEntity = restTemplate
                .exchange("/categories/" + EXISTENT_ID, HttpMethod.PUT, httpEntity, CategoryDTO.class);

        CategoryDTO newCategory = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(NONEXISTENT_CATEGORY_NAME, newCategory.getName());

        // update to old name
        newCategory.setName(CATEGORY_NAME);
        try {
            categoryService.update(newCategory, newCategory.getId());
        } catch (UniqueConstraintViolationException e) {
            e.printStackTrace();
        }

//        httpEntity = new HttpEntity<>(newCategory, getAuthHeadersAdmin(restTemplate));
//        restTemplate
//                .exchange("/categories/" + EXISTENT_ID, HttpMethod.PUT, httpEntity, CategoryDTO.class);
    }

    @Test
    public void update_ExistentName_ReturnsBadRequest() {
        CategoryDTO oldCategory = categoryService.getOneDTO(EXISTENT_ID);
        assertEquals(CATEGORY_NAME, oldCategory.getName());
        oldCategory.setName(CATEGORY_NAME2);

        HttpEntity<Object> httpEntity = new HttpEntity<>(oldCategory, getAuthHeadersAdmin(restTemplate));
        ResponseEntity<CategoryDTO> responseEntity = restTemplate
                .exchange("/categories/" + EXISTENT_ID, HttpMethod.PUT, httpEntity, CategoryDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void delete_ExistentIdNoSubcategories_ReturnsOk() {
        CategoryDTO cat = categoryService.getOneDTO(EXISTENT_ID_2);
        HttpEntity<Object> httpEntity = new HttpEntity<>(getAuthHeadersAdmin(restTemplate));
        ResponseEntity<Void> responseEntity = restTemplate
                .exchange("/categories/" + EXISTENT_ID_2, HttpMethod.DELETE, httpEntity, Void.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // add back to db
        try {
            categoryService.create(cat);
        } catch (UniqueConstraintViolationException e) {
            e.printStackTrace();
        }
//        httpEntity = new HttpEntity<>(cat, getAuthHeadersAdmin(restTemplate));
//        restTemplate.exchange("/categories", HttpMethod.POST, httpEntity, CategoryDTO.class);
    }

    @Test
    public void delete_ExistentIdHasSubcategories_ReturnsBadRequest() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(getAuthHeadersAdmin(restTemplate));
        ResponseEntity<Void> responseEntity = restTemplate
                .exchange("/categories/" + EXISTENT_ID, HttpMethod.DELETE, httpEntity, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void delete_NonexistentId_ReturnsNotFound() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(getAuthHeadersAdmin(restTemplate));
        ResponseEntity<Void> responseEntity = restTemplate
                .exchange("/categories/" + NONEXISTENT_ID, HttpMethod.DELETE, httpEntity, Void.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

}
