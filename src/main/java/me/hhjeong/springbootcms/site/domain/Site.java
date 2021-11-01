package me.hhjeong.springbootcms.site.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.hhjeong.springbootcms.common.domain.BaseEntity;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode
@DynamicUpdate
@Builder
public class Site extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false, unique = true)
    private String alias;

    @Column(nullable = false)
    private boolean useLoginLock;

    @Column(nullable = false)
    private int countOfLoginFail;

    public Site(Long id) {
        this.id = id;
    }

    public void update(Site site) {
        this.name = site.name;
        this.enabled = site.enabled;
        this.alias = site.alias;
        this.useLoginLock = site.useLoginLock;
        this.countOfLoginFail = site.countOfLoginFail;
    }
}
