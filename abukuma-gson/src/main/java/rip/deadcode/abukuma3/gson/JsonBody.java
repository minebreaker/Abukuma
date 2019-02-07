package rip.deadcode.abukuma3.gson;

import java.lang.annotation.*;


@Retention( RetentionPolicy.RUNTIME )
@Target( {
                 ElementType.ANNOTATION_TYPE,
                 ElementType.CONSTRUCTOR,
                 ElementType.FIELD,
                 ElementType.METHOD,
                 ElementType.TYPE
         } )
@Documented
public @interface JsonBody {
}
