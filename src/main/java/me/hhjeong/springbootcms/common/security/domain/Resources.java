package me.hhjeong.springbootcms.common.security.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Resources {

    @Id
    @GeneratedValue
    @Column(name = "resource_id")
    private Long id;

    @Column(name = "resource_name")
    private String resourceName;

    @Column(name = "http_method")
    private String httpMethod;

    @Column(name = "order_num")
    private int orderNum;

    @Column(name = "resource_type")
    private String resourceType;

    @OneToMany(mappedBy = "resources", cascade = CascadeType.ALL)
    private Set<RoleResources> roleResources = new HashSet<>();

    protected Resources() {
    }

    public Resources(Long id, String resourceName, String httpMethod, int orderNum, String resourceType, Set<RoleResources> roleResources) {
        this.id = id;
        this.resourceName = resourceName;
        this.httpMethod = httpMethod;
        this.orderNum = orderNum;
        this.resourceType = resourceType;
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

    public Set<RoleResources> getRoleResources() {
        return roleResources;
    }
}
