package rip.deadcode.abukuma3.collection;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;
import java.util.Optional;


public final class CaseInsensitivePlmm<V> extends ForwardingPersistentListMultimap<
        String, V, CaseInsensitivePlmm<V>, AbuPersistentListMultimap<String, V>> {

    private AbuPersistentListMultimap<String, V> delegate;

    private CaseInsensitivePlmm() {
        this.delegate = AbuPersistentListMultimap.create();
    }

    private CaseInsensitivePlmm( AbuPersistentListMultimap<String, V> delegate ) {
        this.delegate = delegate;
    }

    public static CaseInsensitivePlmm create() {
        return new CaseInsensitivePlmm();
    }

    @Override
    protected PersistentListMultimap<String, V, AbuPersistentListMultimap<String, V>> delegate() {
        return delegate;
    }

    @Nullable
    private static String lowerKey( @Nullable Object key ) {
        return key == null ? null : key.toString().toLowerCase();
    }

    @Override
    public boolean containsEntry( @Nullable Object key, @Nullable Object value ) {
        return super.containsEntry( lowerKey( key ), value );
    }

    @Override
    public boolean containsKey( @Nullable Object key ) {
        return super.containsKey( lowerKey( key ) );
    }

    @Override
    public List<V> get( @Nullable String key ) {
        return super.get( lowerKey( key ) );
    }

    @Override
    protected CaseInsensitivePlmm<V> constructor( AbuPersistentListMultimap<String, V> delegate ) {
        return new CaseInsensitivePlmm<>( delegate );
    }

    @Override
    public V getValue( String key ) {
        return super.getValue( lowerKey( key ) );
    }

    @Override
    public Optional<V> mayGet( String key ) {
        return super.mayGet( lowerKey( key ) );
    }

    @Override
    public CaseInsensitivePlmm<V> add( String key, V value ) {
        return super.add( lowerKey( key ), value );
    }

    @Override
    public CaseInsensitivePlmm<V> add( String key, Iterable<? extends V> values ) {
        return super.add( lowerKey( key ), values );
    }

    @Override
    public CaseInsensitivePlmm<V> set( String key, V value ) {
        return super.set( lowerKey( key ), value );
    }

    @Override
    public CaseInsensitivePlmm<V> set( String key, Iterable<? extends V> values ) {
        return super.set( lowerKey( key ), values );
    }

    @Override
    public CaseInsensitivePlmm<V> delete( String key ) {
        return super.delete( lowerKey( key ) );
    }
}
