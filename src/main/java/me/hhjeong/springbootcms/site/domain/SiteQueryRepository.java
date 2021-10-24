package me.hhjeong.springbootcms.site.domain;

import static me.hhjeong.springbootcms.site.domain.QSite.site;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
