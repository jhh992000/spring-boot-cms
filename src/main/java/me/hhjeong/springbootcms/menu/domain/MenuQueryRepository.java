package me.hhjeong.springbootcms.menu.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MenuQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Menu> findMenus(Long siteId) {
        QMenu parent = new QMenu("parent");
        QMenu child = new QMenu("child");
        return queryFactory
                .selectFrom(parent).distinct()
                .leftJoin(parent.children, child).fetchJoin()
                .where(
                        parent.site.id.eq(siteId)
                                .and(parent.parent.isNull())
                )
                .orderBy(parent.listOrder.asc(), child.listOrder.asc())
                .fetch();
    }

}
