package ftn.kts.repository;

import ftn.kts.model.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

	Page<Post> findByCulturalOfferId(long offerId, Pageable paging);

}
