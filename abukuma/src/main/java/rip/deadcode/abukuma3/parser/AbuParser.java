package rip.deadcode.abukuma3.parser;

import rip.deadcode.abukuma3.value.AbuRequestHeader;

import java.io.InputStream;

@FunctionalInterface
public interface AbuParser<T> {
    public T parse( InputStream body, AbuRequestHeader header );
}
