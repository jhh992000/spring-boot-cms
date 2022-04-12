package me.hhjeong.springbootcms.menu.domain;

import lombok.*;
import me.hhjeong.springbootcms.site.domain.Site;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("L")
public class LinkMenu extends Menu {

    private LinkType linkType;
    private String url;

    @Builder
    public LinkMenu(Long id, Site site, String name, Menu parent, Long listOrder, String description, MenuOpenType openType, Boolean hide, Boolean enable, List<Menu> children, LinkType linkType, String url) {
        super(id, site, name, parent, listOrder, description, openType, hide, enable, children);
        this.linkType = linkType;
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        LinkMenu linkMenu = (LinkMenu) o;
        return Objects.equals(linkType, linkMenu.linkType) && Objects.equals(url, linkMenu.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), linkType, url);
    }

}
