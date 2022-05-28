package me.hhjeong.springbootcms.security.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleResourcesRepository extends JpaRepository<RoleResources, Long> {

    Optional<RoleResources> findFirstByOrderByIdDesc();

    @Query("SELECT a FROM RoleResources a WHERE a.id >= :id")
    List<RoleResources> findLatest(@Param("id") Long id, Pageable pageable);
}
