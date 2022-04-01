package rip.deadcode.abukuma3.value.internal;


import org.junit.jupiter.api.Test;
import rip.deadcode.abukuma3.collection.PersistentCollections;
import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.utils.url.internal.ResponseImpl;
import rip.deadcode.abukuma3.value.Cookie;
import rip.deadcode.abukuma3.value.Header;
import rip.deadcode.abukuma3.value.Response;

import static com.google.common.truth.Truth.assertThat;
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
}
