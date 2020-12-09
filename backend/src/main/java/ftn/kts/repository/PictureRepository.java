package ftn.kts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ftn.kts.model.Picture;

public interface PictureRepository extends JpaRepository<Picture, Long> {
	@Query(value = "SELECT nextval('pictures_id_seq')", nativeQuery =
            true)
    Long getNextSeriesId();
}
