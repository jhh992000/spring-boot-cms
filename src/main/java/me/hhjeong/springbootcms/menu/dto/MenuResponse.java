package me.hhjeong.springbootcms.menu.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.hhjeong.springbootcms.menu.domain.Menu;
import me.hhjeong.springbootcms.menu.domain.MenuOpenType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuResponse {

    private Long id;

    private Long siteId;

    private Long parentId;

    private Long listOrder;

    private String name;

    private String description;

    private MenuOpenType openType;

    private Boolean hide;

    private Boolean enable;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdDatetime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime modifiedDatetime;

    private List<MenuResponse> children;

    public static MenuResponse of(Menu menu) {
        return MenuResponse.builder()
                .id(menu.getId())
                .siteId(menu.getSite().getId())
                .parentId(menu.getParentId())
                .listOrder(menu.getListOrder())
                .name(menu.getName())
                .description(menu.getDescription())
                .openType(menu.getOpenType())
                .hide(menu.getHide())
                .enable(menu.getEnable())
                .createdDatetime(menu.getCreatedDatetime())
                .modifiedDatetime(menu.getModifiedDatetime())
                .children(findChildrens(menu))
                .build();
    }

    private static List<MenuResponse> findChildrens(Menu menu) {
        List<Menu> children = menu.getChildren();
        if (children == null) {
            return new ArrayList<>();
        }
        return children.stream()
                .map(MenuResponse::of)
                .collect(Collectors.toList());
    }

}
