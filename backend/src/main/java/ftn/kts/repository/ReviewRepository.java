package ftn.kts.repository;

import ftn.kts.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository  extends JpaRepository<Review, Long> {

    @Query("SELECT r from Review r " +
            "JOIN r.user u " +
            "JOIN r.culturalOffer o " +
            "WHERE " +
            "lower(r.content) like %?1% " +
            "or lower(u.name || ' ' || u.surname) like %?1% " +
            "or lower(u.username) like %?1% " +
            "or lower(o.name) like %?1%")
    Page<Review> search(String query, Pageable pageable);

    @Query("SELECT r from Review r " +
            "JOIN r.culturalOffer o " +
            "WHERE o.id = ?1")
    Page<Review> findByCulturalOffer(long offerId, Pageable pageable);

}
