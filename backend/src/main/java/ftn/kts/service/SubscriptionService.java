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

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
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

	public SubscriptionDTO getOneDTO(long id) {
		Subscription subscription = getOne(id);
		return toDTO(subscription);
	}

	public void create(SubscriptionDTO dto) {
		Subscription subscription = toEntity(dto);
		subscriptionRepository.save(subscription);
	}

	public SubscriptionDTO update(SubscriptionDTO dto, Long id) {
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
		Subcategory subCategory = subcategoryService.getOne(dto.getSubcategoryId());
		CulturalOffer culturalOffer = culturalOfferService.getOne(dto.getCulturalOfferId());
		RegisteredUser registeredUser = registeredUserService.getOne(dto.getRegisteredUserId());
		return new Subscription(dto.getId(), dto.getDateOfSubscription(), subCategory, culturalOffer, registeredUser);
	}

	private SubscriptionDTO toDTO(Subscription entity) {
		return new SubscriptionDTO(entity.getId(), entity.getDateOfSubscription(), entity.getUser(),
				entity.getSubcategory(), entity.getCulturalOffer());
	}

	private void updateSubscription(Subscription subscription, SubscriptionDTO dto) {
		subscription.setDateOfSubscription(dto.getDateOfSubscription());
		subscription.setUser(registeredUserService.getOne(dto.getRegisteredUserId()));

		subscription.setCulturalOffer(culturalOfferService.getOne(dto.getCulturalOfferId()));
		subscription.setSubcategory(subcategoryService.getOne(dto.getCulturalOfferId()));
	}

}
