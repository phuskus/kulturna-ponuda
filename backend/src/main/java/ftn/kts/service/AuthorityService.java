package ftn.kts.service;

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
	
}
