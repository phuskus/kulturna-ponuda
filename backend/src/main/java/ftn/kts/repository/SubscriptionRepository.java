package ftn.kts.repository;

import ftn.kts.model.CulturalOffer;
import ftn.kts.model.RegisteredUser;
import ftn.kts.model.Subcategory;
import ftn.kts.model.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Page<Subscription> findAll(Pageable pageable);
    Page<Subscription> findByUser(RegisteredUser user, Pageable pageable);
    Page<Subscription> findBySubcategory(Subcategory subcategory, Pageable pageable);
    Page<Subscription> findByCulturalOffer(CulturalOffer culturalOffer, Pageable pageable);
}
