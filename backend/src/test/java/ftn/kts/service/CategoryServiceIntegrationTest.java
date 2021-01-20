package ftn.kts.service;

import ftn.kts.dto.CategoryDTO;
import ftn.kts.exceptions.UniqueConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static ftn.kts.constants.CategoryConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class CategoryServiceIntegrationTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void getAll_ReturnsAllCategories() {
        List<CategoryDTO> list = categoryService.getAllDTO();
        assertNotNull(list);
    }

    @Test
    public void getOne_ExistentId_ReturnsCategory() {
		CategoryDTO category = categoryService.getOneDTO(EXISTENT_ID);
		assertNotNull(category);
		assertEquals(CATEGORY_NAME, category.getName());    	
    }

    @Test
    public void getOne_NonexistentId_ThrowsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> categoryService.getOneDTO(NONEXISTENT_ID));
    }

    @Test
    public void createDelete_ValidNewObject_CreatesAndDeletesSuccessfully() {
        CategoryDTO category = new CategoryDTO(NONEXISTENT_CATEGORY_NAME);

        try {
            CategoryDTO createdCategory = categoryService.create(category);
            assertEquals(category, createdCategory);

            //clean up
            categoryService.delete(createdCategory.getId());
            assertThrows(NoSuchElementException.class, () -> categoryService.getOneDTO(createdCategory.getId()));
        } catch (UniqueConstraintViolationException e) {
            e.printStackTrace();
            fail("Category name not unique");
        }
    }

    @Test
    public void create_ExistentName_ThrowsUniqueConstraintValidation() {
        CategoryDTO category = new CategoryDTO(CATEGORY_NAME);
        assertThrows(UniqueConstraintViolationException.class, () -> categoryService.create(category));
    }

    @Test
    public void delete_NonexistentId_ThrowsNoSuchElementException() {
        assertThrows(EmptyResultDataAccessException.class, () -> categoryService.delete(NONEXISTENT_ID));
    }

    @Test
    public void update_SetNewContent_ContentChanged() {
        CategoryDTO oldCategory = categoryService.getOneDTO(EXISTENT_ID);
        assertEquals(CATEGORY_NAME, oldCategory.getName());

        oldCategory.setName(NONEXISTENT_CATEGORY_NAME);
        try {
            categoryService.update(oldCategory, oldCategory.getId());
            CategoryDTO newCategory = categoryService.getOneDTO(EXISTENT_ID);
            assertEquals(NONEXISTENT_CATEGORY_NAME, newCategory.getName());

            // return to old value
            newCategory.setName(CATEGORY_NAME);
            categoryService.update(newCategory, newCategory.getId());
            assertEquals(CATEGORY_NAME, categoryService.getOneDTO(EXISTENT_ID).getName());
        } catch (UniqueConstraintViolationException e) {
            e.printStackTrace();
            fail("Category name not unique");
        }
    }

    @Test
    public void update_SetExistentName_ThrowsUniqueConstraintValidation() {
        CategoryDTO category = categoryService.getOneDTO(EXISTENT_ID);
        category.setName(CATEGORY_NAME2);
        assertThrows(UniqueConstraintViolationException.class, () -> categoryService.update(category, category.getId()));
    }


}
