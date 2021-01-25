package ftn.kts.service;

import ftn.kts.dto.PictureDTO;
import ftn.kts.dto.SubcategoryDTO;
import ftn.kts.exceptions.UniqueConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static ftn.kts.constants.SubcategoryConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class SubcategoryServiceIntegrationTest {

    @Autowired
    private SubcategoryService subcategoryService;

    @Test
    public void getAll_ReturnsAllSubcategoriesPageable() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        Page<SubcategoryDTO> list = subcategoryService.getAllDTO(pageable);
        assertEquals(list.getNumberOfElements(), PAGEABLE_SIZE);
    }

    @Test
    public void getOne_ExistentId_ReturnsSubcategory() {
        SubcategoryDTO subcategory = subcategoryService.getOneDTO(EXISTENT_ID);
        assertNotNull(subcategory);
        assertEquals(DB_SUBCATEGORY_NAME, subcategory.getName());
    }

    @Test
    public void getOne_NonexistentId_ThrowsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> subcategoryService.getOneDTO(NONEXISTENT_ID));
    }
/*
    @Test
    public void createDelete_ValidNewObject_CreatesAndDeletesSuccessfully() {
        PictureDTO icon = new PictureDTO();
        icon.setId(1L);
    	SubcategoryDTO subcategory = new SubcategoryDTO(DB_NONEXISTENT_SUBCATEGORY_NAME, DB_CATEGORY_ID, icon);

        try {
            SubcategoryDTO createdSubcategory = subcategoryService.create(subcategory);
            assertEquals(subcategory, createdSubcategory);

            //clean up
            subcategoryService.delete(createdSubcategory.getId());
            assertThrows(NoSuchElementException.class, () -> subcategoryService.getOneDTO(createdSubcategory.getId()));
        } catch (UniqueConstraintViolationException e) {
            e.printStackTrace();
            fail("Subcategory name not unique");
        }
    }

    @Test
    public void create_ExistentName_ThrowsUniqueConstraintValidation() {
        PictureDTO icon = new PictureDTO();
        icon.setId(1L);
        SubcategoryDTO subcategory = new SubcategoryDTO(DB_SUBCATEGORY_NAME, DB_CATEGORY_ID, icon);
        assertThrows(UniqueConstraintViolationException.class, () -> subcategoryService.create(subcategory));
    }
*/
    @Test
    public void delete_NonexistentId_ThrowsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> subcategoryService.delete(NONEXISTENT_ID));
    }

    @Test
    public void update_SetNewContent_ContentChanged() {
        SubcategoryDTO oldSubcategory = subcategoryService.getOneDTO(EXISTENT_ID);
        assertEquals(DB_SUBCATEGORY_NAME, oldSubcategory.getName());

        oldSubcategory.setName(DB_NONEXISTENT_SUBCATEGORY_NAME);
        try {
            subcategoryService.update(oldSubcategory, oldSubcategory.getId());
            SubcategoryDTO newSubcategory = subcategoryService.getOneDTO(EXISTENT_ID);
            assertEquals(DB_NONEXISTENT_SUBCATEGORY_NAME, newSubcategory.getName());

            // return to old value
            newSubcategory.setName(DB_SUBCATEGORY_NAME);
            subcategoryService.update(newSubcategory, newSubcategory.getId());
            assertEquals(DB_SUBCATEGORY_NAME, subcategoryService.getOneDTO(EXISTENT_ID).getName());
        } catch (UniqueConstraintViolationException e) {
            e.printStackTrace();
            fail("Subcategory name not unique");
        }
    }

    @Test
    public void update_SetExistentName_ThrowsUniqueConstraintValidation() {
        SubcategoryDTO subcategory = subcategoryService.getOneDTO(EXISTENT_ID);
        subcategory.setName(DB_SUBCATEGORY_NAME2);
        assertThrows(UniqueConstraintViolationException.class, () -> subcategoryService.update(subcategory, subcategory.getId()));
    }

}
