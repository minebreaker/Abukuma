package rip.deadcode.abukuma3;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This annotation shows the api is mutable and thus should not be shared between threads.
 */
@Retention( RetentionPolicy.CLASS )
@Target( {
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.FIELD,
        ElementType.METHOD,
        ElementType.TYPE
} )
@Documented
public @interface Mutable {
}
