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
@DiscriminatorValue("B")
public class BoardMenu extends Menu {

    private Long boardId;

    @Builder
    public BoardMenu(Long id, Site site, String name, Menu parent, Long listOrder, String description, MenuOpenType openType, Boolean hide, Boolean enable, List<Menu> children, Long boardId) {
        super(id, site, name, parent, listOrder, description, openType, hide, enable, children);
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
