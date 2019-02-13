package rip.deadcode.abukuma3.internal;

import com.google.common.collect.Multimap;


public final class AbuMultimap<K, V> extends AbuAbstractMultimap<K, V, AbuMultimap<K, V>> {

    private final Multimap<K, V> delegate;

    public AbuMultimap( Multimap<K, V> delegate ) {
        this.delegate = delegate;
    }

    @Override public AbuMultimap<K, V> constructor( Multimap<K, V> delegate ) {
        return null;
    }

    @Override protected Multimap<K, V> delegate() {
        return delegate;
    }
}
