package ftn.kts.repository;

import ftn.kts.model.Subcategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

	Subcategory findByNameIgnoringCase(String name);
	Page<Subcategory> findAll(Pageable pageable);
}
