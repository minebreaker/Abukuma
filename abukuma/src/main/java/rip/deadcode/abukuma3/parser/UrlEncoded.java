package rip.deadcode.abukuma3.parser;

import com.google.common.collect.Multimap;
import rip.deadcode.abukuma3.internal.AbuAbstractMultimap;


public final class UrlEncoded extends AbuAbstractMultimap<String, String, UrlEncoded> {

    private final Multimap<String, String> delegate;

    private UrlEncoded( Multimap<String, String> delegate ) {
        this.delegate = delegate;
    }

    public static UrlEncoded create( Multimap<String, String> delegate ) {
        return new UrlEncoded( delegate );
    }

    @Override public UrlEncoded constructor( Multimap<String, String> delegate ) {
        return new UrlEncoded( delegate );
    }

    @Override protected Multimap<String, String> delegate() {
        return delegate;
    }
}
