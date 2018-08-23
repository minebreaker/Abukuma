package rip.deadcode.abukuma3;

import java.io.InputStream;

@FunctionalInterface
public interface AbuParser<T> {
    public T parse( InputStream body, AbuRequestHeader header );
}
