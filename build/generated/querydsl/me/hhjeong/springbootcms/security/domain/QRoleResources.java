package me.hhjeong.springbootcms.security.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoleResources is a Querydsl query type for RoleResources
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRoleResources extends EntityPathBase<RoleResources> {

    private static final long serialVersionUID = 2072012811L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoleResources roleResources = new QRoleResources("roleResources");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QResources resources;

    public final QRole role;

    public QRoleResources(String variable) {
        this(RoleResources.class, forVariable(variable), INITS);
    }

    public QRoleResources(Path<? extends RoleResources> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoleResources(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoleResources(PathMetadata metadata, PathInits inits) {
        this(RoleResources.class, metadata, inits);
    }

    public QRoleResources(Class<? extends RoleResources> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.resources = inits.isInitialized("resources") ? new QResources(forProperty("resources")) : null;
        this.role = inits.isInitialized("role") ? new QRole(forProperty("role")) : null;
    }

}

