package me.hhjeong.springbootcms.site.domain;

import static me.hhjeong.springbootcms.site.domain.QSite.site;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class SiteRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public SiteRepositorySupport(JPAQueryFactory queryFactory) {
        super(Site.class);
        this.queryFactory = queryFactory;
    }

    public List<Site> findByName(String name) {
        return queryFactory
            .selectFrom(site)
            .where(site.name.eq(name))
            .fetch();
    }
}
