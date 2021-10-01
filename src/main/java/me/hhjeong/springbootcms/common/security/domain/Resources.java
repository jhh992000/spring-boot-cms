package me.hhjeong.springbootcms.common.security.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
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
public class Resources {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resource_id")
    private Long id;

    @Column(name = "resource_name", nullable = false)
    private String resourceName;

    @Column(name = "http_method", nullable = false)
    private String httpMethod;

    @Column(name = "order_num", nullable = false)
    private int orderNum;

    @Column(name = "resource_type", nullable = false)
    private String resourceType;

    @CreatedDate
    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @OneToMany(mappedBy = "resources", cascade = CascadeType.ALL)
    private Set<RoleResources> roleResources = new HashSet<>();

    protected Resources() {
    }

    public Resources(Long id, String resourceName, String httpMethod, int orderNum, String resourceType, LocalDateTime createTime,
        Set<RoleResources> roleResources) {
        this.id = id;
        this.resourceName = resourceName;
        this.httpMethod = httpMethod;
        this.orderNum = orderNum;
        this.resourceType = resourceType;
        this.createTime = createTime;
        this.roleResources = roleResources;
    }

    public Long getId() {
        return id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public String getResourceType() {
        return resourceType;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public Set<RoleResources> getRoleResources() {
        return roleResources;
    }
}
