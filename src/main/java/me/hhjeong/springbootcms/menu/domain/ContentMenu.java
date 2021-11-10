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
@DiscriminatorValue("C")
public class ContentMenu extends Menu {

    private Long contentId;

    public ContentMenu(Long contentId, Menu menu) {
        builder()
            .site(menu.getSite())
            .parent(menu.getParent())
            .listOrder(menu.getListOrder())
            .description(menu.getDescription())
            .openType(menu.getOpenType())
            .hide(menu.getHide())
            .enable(menu.getEnable())
            .build();

        this.contentId = contentId;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        ContentMenu contentMenu = (ContentMenu) o;
        return Objects.equals(contentId, contentMenu.contentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), contentId);
    }
}
