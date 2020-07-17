package rip.deadcode.abukuma3.value.internal;


import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;
import rip.deadcode.abukuma3.collection.PersistentCollections;
import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.value.Cookie;
import rip.deadcode.abukuma3.value.Header;
import rip.deadcode.abukuma3.value.Response;

import java.util.Map;
import java.util.NoSuchElementException;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth8.assertThat;
import static java.util.stream.Collectors.toMap;
import static rip.deadcode.izvestia.Core.expect;


class ResponseImplTest {

    private static final String body = "test";
    private static final int status = 200;
    private static final Header header = Header.create();
    private static final PersistentList<Cookie> cookie = PersistentCollections.createList();
    private static final Response param = new ResponseImpl(
            body, status, header, cookie );

    @Test
    void test() {
        assertThat( param.body() ).isSameInstanceAs( body );
        assertThat( param.status() ).isEqualTo( status );
        assertThat( param.header() ).isSameInstanceAs( header );
        assertThat( param.cookie() ).isSameInstanceAs( cookie );

        expect( () -> {
            new ResponseImpl( null, status, header, cookie );
        } ).throwsException( NullPointerException.class );
        expect( () -> {
            new ResponseImpl( body, status, null, cookie );
        } ).throwsException( NullPointerException.class );
        expect( () -> {
            new ResponseImpl( body, status, header, null );
        } ).throwsException( NullPointerException.class );


        assertThat( param.body( "replaced" ).body() ).isEqualTo( "replaced" );
        assertThat( param.status( 400 ).status() ).isEqualTo( 400 );
//        assertThat( param.header() ).isSameInstanceAs( header );
//        assertThat( param.cookie() ).isSameInstanceAs( cookie );

        expect( () -> {
            //noinspection ConstantConditions
            param.body( null );
        } ).throwsException( NullPointerException.class );
        expect( () -> {
            //noinspection ConstantConditions
            param.header( (Header) null );
        } ).throwsException( NullPointerException.class );
        expect( () -> {
            //noinspection ConstantConditions
            param.cookie( (PersistentList<Cookie>) null );
        } ).throwsException( NullPointerException.class );
    }

    @Test
    void testSize() {
        assertThat( param.size() ).isEqualTo( 4 );
    }

    @Test
    void testIsEmpty() {
        assertThat( param.isEmpty() ).isFalse();
    }

    @Test
    void testContainsKey() {
        assertThat( param.containsKey( "body" ) ).isTrue();
        assertThat( param.containsKey( "status" ) ).isTrue();
        assertThat( param.containsKey( "header" ) ).isTrue();
        assertThat( param.containsKey( "cookie" ) ).isTrue();

        assertThat( param.containsKey( "" ) ).isFalse();
        assertThat( param.containsKey( null ) ).isFalse();
    }

    @Test
    void testContainsValue() {
        assertThat( param.containsValue( body ) ).isTrue();
        assertThat( param.containsValue( status ) ).isTrue();
        assertThat( param.containsValue( header ) ).isTrue();
        assertThat( param.containsValue( cookie ) ).isTrue();

        assertThat( param.containsValue( "" ) ).isFalse();
        assertThat( param.containsValue( null ) ).isFalse();
    }

    @Test
    void testGet() {
        assertThat( param.get( "body" ) ).isSameInstanceAs( body );
        assertThat( param.get( "status" ) ).isEqualTo( status );
        assertThat( param.get( "header" ) ).isSameInstanceAs( header );
        assertThat( param.get( "cookie" ) ).isSameInstanceAs( cookie );

        assertThat( param.get( "" ) ).isNull();
        assertThat( param.get( null ) ).isNull();
    }

    @Test
    void testMutableOperations() {
        expect( () -> {
            param.put( null, null );
        } ).throwsException( UnsupportedOperationException.class );
        expect( () -> {
            param.remove( null );
        } ).throwsException( UnsupportedOperationException.class );
        expect( () -> {
            //noinspection ConstantConditions
            param.putAll( null );
        } ).throwsException( UnsupportedOperationException.class );
        expect( () -> {
            param.clear();
        } ).throwsException( UnsupportedOperationException.class );
    }

    @Test
    void testKeySet() {
        assertThat( param.keySet() ).containsExactly( "body", "status", "header", "cookie" );
    }

    @Test
    void testValues() {
        assertThat( param.values() ).containsExactly( body, status, header, cookie );
    }

    @Test
    void testEntrySet() {

        Map<String, Object> other = ImmutableMap.of(
                "body", body, "status", status, "header", header, "cookie", cookie );
        assertThat( param.entrySet().stream().collect( toMap( Map.Entry::getKey, Map.Entry::getValue ) ) )
                .isEqualTo( other );
    }

    @Test
    void testEqualsAndHashCode() {

        Map<String, Object> other = ImmutableMap.of(
                "body", body, "status", status, "header", header, "cookie", cookie );

        assertThat( param ).isEqualTo( other );
        assertThat( other ).isEqualTo( param );
        assertThat( param ).isEqualTo( param );
        assertThat( param ).isNotEqualTo( ImmutableMap.of() );
        assertThat( param.hashCode() ).isEqualTo( other.hashCode() );
    }

    @Test
    void testMayGet() {
        assertThat( param.mayGet( "body" ) ).hasValue( body );
        assertThat( param.mayGet( "status" ) ).hasValue( status );
        assertThat( param.mayGet( "header" ) ).hasValue( header );
        assertThat( param.mayGet( "cookie" ) ).hasValue( cookie );

        assertThat( param.mayGet( "" ) ).isEmpty();
        assertThat( param.mayGet( null ) ).isEmpty();
    }

    @Test
    void testSet() {
        assertThat( param.set( "body", "replaced" ) )
                .isEqualTo( ImmutableMap.of(
                        "body", "replaced", "status", status, "header", header, "cookie", cookie ) );
        assertThat( param.set( "key", "value" ) )
                .isEqualTo( ImmutableMap.of(
                        "body", body, "status", status, "header", header, "cookie", cookie, "key", "value" ) );

        expect( () -> {
            param.set( null, "value" );
        } ).throwsException( NullPointerException.class );
//        expect( () -> {
//            param.set( "key", null );
//        } ).throwsException( NullPointerException.class );
    }

    @Test
    void testDelete() {
        assertThat( param.delete( "body" ) )
                .isEqualTo( ImmutableMap.of( "status", status, "header", header, "cookie", cookie ) );

        expect( () -> {
            param.delete( "" );
        } ).throwsException( NoSuchElementException.class );
//        expect( () -> {
//            param.delete( null );
//        } ).throwsException( NullPointerException.class );
    }

    @Test
    void testMerge() {
        assertThat( param.merge( ImmutableMap.of( "body", "replaced", "key", "value" ) ) )
                .isEqualTo( ImmutableMap.of(
                        "body", "replaced", "status", status, "header", header, "cookie", cookie, "key", "value" ) );
    }
}
