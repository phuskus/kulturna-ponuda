package ftn.kts.service;

import ftn.kts.dto.RegisteredUserDTO;
import ftn.kts.model.RegisteredUser;
import ftn.kts.model.User;
import ftn.kts.repository.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public List<RegisteredUserDTO> getAllDTO() {
        List<RegisteredUser> users = registeredUserRepository.findAll();
        List<RegisteredUserDTO> dtoList = new ArrayList<>();
        for (RegisteredUser user : users) {
            dtoList.add(toDTO(user));
        }
        return dtoList;
    }

    public RegisteredUserDTO getOneDTO(long id) {
        RegisteredUser user = registeredUserRepository.findById(id).get();
        RegisteredUserDTO dto = toDTO(user);
        return dto;
    }

    public void create(RegisteredUserDTO dto) {
        RegisteredUser user = toEntity(dto);
        registeredUserRepository.save(user);
    }

    public RegisteredUserDTO update(RegisteredUserDTO dto, Long id) {
        RegisteredUser user = registeredUserRepository.findById(id).get();
        updateUser(user, dto);
        registeredUserRepository.save(user);
        return toDTO(user);
    }

    public void delete(Long id) {
        registeredUserRepository.deleteById(id);
    }
    
	public RegisteredUser getOne(long id) {
		return registeredUserRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("User with id " + id + " doesn't exist!"));
	}

	public List<RegisteredUser> getAll(long id) {
		return registeredUserRepository.findAll();
	}

    private void updateUser(User user, RegisteredUserDTO dto) {
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
    }

    private RegisteredUserDTO toDTO (RegisteredUser entity) {
        RegisteredUserDTO user = new RegisteredUserDTO(entity.getName(), entity.getUsername(), entity.getPassword());
        user.setReviews(reviewService.convertToDTO(entity.getReviews()));
        user.setSubscriptions(subscriptionService.convertToDTO(entity.getSubscriptions()));
        return user;
    }

    private RegisteredUser toEntity(RegisteredUserDTO dto) {
        RegisteredUser user = new RegisteredUser(dto.getName(), dto.getUsername(), dto.getPassword());
        return user;
    }

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Autowired
    public void setSubscriptionService(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }
}
