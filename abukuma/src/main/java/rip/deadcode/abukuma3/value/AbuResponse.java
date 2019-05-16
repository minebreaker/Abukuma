package rip.deadcode.abukuma3.value;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;


public interface AbuResponse {

    /**
     * This method can be called multiple times. Must be idempotent i.e. must return same object every time.
     */
    public Object body();

    public AbuResponse body( Object body );

    public int status();

    public AbuResponse status( int status );

    public AbuHeader header();

    public AbuResponse header( AbuHeader header );

    public AbuResponse header( Function<AbuHeader, AbuHeader> header );

    public List<Cookie> cookie();

    public Optional<Cookie> cookie( String key );

    public AbuResponse addCookie( Cookie cookie );

    public AbuResponse addCookie( Cookie... cookie );
}
