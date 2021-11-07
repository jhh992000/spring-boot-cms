package me.hhjeong.springbootcms.menu.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleMenuRepository extends JpaRepository<RoleMenu, Long> {

    @Query("SELECT r FROM RoleMenu r WHERE r.role.id = :roleId AND r.site.id = :siteId")
    List<RoleMenu> findByRoleIdAndSiteId(@Param("roleId") Long roleId, @Param("siteId")  Long siteId);

    @Modifying
    @Query("DELETE FROM RoleMenu r WHERE r.role.id = :roleId AND r.site.id = :siteId")
    void deleteByRoleIdAndSiteId(@Param("roleId") Long roleId, @Param("siteId")  Long siteId);

}
