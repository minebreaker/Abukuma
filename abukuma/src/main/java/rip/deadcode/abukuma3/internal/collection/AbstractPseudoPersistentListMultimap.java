package rip.deadcode.abukuma3.internal.collection;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ForwardingListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.List;


public abstract class AbstractPseudoPersistentListMultimap<K, V, T extends AbstractPseudoPersistentListMultimap<K, V, T>>
        extends ForwardingListMultimap<K, V>
        implements PersistentListMultimap<K, V, T> {

    private final ListMultimap<K, V> delegate;

    protected AbstractPseudoPersistentListMultimap() {
        this.delegate = ArrayListMultimap.create();
    }

    protected AbstractPseudoPersistentListMultimap( ListMultimap<K, V> delegate ) {
        this.delegate = delegate;
    }

    @Override
    protected final ListMultimap<K, V> delegate() {
        return delegate;
    }

    protected abstract T constructor( ListMultimap<K, V> delegate );

    @Override
    public V getValue( K key ) {
        return delegate().get( key ).get( 0 );
    }

    @Override
    public T set( K key, V value ) {
        ListMultimap<K, V> copy = ArrayListMultimap.create( delegate() );
        if ( copy.containsKey( key ) ) {
            copy.removeAll( key );
        }
        copy.put( key, value );
        return constructor( copy );
    }

    @Override
    public T add( K key, V value ) {
        ListMultimap<K, V> copy = ArrayListMultimap.create( delegate() );
        List<V> currentValues = delegate().get( key );
        copy.removeAll( key );
        copy.put( key, value );
        copy.putAll( key, currentValues );
        return constructor( copy );
    }

    @Override
    public T delete( K key ) {
        ListMultimap<K, V> copy = ArrayListMultimap.create( delegate() );
        copy.removeAll( key );
        return constructor( copy );
    }
}
