package me.hhjeong.springbootcms.security.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResourcesRepository extends JpaRepository<Resources, Long> {

    Optional<Resources> findFirstByOrderByIdDesc();

    @Query("SELECT a FROM Resources a WHERE a.id >= :id")
    List<Resources> findLatest(@Param("id") Long id, Pageable pageable);
}
