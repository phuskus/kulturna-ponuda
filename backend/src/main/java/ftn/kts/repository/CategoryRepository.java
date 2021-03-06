package ftn.kts.repository;

import ftn.kts.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository  extends JpaRepository<Category, Long> {
	Category findByNameIgnoringCase(String name);
}
