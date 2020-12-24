package ftn.kts.repository;

import ftn.kts.model.Category;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static ftn.kts.constants.CategoryConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryRepositoryIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void findAll_ReturnsAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, categories.size());
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
    public void findByName_NonexistentName_ReturnsNull() {
        Category category = categoryRepository.findByNameIgnoringCase(DB_NONEXISTENT_CATEGORY_NAME);
        assertNull(category);
    }

    @Test
    public void findByName_NullName_ReturnsNull() {
        Category category = categoryRepository.findByNameIgnoringCase(null);
        assertNull(category);
    }
}
