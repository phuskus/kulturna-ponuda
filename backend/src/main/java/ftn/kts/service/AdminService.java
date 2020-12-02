package ftn.kts.service;

import ftn.kts.dto.AdminDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.kts.model.Admin;
import ftn.kts.repository.AdminRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    private AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<AdminDTO> getAll() {
        List<Admin> admins = adminRepository.findAll();
        List<AdminDTO> dtoList = new ArrayList<>();
        for (Admin o : admins) {
            dtoList.add(toDTO(o));
        }
        return dtoList;
    }

    public AdminDTO getOne(long id) {
        Admin admin = adminRepository.findById(id).get();
        AdminDTO dto = toDTO(admin);
        return dto;
    }

    public void create(AdminDTO dto) {
        Admin admin = toEntity(dto);
        adminRepository.save(admin);
    }

    public AdminDTO update(AdminDTO dto, Long id) {
        Admin admin = adminRepository.findById(id).get();
        updateAdmin(admin, dto);
        return toDTO(admin);
    }

    public void delete(Long id) {
        adminRepository.deleteById(id);
    }

    protected Admin toEntity(AdminDTO dto) {
        return new Admin("", dto.getUsername(), dto.getPassword(), dto.getCategories(), dto.getCulturalOffers());
    }

    private AdminDTO toDTO(Admin admin) {
        return new AdminDTO(admin.getId(), admin.getUsername(), admin.getPassword(), admin.getCategories(), admin.getCulturalOffers());
    }

    private Admin updateAdmin(Admin admin, AdminDTO dto){
        admin.setUsername(dto.getUsername());
        admin.setPassword(dto.getPassword());
        admin.setCategories(dto.getCategories());
        admin.setCulturalOffers(dto.getCulturalOffers());
        return admin;
    }
}
