package me.hhjeong.springbootcms.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.hhjeong.springbootcms.menu.domain.Menu;
import me.hhjeong.springbootcms.menu.domain.MenuOpenType;
import me.hhjeong.springbootcms.site.domain.Site;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateMenuRequest {

    @NotNull
    private Long siteId;

    private Long parentId;

    @NotNull
    private Long listOrder;

    @NotEmpty
    private String name;

    private String description;

    @NotNull
    private MenuOpenType openType;

    @NotNull
    private Boolean hide;

    @NotNull
    private Boolean enable;

    public Menu toMenu() {
        return new Menu(null, new Site(siteId), name, parentId != null ? new Menu(parentId) : null, listOrder, description, openType, hide, enable, null);
    }

}
