package ftn.kts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import ftn.kts.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.kts.dto.AdminDTO;
import ftn.kts.model.Admin;
import ftn.kts.model.Authority;
import ftn.kts.model.User;
import ftn.kts.repository.AdminRepository;
import ftn.kts.security.CustomUserDetailsService;

@Service
public class AdminService {
    private AdminRepository adminRepository;
    private AuthorityService authService;
    private CustomUserDetailsService userDetailsService;
    private MailSenderService mailService;
    private UserService userService;

    @Autowired
    public AdminService(AdminRepository adminRepository, UserService userService, AuthorityService authService, CustomUserDetailsService userDetailsService, MailSenderService mailService) {
        this.adminRepository = adminRepository;
        this.authService = authService;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.mailService = mailService;
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

    public AdminDTO create2(AdminDTO dto) {
        Admin admin = toEntity(dto);
        admin.setPassword(userDetailsService.encodePassword(dto.getPassword()));
        ArrayList<Authority> auth = new ArrayList<>();
        auth.add(authService.findByName("ROLE_ADMIN"));
        admin.setAuthorities(auth);
        admin.setEnabled(true);
        AdminDTO saved = toDTO(adminRepository.save(admin));
        return saved;
    }

    public AdminDTO create(AdminDTO dto) {
        Admin admin = toEntity(dto);
        userService.createUserAuthority(admin, "ROLE_ADMIN");
        admin.setEnabled(true);
        sendConfirmation(admin);
        return toDTO(adminRepository.save(admin));
    }

    private void sendConfirmation(Admin admin) {
        String generatedKey = RandomUtil.buildAuthString(30);
        admin.setResetKey(generatedKey);
        mailService.setPasswordForAdmin(admin, generatedKey);
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
