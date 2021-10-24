package me.hhjeong.springbootcms.site.domain;

import java.util.List;

public interface SiteRepositoryCustom {

    List<Site> findByName(String name);
}
