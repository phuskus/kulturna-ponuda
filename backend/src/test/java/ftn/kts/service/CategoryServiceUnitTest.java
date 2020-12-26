package ftn.kts.service;

import static ftn.kts.constants.CategoryConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import ftn.kts.dto.CategoryDTO;
import ftn.kts.exceptions.UniqueConstraintViolationException;
import ftn.kts.model.Category;
import ftn.kts.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryServiceUnitTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;


    @Before
    public void setup() {

        Category cat1 = new Category(CATEGORY_NAME);

        Category newCat = new Category(NONEXISTENT_CATEGORY_NAME);

        given(categoryRepository.findById(EXISTENT_ID)).willReturn(java.util.Optional.of(cat1));
        given(categoryRepository.save(newCat)).willReturn(newCat);


    }

    @Test
    public void add_NonexistentName_ReturnsNewCategory(){

    }

    @Test
    public void add_ExistentName_ThrowsUniqueConstraintViolationException(){

    }

    @Test
    public void update_NonexistentName_ReturnsChangedCategory() {
        CategoryDTO cat = new CategoryDTO(NONEXISTENT_CATEGORY_NAME);

        try {
            CategoryDTO changed = categoryService.update(cat, EXISTENT_ID);
            assertEquals(cat, changed);
        } catch (UniqueConstraintViolationException e) {
            fail("Category name not unique");
        }

    }

    @Test
    public void update_ExistentName_ThrowsUniqueConstraintViolationException() {
        CategoryDTO cat = new CategoryDTO(CATEGORY_NAME);
        assertThrows(UniqueConstraintViolationException.class, () -> categoryService.update(cat, EXISTENT_ID));
    }
}
