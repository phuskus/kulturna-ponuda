package ftn.kts.service;

import ftn.kts.dto.SubscriptionDTO;
import ftn.kts.model.CulturalOffer;
import ftn.kts.model.RegisteredUser;
import ftn.kts.model.Subscription;
import ftn.kts.model.Subcategory;
import ftn.kts.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {

	private SubscriptionRepository subscriptionRepository;

	private CulturalOfferService culturalOfferService;
	private SubcategoryService subcategoryService;
	private RegisteredUserService registeredUserService;

	@Autowired
	public SubscriptionService(SubscriptionRepository subscriptionRepository,
							   CulturalOfferService culturalOfferService, SubcategoryService subcategoryService,
							   RegisteredUserService registeredUserService) {
		this.subscriptionRepository = subscriptionRepository;
		this.culturalOfferService = culturalOfferService;
		this.subcategoryService = subcategoryService;
		this.registeredUserService = registeredUserService;
	}

	public Page<SubscriptionDTO> getAllDTO(Pageable pageable) {
		return subscriptionRepository.findAll(pageable).map(this::toDTO);
	}

	public List<RegisteredUser> getUsersForOffer(long offerId) {
		List<Subscription> subscriptions = subscriptionRepository.findByCulturalOfferId(offerId);
		List<RegisteredUser> users = new ArrayList<>();
		for (Subscription s : subscriptions) {
			users.add(s.getUser());
		}

		return users;
	}

	public SubscriptionDTO getOneDTO(long id) {
		Subscription subscription = getOne(id);
		return toDTO(subscription);
	}

	public Page<SubscriptionDTO> getAllDTOForUsername(String username, Pageable pageable) {
		return subscriptionRepository.findByUsername(username, pageable).map(this::toDTO);
	}

	public boolean isSubscribedToOffer(long userId, long offerId) {
		Subscription subscription = subscriptionRepository.findByUserAndOffer(userId, offerId);
		return subscription != null;
	}

	public boolean isSubscribedToOffer(String username, long offerId) {
		Subscription subscription = subscriptionRepository.findByUsernameAndOffer(username, offerId);
		return subscription != null;
	}

	public boolean isSubscribedToSubcategory(String username, String subcategoryName) {
		Subscription subscription = subscriptionRepository.findByUsernameAndSubcategory(username, subcategoryName);
		return subscription != null;
	}

	public String subscribeToOffer(String username, long offerId) throws Exception {
		if (isSubscribedToOffer(username, offerId)) {
			return "User " + username + " is already subscribed to offer " + offerId;
		}

		RegisteredUser user = registeredUserService.findByUsername(username);
		if (user == null) {
			throw new Exception("User " + username + " doesn't exist!");
		}

		CulturalOffer culturalOffer = culturalOfferService.getOne(offerId);
		if (culturalOffer == null) {
			throw new Exception("Cultural offer " + offerId + " doesn't exist!");
		}

		Subscription subscription = new Subscription(new Date(), null, culturalOffer, user);
		subscriptionRepository.save(subscription);

		return "Successfully subscribed user " + username + " to offer " + offerId;
	}

	public String unsubscribeFromOffer(String username, long offerId) throws Exception {
		RegisteredUser user = registeredUserService.findByUsername(username);
		if (user == null) {
			throw new Exception("User " + username + " doesn't exist!");
		}

		CulturalOffer culturalOffer = culturalOfferService.getOne(offerId);
		if (culturalOffer == null) {
			throw new Exception("Cultural offer " + offerId + " doesn't exist!");
		}

		Subscription subscription = subscriptionRepository.findByUserAndOffer(user.getId(), offerId);
		if (subscription == null) {
			throw new Exception("User " + username + " is not subscribed to offer " + offerId);
		}

		subscriptionRepository.delete(subscription);

		return "Successfully unsubscribed user " + username + " from offer " + offerId;
	}

	public String subscribeToSubcategory(String username, String subcategoryName) throws Exception {
		if (isSubscribedToSubcategory(username, subcategoryName)) {
			return "User " + username + " is already subscribed to subcategory " + subcategoryName;
		}

		RegisteredUser user = registeredUserService.findByUsername(username);
		if (user == null) {
			throw new Exception("User " + username + " doesn't exist!");
		}

		Subcategory subcategory = subcategoryService.findByNameIgnoringCase(subcategoryName);
		if (subcategory == null) {
			throw new Exception("Subcategory " + subcategoryName + " doesn't exist!");
		}

		Subscription subscription = new Subscription(new Date(), subcategory, null, user);
		subscriptionRepository.save(subscription);

		return "Successfully subscribed user " + username + " to subcategory " + subcategoryName;
	}

	public String unsubscribeFromSubcategory(String username, String subcategoryName) throws Exception {
		RegisteredUser user = registeredUserService.findByUsername(username);
		if (user == null) {
			throw new Exception("User " + username + " doesn't exist!");
		}

		Subcategory subcategory = subcategoryService.findByNameIgnoringCase(subcategoryName);
		if (subcategory == null) {
			throw new Exception("Subcategory " + subcategoryName + " doesn't exist!");
		}

		Subscription subscription = subscriptionRepository.findByUsernameAndSubcategory(username, subcategoryName);
		if (subscription == null) {
			throw new Exception("User " + username + " is not subscribed to subcategory " + subcategoryName);
		}

		subscriptionRepository.delete(subscription);

		return "Successfully unsubscribed user " + username + " from subcategory " + subcategoryName;
	}

	public SubscriptionDTO create(SubscriptionDTO dto) {
		Subscription subscription = toEntity(dto);
		subscription = subscriptionRepository.save(subscription);
		return toDTO(subscription);
	}

	public SubscriptionDTO update(SubscriptionDTO dto, Long id) throws Exception {
		Subscription subscription = getOne(id);
		updateSubscription(subscription, dto);
		return toDTO(subscription);
	}

	public void delete(Long id) {
		Subscription sub = getOne(id);
		subscriptionRepository.delete(sub);
	}

	public Subscription getOne(long id) {
		return subscriptionRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Subscription with id " + id + " doesn't exist!"));
	}

	public List<Subscription> getAll() {
		return subscriptionRepository.findAll();
	}

	public Set<SubscriptionDTO> convertToDTO(Set<Subscription> subscriptions) {
		return subscriptions.stream().map(this::toDTO).collect(Collectors.toSet());
	}
	
	private Subscription toEntity(SubscriptionDTO dto) {
		Subcategory subCategory = null;
		if (dto.getSubcategoryId() != null) {
			subCategory = subcategoryService.getOne(dto.getSubcategoryId());
		}
		CulturalOffer culturalOffer = null;
		if (dto.getCulturalOfferId() != null) {
			culturalOffer = culturalOfferService.getOne(dto.getCulturalOfferId());
		}
		RegisteredUser registeredUser = registeredUserService.getOne(dto.getRegisteredUserId());
		return new Subscription(dto.getId(), dto.getDateOfSubscription(), subCategory, culturalOffer, registeredUser);
	}

	private SubscriptionDTO toDTO(Subscription entity) {
		return new SubscriptionDTO(entity.getId(), entity.getDateOfSubscription(), entity.getUser(),
				entity.getSubcategory(), entity.getCulturalOffer());
	}

	private void updateSubscription(Subscription subscription, SubscriptionDTO dto) throws Exception{
		subscription.setDateOfSubscription(dto.getDateOfSubscription());
		subscription.setUser(registeredUserService.getOne(dto.getRegisteredUserId()));

		if (dto.getCulturalOfferId() != null) {

		} else if (dto.getSubcategoryId() != null) {

		} else {
			throw new Exception("A subscription must be tied to a subcategory or a cultural offer.");
		}
		subscription.setCulturalOffer(culturalOfferService.getOne(dto.getCulturalOfferId()));
		subscription.setSubcategory(subcategoryService.getOne(dto.getCulturalOfferId()));
	}

}
