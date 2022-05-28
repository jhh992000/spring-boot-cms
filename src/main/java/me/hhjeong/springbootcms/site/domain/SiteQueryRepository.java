package me.hhjeong.springbootcms.site.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static me.hhjeong.springbootcms.site.domain.QSite.site;

@RequiredArgsConstructor
@Repository
public class SiteQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Site> findByName(String name) {
        return queryFactory
                .selectFrom(site)
                .where(site.name.eq(name))
                .fetch();
    }

}
