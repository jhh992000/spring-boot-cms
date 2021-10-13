package me.hhjeong.springbootcms.account.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AccountIdValidator.class)
public @interface AccountId {

    String message() default "아이디가 유효하지 않습니다.";
}
