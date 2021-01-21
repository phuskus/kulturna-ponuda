package ftn.kts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.kts.dto.AdminDTO;
import ftn.kts.model.Admin;
import ftn.kts.model.Authority;
import ftn.kts.model.User;
import ftn.kts.repository.AdminRepository;

@Service
public class AdminService {
    private AdminRepository adminRepository;
    private UserService userService;
    private AuthorityService authService;

    @Autowired
    public AdminService(AdminRepository adminRepository, UserService userService, AuthorityService authService) {
        this.adminRepository = adminRepository;
        this.userService = userService;
        this.authService = authService;
    }

    public List<AdminDTO> getAllDTO() {
        List<Admin> admins = adminRepository.findAll();
        List<AdminDTO> dtoList = new ArrayList<>();
        for (Admin o : admins) {
            dtoList.add(toDTO(o));
        }
        return dtoList;
    }

    public AdminDTO getOneDTO(long id) {
        Admin admin = adminRepository.findById(id).get();
        AdminDTO dto = toDTO(admin);
        return dto;
    }

    public AdminDTO create(AdminDTO dto) {
        Admin admin = toEntity(dto);
        ArrayList<Authority> auth = new ArrayList<>();
        auth.add(authService.findByName("ROLE_ADMIN"));
        admin.setAuthorities(auth);
        AdminDTO saved = toDTO(adminRepository.save(admin));
        return saved;
    }
    
    public AdminDTO update(AdminDTO dto, Long id) {
        Admin admin = adminRepository.findById(id).get();
        adminRepository.save(updateAdmin(admin, dto));
        return toDTO(admin);
    }

    public void delete(Long id) {
        adminRepository.deleteById(id);
    }

    public Admin getOne(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Admin with id " + id + " doesn't exist!"));
    }

    public List<Admin> getAll() {
        return adminRepository.findAll();
    }

    protected Admin toEntity(AdminDTO dto) {
        return new Admin(dto.getName(), dto.getSurname(), dto.getUsername(), dto.getPassword());
    }

    private AdminDTO toDTO(Admin admin) {
        return new AdminDTO(admin.getId(), admin.getName(), admin.getSurname(), admin.getUsername(), admin.getPassword());
    }

    private Admin updateAdmin(Admin admin, AdminDTO dto) {
        admin.setName(dto.getName());
        admin.setSurname(dto.getSurname());
        admin.setUsername(dto.getUsername());
        admin.setPassword(dto.getPassword());
//        admin.setCategories(dto.getCategories());
//        admin.setCulturalOffers(dto.getCulturalOffers());
        return admin;
    }
}
