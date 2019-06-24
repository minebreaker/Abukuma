package rip.deadcode.abukuma3.collection;

import com.google.common.collect.ForwardingMap;
import org.organicdesign.fp.collections.ImMap;
import org.organicdesign.fp.collections.MutableMap;
import org.organicdesign.fp.collections.PersistentHashMap;

import java.util.Map;
import java.util.Optional;


public abstract class AbstractPersistentMap<K, V, T extends AbstractPersistentMap<K, V, T>>
        extends ForwardingMap<K, V> implements PersistentMap<K, V, T> {

    private final ImMap<K, V> delegate;

    protected AbstractPersistentMap() {
        this.delegate = PersistentHashMap.empty();
    }

    protected AbstractPersistentMap( Envelope<K, V> delegate ) {
        this.delegate = delegate.load;
    }

    @Override
    protected final Map<K, V> delegate() {
        return delegate;
    }

    protected static final class Envelope<K, V> {
        private final ImMap<K, V> load;

        private Envelope( ImMap<K, V> load ) {
            this.load = load;
        }
    }

    protected abstract T constructor( Envelope<K, V> delegate );

    private T constructor( ImMap<K, V> delegate ) {
        return constructor( new Envelope<>( delegate ) );
    }

    @Override
    public Optional<V> mayGet( K key ) {
        return Optional.ofNullable( delegate.get( key ) );
    }

    @Override
    public T set( K key, V value ) {
        return constructor( delegate.assoc( key, value ) );
    }

    @Override
    public T delete( K key ) {
        return constructor( delegate.without( key ) );
    }

    @Override
    public T merge( Map<K, V> other ) {
        MutableMap<K, V> temp = delegate.mutable();
        for ( Entry<K, V> e : other.entrySet() ) {
            temp.assoc( e );
        }
        return constructor( temp.immutable() );
    }
}
