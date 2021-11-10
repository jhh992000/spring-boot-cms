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
@DiscriminatorValue("P")
public class ProgramMenu extends Menu {

    private Long programId;

    public ProgramMenu(Long programId, Menu menu) {
        builder()
            .site(menu.getSite())
            .parent(menu.getParent())
            .listOrder(menu.getListOrder())
            .description(menu.getDescription())
            .openType(menu.getOpenType())
            .hide(menu.getHide())
            .enable(menu.getEnable())
            .build();

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
