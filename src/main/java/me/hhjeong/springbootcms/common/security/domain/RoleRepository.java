package me.hhjeong.springbootcms.common.security.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findFirstByOrderByIdDesc();

    @Query("SELECT a FROM Role a WHERE a.id >= :id")
    List<Role> findLatest(@Param("id") Long id, Pageable pageable);
}
