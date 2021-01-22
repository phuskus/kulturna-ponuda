package ftn.kts.service;

import ftn.kts.model.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.kts.repository.AuthorityRepository;

@Service
public class AuthorityService {

    private AuthorityRepository authRepository;

    @Autowired
    public AuthorityService(AuthorityRepository authRepository) {
        this.authRepository = authRepository;
    }

    public Authority findOne(long id) {
        return authRepository.getOne(id);
    }

    public Authority findByName(String name) {
        return authRepository.findByName(name);
    }
    
    public Authority create(String name) {
    	Authority role = new Authority(name);
    	return authRepository.save(role);
    }

}
