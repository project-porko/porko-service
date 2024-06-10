package io.porko.pagination.request;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface Pagination {
    int size() default 10;

    int page() default 1;

    String[] sort() default {};

    Sort.Direction direction() default Direction.ASC;
}
