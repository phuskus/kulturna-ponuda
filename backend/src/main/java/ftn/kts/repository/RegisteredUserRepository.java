package ftn.kts.repository;

import ftn.kts.model.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Long> {
    @Query("SELECT u FROM RegisteredUser u WHERE u.username = ?1")
    public RegisteredUser findByUsername(String username);
}
