package rip.deadcode.abukuma3.gson.internal;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.function.Predicate;

public final class GsonUtils {

    static boolean isAnnotatedBy( Class<?> cls, Class<? extends Annotation> annotation ) {
        return any( cls.getAnnotations(), a -> Objects.equals( a.annotationType(), annotation ) )
               || ( !cls.getSuperclass().equals( Object.class ) && isAnnotatedBy( cls.getSuperclass(), annotation ) );
    }

    static <T> boolean any( T[] array, Predicate<T> predicate ) {
        for ( T each : array ) {
            if ( predicate.test( each ) ) {
                return true;
            }
        }

        return false;
    }
}
