package me.hhjeong.springbootcms.menu.domain;

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
        super(menu.getSite(), menu.getParent(), menu.getListOrder(), menu.getName(), menu.getDescription(), menu.getOpenType(), menu.getHide(), menu.getEnable());
        this.contentId = contentId;
    }
}
