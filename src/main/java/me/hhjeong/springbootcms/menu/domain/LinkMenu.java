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
@DiscriminatorValue("L")
public class LinkMenu extends Menu {

    private LinkType linkType;
    private String url;

    public LinkMenu(LinkType linkType, String url, Menu menu) {
        super(menu.getSite(), menu.getParent(), menu.getListOrder(), menu.getName(), menu.getDescription(), menu.getOpenType(), menu.getHide(), menu.getEnable());
        this.linkType = linkType;
        this.url = url;
    }
}
