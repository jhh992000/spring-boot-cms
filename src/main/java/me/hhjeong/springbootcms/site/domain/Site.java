package me.hhjeong.springbootcms.site.domain;

import lombok.*;
import me.hhjeong.springbootcms.common.domain.BaseEntity;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Site site = (Site) o;
        return Objects.equals(id, site.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void update(Site site) {
        this.name = site.name;
        this.enabled = site.enabled;
        this.alias = site.alias;
        this.useLoginLock = site.useLoginLock;
        this.countOfLoginFail = site.countOfLoginFail;
    }
}
