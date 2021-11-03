package me.hhjeong.springbootcms.menu.domain;

import static me.hhjeong.springbootcms.menu.domain.QMenu.menu;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MenuQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Menu> findMenus(Long siteId) {
        return queryFactory
            .selectFrom(menu)
            .where(
                menu.site.id.eq(siteId)
                    .and(menu.parent.isNull())
            )
            .fetch();
    }

}
