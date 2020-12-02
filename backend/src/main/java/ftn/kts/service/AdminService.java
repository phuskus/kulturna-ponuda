package ftn.kts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.kts.model.Admin;
import ftn.kts.repository.AdminRepository;

@Service
public class AdminService {
    private AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
    
    public Admin getOne(Long id) {
    	return adminRepository.findById(id).get();
    }
}
