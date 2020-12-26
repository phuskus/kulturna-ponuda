package ftn.kts.service;

import ftn.kts.dto.AdminDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static ftn.kts.constants.AdminConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class AdminServiceIntegrationTest {

    @Autowired
    private AdminService adminService;

    @Test
    public void getAll_ReturnsAllAdmins() {
        List<AdminDTO> list = adminService.getAllDTO();
        assertNotNull(list);
        assertEquals(NUM_ITEMS, list.size());
    }

    @Test
    public void getOne_ExistentId_ReturnsAdmin() {
        AdminDTO admin = adminService.getOneDTO(EXISTENT_ID);
        assertNotNull(admin);
        assertEquals(EXISTENT_NAME, admin.getName());
    }

    @Test
    public void getOne_NonexistentId_ThrowsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> adminService.getOneDTO(NONEXISTENT_ID));
    }

    @Test
    public void createDelete_ValidNewObject_CreatesAndDeletesSuccessfully() {
        AdminDTO admin = new AdminDTO(NEW_NAME, "user", "pass");

        AdminDTO createdAdmin = adminService.create(admin);
        assertEquals(admin, createdAdmin);

        // clean up
        adminService.delete(createdAdmin.getId());
        assertThrows(NoSuchElementException.class, () -> adminService.getOneDTO(createdAdmin.getId()));
    }

    @Test
    public void delete_NonexistentId_ThrowsNoSuchElementException() {
        assertThrows(EmptyResultDataAccessException.class, () -> adminService.delete(NONEXISTENT_ID));
    }

    @Test
    public void update_SetNewContent_ContentChanged() {
        AdminDTO oldAdmin = adminService.getOneDTO(EXISTENT_ID);
        assertEquals(EXISTENT_NAME, oldAdmin.getName());

        oldAdmin.setName(NEW_NAME);
        adminService.update(oldAdmin, oldAdmin.getId());
        AdminDTO newAdmin = adminService.getOneDTO(EXISTENT_ID);
        assertEquals(NEW_NAME, newAdmin.getName());

        // return to old value
        newAdmin.setName(EXISTENT_NAME);
        adminService.update(newAdmin, newAdmin.getId());
        assertEquals(EXISTENT_NAME, adminService.getOneDTO(EXISTENT_ID).getName());
    }

}
