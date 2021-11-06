package me.hhjeong.springbootcms.security.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import me.hhjeong.springbootcms.common.domain.BaseEntity;

@Entity
@Getter
public class Resources extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resource_name", nullable = false)
    private String resourceName;

    @Column(name = "http_method", nullable = false)
    private String httpMethod;

    @Column(name = "order_num", nullable = false)
    private int orderNum;

    @Column(name = "resource_type", nullable = false)
    private String resourceType;

    @OneToMany(mappedBy = "resources", cascade = CascadeType.ALL)
    private Set<RoleResources> roleResources = new HashSet<>();

    protected Resources() {
    }

    public Resources(Long id) {
        this.id = id;
    }

    public Resources(String resourceName, String httpMethod, int orderNum, String resourceType) {
        this(null, resourceName, httpMethod, orderNum, resourceType);
    }

    public Resources(Long id, String resourceName, String httpMethod, int orderNum, String resourceType) {
        this.id = id;
        this.resourceName = resourceName;
        this.httpMethod = httpMethod;
        this.orderNum = orderNum;
        this.resourceType = resourceType;
    }

    public void update(Resources resources) {
        this.resourceName = resources.getResourceName();
        this.httpMethod = resources.getHttpMethod();
        this.orderNum = resources.getOrderNum();
        this.resourceType = resources.getResourceType();
    }
}
