package me.hhjeong.springbootcms.menu.domain;

import lombok.*;
import me.hhjeong.springbootcms.common.domain.BaseEntity;
import me.hhjeong.springbootcms.site.domain.Site;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
@DynamicUpdate
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_menu_site"), nullable = false)
    @ToString.Exclude
    private Site site;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @ToString.Exclude
    private Menu parent;

    @Column(name = "list_order", nullable = false)
    private Long listOrder;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "open_type", nullable = false)
    private MenuOpenType openType;

    @Column(name = "hide", nullable = false)
    private Boolean hide;

    @Column(name = "enable", nullable = false)
    private Boolean enable;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Menu> children = new ArrayList<>();

    public Menu(Long id) {
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
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void update(Menu menu) {
        this.name = menu.name;
        this.description = menu.description;
        this.openType = menu.openType;
        this.hide = menu.hide;
        this.enable = menu.enable;
    }

    public Long getParentId() {
        if (parent == null) {
            return null;
        }
        return parent.getId();
    }

    public void addChildren(Menu menu) {
        children.add(menu);
        menu.toParent(this);
    }

    private void toParent(Menu menu) {
        parent = menu;
    }
}
