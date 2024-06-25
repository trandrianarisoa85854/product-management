package io.ennov.productmanagement.repository;

import io.ennov.productmanagement.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * retrieve user by email
     *
     * @param email
     * @return Optional<User>
     */
    Optional<User> findByEmail(String email);
}
