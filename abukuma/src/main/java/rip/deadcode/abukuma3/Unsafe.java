package rip.deadcode.abukuma3;

import java.lang.annotation.*;

/**
 * This annotation shows the api is unstable and you should not usually use it.
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
public @interface Unsafe {}
