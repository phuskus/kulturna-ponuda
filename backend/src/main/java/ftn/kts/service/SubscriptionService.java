package ftn.kts.service;

import ftn.kts.dto.SubscriptionDTO;
import ftn.kts.model.CulturalOffer;
import ftn.kts.model.RegisteredUser;
import ftn.kts.model.Subscription;
import ftn.kts.model.Subcategory;
import ftn.kts.repository.CulturalOfferRepository;
import ftn.kts.repository.RegisteredUserRepository;
import ftn.kts.repository.SubcategoryRepository;
import ftn.kts.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SubscriptionService {

	private SubscriptionRepository subscriptionRepository;
	private CulturalOfferRepository culturalOfferRepository;
	private SubcategoryRepository subcategoryRepository;
	private RegisteredUserRepository registeredUserRepository;

	@Autowired
	public SubscriptionService(SubscriptionRepository subscriptionRepository,
			CulturalOfferRepository culturalOfferRepository, SubcategoryRepository subcategoryRepository,
			RegisteredUserRepository registeredUserRepository) {
		this.subscriptionRepository = subscriptionRepository;
		this.culturalOfferRepository = culturalOfferRepository;
		this.subcategoryRepository = subcategoryRepository;
		this.registeredUserRepository = registeredUserRepository;
	}

	public List<SubscriptionDTO> getAllDTO() {
		List<Subscription> subscriptions = subscriptionRepository.findAll();
		List<SubscriptionDTO> dtos = new ArrayList<>();
		for (Subscription s : subscriptions) {
			dtos.add(toDTO(s));
		}
		return dtos;
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
	
	private Subscription toEntity(SubscriptionDTO dto) {
		Subcategory subCategory = subcategoryRepository.findById(dto.getSubcategoryId()).get();
		CulturalOffer culturalOffer = culturalOfferRepository.findById(dto.getCulturalOfferId()).get();
		RegisteredUser registeredUser = registeredUserRepository.findById(dto.getRegisteredUserId()).get();
		return new Subscription(dto.getId(), dto.getDateOfSubscription(), subCategory, culturalOffer, registeredUser);
	}

	private SubscriptionDTO toDTO(Subscription entity) {
		return new SubscriptionDTO(entity.getId(), entity.getDateOfSubscription(), entity.getUser(),
				entity.getSubcategory(), entity.getCulturalOffer());
	}

	private void updateSubscription(Subscription subscription, SubscriptionDTO dto) {
		subscription.setDateOfSubscription(dto.getDateOfSubscription());
		subscription.setUser(registeredUserRepository.findById(dto.getRegisteredUserId()).get());

		subscription.setCulturalOffer(culturalOfferRepository.findById(dto.getCulturalOfferId()).get());
		subscription.setSubcategory(subcategoryRepository.findById(dto.getCulturalOfferId()).get());
	}
}
