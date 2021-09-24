package me.hhjeong.springbootcms.common.security.domain;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_desc")
    private String roleDesc;

    @CreatedDate
    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<RoleResources> roleResources = new LinkedHashSet<>();

    protected Role() {
    }

    public Role(Long id, String roleName, String roleDesc, LocalDateTime createTime, Set<RoleResources> roleResources) {
        this.id = id;
        this.roleName = roleName;
        this.roleDesc = roleDesc;
        this.createTime = createTime;
        this.roleResources = roleResources;
    }

    public Long getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public Set<RoleResources> getRoleResources() {
        return roleResources;
    }
}
