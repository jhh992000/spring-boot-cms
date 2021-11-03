package me.hhjeong.springbootcms.menu.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.hhjeong.springbootcms.menu.domain.Menu;
import me.hhjeong.springbootcms.menu.domain.MenuOpenType;
import me.hhjeong.springbootcms.site.domain.Site;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMenuRequest {

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
        return Menu.builder()
            .site(new Site(siteId))
            .parent(new Menu(parentId))
            .listOrder(listOrder)
            .name(name)
            .description(description)
            .openType(openType)
            .hide(hide)
            .enable(enable)
            .build();
    }

    public Menu toMenu(Long id) {
        return Menu.builder()
            .id(id)
            .site(new Site(siteId))
            .parent(new Menu(parentId))
            .listOrder(listOrder)
            .name(name)
            .description(description)
            .openType(openType)
            .hide(hide)
            .enable(enable)
            .build();
    }
}
