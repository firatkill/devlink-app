package com.worksOnLocal.DevLink.repository;

import com.worksOnLocal.DevLink.entity.ExternalLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalLinkRepository extends JpaRepository<ExternalLink,Long> {
}
