package ftn.kts.repository;

import ftn.kts.model.CulturalOffer;
import ftn.kts.model.RegisteredUser;
import ftn.kts.model.Subcategory;
import ftn.kts.model.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Page<Subscription> findAll(Pageable pageable);
    List<Subscription> findByUser(RegisteredUser user);
    Page<Subscription> findBySubcategory(Subcategory subcategory, Pageable pageable);
    Page<Subscription> findByCulturalOffer(CulturalOffer culturalOffer, Pageable pageable);

    @Query("SELECT s FROM Subscription s WHERE s.user.id = ?1 and s.culturalOffer.id = ?2")
    Subscription findByUserAndOffer(long userId, long offerId);

    @Query("SELECT s FROM Subscription s WHERE s.user.username = ?1 and s.culturalOffer.id = ?2")
    Subscription findByUsernameAndOffer(String username, long offerId);

    @Query("SELECT s FROM Subscription s WHERE s.user.username = ?1 and LOWER(s.subcategory.name) = LOWER(?2)")
    Subscription findByUsernameAndSubcategory(String username, String subcategoryName);
}
