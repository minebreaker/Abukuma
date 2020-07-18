package rip.deadcode.abukuma3.collection;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;


class AbstractPersistentMapViewTest {

    private static interface TestAbstractPersistentMapView<K, V>
            extends PersistentMapView<K, V, TestAbstractPersistentMapView<K, V>> {

        public TestAbstractPersistentMapView<K, V> uniqueParam( K key, V value );
    }

    private static final class TestAbstractPersistentMapViewImpl<K, V>
            extends AbstractPersistentMapView<K, V, TestAbstractPersistentMapView<K, V>>
            implements TestAbstractPersistentMapView<K, V> {

        private TestAbstractPersistentMapViewImpl() {
            super();
        }

        private TestAbstractPersistentMapViewImpl( Envelope<K, V> delegate ) {
            super( delegate );
        }

        @Override protected TestAbstractPersistentMapViewImpl<K, V> constructor( Envelope<K, V> delegate ) {
            return new TestAbstractPersistentMapViewImpl<>( delegate );
        }

        @Override public TestAbstractPersistentMapView<K, V> uniqueParam( K key, V value ) {
            return set( key, value );
        }
    }

    @Test
    void test() {
        TestAbstractPersistentMapView<String, String> value = new TestAbstractPersistentMapViewImpl<>();
        value = value.set( "a", "1" )
                     .uniqueParam( "c", "3" )
                     .set( "b", "2" )
                     .uniqueParam( "d", "4" );

        assertThat( value ).containsExactly( "a", "1", "b", "2", "c", "3", "d", "4" );
    }
}
