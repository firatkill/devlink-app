package com.worksOnLocal.DevLink.repository;

import com.worksOnLocal.DevLink.entity.User;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(@Length(min = 3,max=24) String username);

    boolean existsByEmail(@Email String email);

    Optional<User> findByEmail(@Email String email);
}
