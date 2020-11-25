package ftn.kts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ftn.kts.model.Picture;

public interface PictureRepository extends JpaRepository<Picture, Long> {

}
