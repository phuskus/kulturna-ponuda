package ftn.kts.service;

import static ftn.kts.constants.CategoryConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryServiceUnitTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;



    @Test
    public void add_NonexistentName_ReturnsNewCategory() {
        CategoryDTO dto = new CategoryDTO(NONEXISTENT_CATEGORY_NAME);

        given(categoryRepository.findByNameIgnoringCase(NONEXISTENT_CATEGORY_NAME)).willReturn(null);
        given(categoryRepository.save(any())).willReturn(new Category(NONEXISTENT_ID, NONEXISTENT_CATEGORY_NAME));

        try {
            CategoryDTO created = categoryService.create(dto);
            verify(categoryRepository, times(1)).findByNameIgnoringCase(NONEXISTENT_CATEGORY_NAME);
            verify(categoryRepository, times(1)).save(any());
            assertEquals(created, dto);
        } catch (UniqueConstraintViolationException e) {
            fail();
        }
    }

    @Test
    public void add_ExistentName_ThrowsUniqueConstraintViolationException() {
        CategoryDTO dto = new CategoryDTO(EXISTENT_ID, CATEGORY_NAME2);
        Category existingCategory = new Category(EXISTENT_ID_2, CATEGORY_NAME2);

        given(categoryRepository.findByNameIgnoringCase(CATEGORY_NAME2)).willReturn(existingCategory);

        assertThrows(UniqueConstraintViolationException.class, () -> categoryService.create(dto));
        verify(categoryRepository, times(1)).findByNameIgnoringCase(CATEGORY_NAME2);
    }

    @Test
    public void update_NonexistentName_ReturnsChangedCategory() {
        CategoryDTO dto = new CategoryDTO(NONEXISTENT_CATEGORY_NAME);
        Category existingCategory = new Category(EXISTENT_ID, CATEGORY_NAME);

        given(categoryRepository.findById(any())).willReturn(Optional.of(existingCategory));
        given(categoryRepository.findByNameIgnoringCase(NONEXISTENT_CATEGORY_NAME)).willReturn(existingCategory);
        given(categoryRepository.save(any())).willReturn(existingCategory);

        try {
            CategoryDTO created = categoryService.update(dto, EXISTENT_ID);
            verify(categoryRepository, times(1)).findById(any());
            verify(categoryRepository, times(1)).findByNameIgnoringCase(NONEXISTENT_CATEGORY_NAME);
            verify(categoryRepository, times(1)).save(any());
            assertEquals(created, dto);
        } catch (UniqueConstraintViolationException e) {
            fail();
        }

    }

    @Test
    public void update_ExistentName_ThrowsUniqueConstraintViolationException() {
        CategoryDTO dto = new CategoryDTO(CATEGORY_NAME2);
        Category existingCategory = new Category(EXISTENT_ID_2, CATEGORY_NAME2);

        given(categoryRepository.findById(any())).willReturn(Optional.of(existingCategory));
        given(categoryRepository.findByNameIgnoringCase(CATEGORY_NAME2)).willReturn(existingCategory);

        assertThrows(UniqueConstraintViolationException.class, () -> categoryService.update(dto, EXISTENT_ID));
        verify(categoryRepository, times(1)).findById(any());
        verify(categoryRepository, times(1)).findByNameIgnoringCase(CATEGORY_NAME2);
    }
}
