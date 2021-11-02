package me.hhjeong.springbootcms.common.utils;

import com.google.common.base.CaseFormat;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Service
@ActiveProfiles("test")
public class DatabaseCleanup implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;

    private List<TablePrimaryKey> tablePrimaryKeys;

    @Override
    public void afterPropertiesSet() {
        tablePrimaryKeys = entityManager.getMetamodel().getEntities().stream()
            .filter(e -> e.getJavaType().getAnnotation(Entity.class) != null)
            .map(e -> {
                String pkColumnName = getPKColumnName(e.getJavaType());
                return new TablePrimaryKey(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName()), pkColumnName);
            })
            .collect(Collectors.toList());
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        truncateTables();
        initPrimaryKeys();

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

    private void truncateTables() {
        for (TablePrimaryKey tablePrimaryKey : tablePrimaryKeys) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tablePrimaryKey.getTableName()).executeUpdate();
        }
    }

    private void initPrimaryKeys() {
        tablePrimaryKeys.stream()
            .filter(tablePrimaryKey -> tablePrimaryKey.getPrimaryKey() != null)
            .forEach(tablePrimaryKey -> {
                entityManager.createNativeQuery("ALTER TABLE " + tablePrimaryKey.getTableName() + " ALTER COLUMN " + tablePrimaryKey.getPrimaryKey() + " RESTART WITH 1").executeUpdate();
            });
    }

    private String getPKColumnName(Class<?> pojo) {
        if (pojo == null) {
            return null;
        }
        String name = null;
        for (Field f : pojo.getDeclaredFields()) {
            Id id = null;
            Column column = null;
            Annotation[] as = f.getAnnotations();
            for (Annotation a : as) {
                if (a.annotationType() == Id.class) {
                    id = (Id) a;
                } else if (a.annotationType() == Column.class) {
                    column = (Column) a;
                }
            }
            if (id != null && column != null) {
                name = column.name();
                break;
            }
        }
        if (name == null && pojo.getSuperclass() != Object.class) {
            name = getPKColumnName(pojo.getSuperclass());
        }
        return name;
    }

}