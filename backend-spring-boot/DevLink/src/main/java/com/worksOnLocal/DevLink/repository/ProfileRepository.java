package com.worksOnLocal.DevLink.repository;

import com.worksOnLocal.DevLink.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProfileRepository extends JpaRepository<Profile,Long> {
    Optional<Profile> findByUser_Id(Long userId);

    Optional<Profile> findByUser_Username(String userUsername);
}
