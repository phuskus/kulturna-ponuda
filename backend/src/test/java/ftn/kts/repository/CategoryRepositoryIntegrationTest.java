package ftn.kts.repository;

import ftn.kts.model.Category;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import static ftn.kts.constants.CategoryConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryRepositoryIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;


    @Test
    public void findAllPageable_ValidPageableObject_ReturnsAllCategories() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        Page<Category> found = categoryRepository.findAll(pageable);
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.getTotalElements());
    }

    @Test
    public void findAllPageable_NullPageableObject_ThrowsNullPtrException() {
        Pageable pageable = null;
        assertThrows(NullPointerException.class, () -> categoryRepository.findAll(pageable));
    }

    @Test
    public void findByName_ExistingName_ReturnsCategory() {
        Category category = categoryRepository.findByNameIgnoringCase(DB_CATEGORY_NAME);
        assertEquals(category.getName(), DB_CATEGORY_NAME);
    }

    @Test
    public void findByName_ExistingNameUpperCase_ReturnsCategory() {
        Category category = categoryRepository.findByNameIgnoringCase(DB_CATEGORY_NAME.toUpperCase());
        assertEquals(category.getName(), DB_CATEGORY_NAME);
    }

    @Test
    public void findByName_ExistingNameLowerCase_ReturnsCategory() {
        Category category = categoryRepository.findByNameIgnoringCase(DB_CATEGORY_NAME.toLowerCase());
        assertEquals(category.getName(), DB_CATEGORY_NAME);
    }

    @Test
    public void findByName_NonexistentName_ReturnsNull(){
        Category category = categoryRepository.findByNameIgnoringCase(DB_NONEXISTENT_CATEGORY_NAME);
        assertNull(category);
    }

    @Test
    public void findByName_NullName_ReturnsNull(){
        Category category = categoryRepository.findByNameIgnoringCase(null);
        assertNull(category);
    }
}
