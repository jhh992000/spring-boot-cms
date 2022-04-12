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
@DiscriminatorValue("C")
public class ContentMenu extends Menu {

    private Long contentId;

    @Builder
    public ContentMenu(Long id, Site site, String name, Menu parent, Long listOrder, String description, MenuOpenType openType, Boolean hide, Boolean enable, List<Menu> children, Long contentId) {
        super(id, site, name, parent, listOrder, description, openType, hide, enable, children);
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
