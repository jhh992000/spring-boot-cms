package me.hhjeong.springbootcms.site.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SiteRepository extends JpaRepository<Site, Long> {

    Optional<Site> findFirstByOrderByIdDesc();

    @Query("SELECT a FROM Site a WHERE a.id >= :id")
    List<Site> findLatest(@Param("id") Long id, Pageable pageable);
}
