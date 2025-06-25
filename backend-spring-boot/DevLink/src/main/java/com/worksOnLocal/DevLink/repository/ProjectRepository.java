package com.worksOnLocal.DevLink.repository;

import com.worksOnLocal.DevLink.entity.Project;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByIdAndProfile_User_Id(@NotBlank Long id, Long id1);


    @Modifying
    @Query("DELETE from Project p WHERE p.id=:id")
    void deleteById(@Param("id") Long id);
}
