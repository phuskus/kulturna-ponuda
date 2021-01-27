package ftn.kts.service;

import ftn.kts.dto.RegisteredUserDTO;
import ftn.kts.model.RegisteredUser;
import ftn.kts.model.User;
import ftn.kts.repository.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class RegisteredUserService {

    private RegisteredUserRepository registeredUserRepository;
    private ReviewService reviewService;
    private SubscriptionService subscriptionService;

    @Autowired
    public RegisteredUserService(RegisteredUserRepository registeredUserRepository) {
        this.registeredUserRepository = registeredUserRepository;
    }
    
    public RegisteredUser getOne(long id) {
		return registeredUserRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("User with id " + id + " doesn't exist!"));
	}

	public RegisteredUser findByUsername(String username) {
        return registeredUserRepository.findByUsername(username);
    }
    
    //TODO after code refactoring add update method for changing name and surname
}
