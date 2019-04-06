package rip.deadcode.abukuma3.parser;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import rip.deadcode.abukuma3.collection.AbstractPersistentListMultimap;


public final class UrlEncoded extends AbstractPersistentListMultimap<String, String, UrlEncoded> {

    private UrlEncoded( Multimap<String, String> delegate ) {
        super( delegate );
    }

    private UrlEncoded( Envelope<String, String> delegate ) {
        super( delegate );
    }

    public static UrlEncoded create( ListMultimap<String, String> delegate ) {
        return new UrlEncoded( delegate );
    }

    @Override public UrlEncoded constructor( Envelope<String, String> delegate ) {
        return new UrlEncoded( delegate );
    }
}
