package rip.deadcode.abukuma3;

import java.io.InputStream;

@FunctionalInterface
public interface AParser<T> {
    public T parse( InputStream body, ARequestHeader header );
}
