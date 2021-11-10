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
@DiscriminatorValue("B")
public class BoardMenu extends Menu {

    private Long boardId;

    public BoardMenu(Long boardId, Menu menu) {
        builder()
            .site(menu.getSite())
            .parent(menu.getParent())
            .listOrder(menu.getListOrder())
            .description(menu.getDescription())
            .openType(menu.getOpenType())
            .hide(menu.getHide())
            .enable(menu.getEnable())
            .build();

        this.boardId = boardId;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        BoardMenu boardMenu = (BoardMenu) o;
        return Objects.equals(boardId, boardMenu.boardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), boardId);
    }
}
