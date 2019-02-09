package rip.deadcode.abukuma3.internal;

import com.google.common.collect.Multimap;

public final class AbuMultimap extends AbuAbstractMultimap<AbuMultimap> {

    private final Multimap<String, String> delegate;

    public AbuMultimap( Multimap<String, String> delegate ) {
        this.delegate = delegate;
    }

    @Override public AbuMultimap constructor( Multimap<String, String> delegate ) {
        return new AbuMultimap( delegate );
    }

    @Override protected Multimap<String, String> delegate() {
        return delegate;
    }
}
