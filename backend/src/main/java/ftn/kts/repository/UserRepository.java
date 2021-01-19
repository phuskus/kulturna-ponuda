package ftn.kts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ftn.kts.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User findByKey(String key);
    //User findByRkey(String rkey);

}
