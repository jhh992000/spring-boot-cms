package me.hhjeong.springbootcms.account.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsername(String username);

    Optional<Account> findFirstByOrderByIdDesc();

    @Query("SELECT a FROM Account a WHERE a.id >= :id")
    List<Account> findLatest(@Param("id") Long id, Pageable pageable);
}
