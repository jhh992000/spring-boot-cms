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
@DiscriminatorValue("P")
public class ProgramMenu extends Menu {

    private Long programId;

    @Builder
    public ProgramMenu(Long id, Site site, String name, Menu parent, Long listOrder, String description, MenuOpenType openType, Boolean hide, Boolean enable, List<Menu> children, Long programId) {
        super(id, site, name, parent, listOrder, description, openType, hide, enable, children);
        this.programId = programId;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        ProgramMenu programMenu = (ProgramMenu) o;
        return Objects.equals(programId, programMenu.programId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), programId);
    }
}
