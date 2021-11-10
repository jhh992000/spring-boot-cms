package me.hhjeong.springbootcms.menu.domain;

import java.util.Objects;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("L")
public class LinkMenu extends Menu {

    private LinkType linkType;
    private String url;

    public LinkMenu(LinkType linkType, String url, Menu menu) {
        builder()
            .site(menu.getSite())
            .parent(menu.getParent())
            .listOrder(menu.getListOrder())
            .description(menu.getDescription())
            .openType(menu.getOpenType())
            .hide(menu.getHide())
            .enable(menu.getEnable())
            .build();

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
