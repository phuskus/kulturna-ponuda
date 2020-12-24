package ftn.kts.repository;

import ftn.kts.model.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static ftn.kts.constants.AdminConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminRepositoryIntegrationTest {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    public void findAll_ReturnsAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, admins.size());
    }
}
