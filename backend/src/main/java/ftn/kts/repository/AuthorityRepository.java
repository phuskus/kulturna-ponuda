package ftn.kts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ftn.kts.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

}
