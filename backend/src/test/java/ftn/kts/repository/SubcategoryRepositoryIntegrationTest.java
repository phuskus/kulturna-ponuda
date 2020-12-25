package ftn.kts.repository;

import ftn.kts.model.Subcategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static ftn.kts.constants.SubcategoryConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SubcategoryRepositoryIntegrationTest {

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Test
    public void findAll_ReturnsAllSubcategories() {
        List<Subcategory> subcategories = subcategoryRepository.findAll();
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, subcategories.size());
    }

    @Test
    public void findByName_ExistingName_ReturnsSubcategory() {
        Subcategory subcategory = subcategoryRepository.findByNameIgnoringCase(DB_SUBCATEGORY_NAME);
        assertEquals(subcategory.getName(), DB_SUBCATEGORY_NAME);
    }

    @Test
    public void findByName_ExistingNameUpperCase_ReturnsSubcategory() {
        Subcategory subcategory = subcategoryRepository.findByNameIgnoringCase(DB_SUBCATEGORY_NAME.toUpperCase());
        assertEquals(subcategory.getName(), DB_SUBCATEGORY_NAME);
    }

    @Test
    public void findByName_ExistingNameLowerCase_ReturnsSubcategory() {
        Subcategory subcategory = subcategoryRepository.findByNameIgnoringCase(DB_SUBCATEGORY_NAME.toLowerCase());
        assertEquals(subcategory.getName(), DB_SUBCATEGORY_NAME);
    }

    @Test
    public void findByName_NonexistentName_ReturnsNull() {
        Subcategory subcategory = subcategoryRepository.findByNameIgnoringCase(DB_NONEXISTENT_SUBCATEGORY_NAME);
        assertNull(subcategory);
    }

    @Test
    public void findByName_NullName_ReturnsNull() {
        Subcategory subcategory = subcategoryRepository.findByNameIgnoringCase(null);
        assertNull(subcategory);
    }
}
