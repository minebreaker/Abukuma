package rip.deadcode.abukuma3.router.internal;

import org.junit.jupiter.api.Test;
import rip.deadcode.abukuma3.handler.Handler;
import rip.deadcode.abukuma3.router.Router;
import rip.deadcode.abukuma3.router.RoutingContext;
import rip.deadcode.abukuma3.router.RoutingResult;
import rip.deadcode.abukuma3.router.StandardRouters;
import rip.deadcode.abukuma3.value.Responses;
import rip.deadcode.abukuma3.value.internal.RequestHeaderImpl;

import static com.google.common.truth.Truth.assertThat;
import static rip.deadcode.abukuma3.collection.PersistentCollections.createList;


class StandardRouterImplTest {

    @Test
    public void testPath() {

        Handler h = ( c, req ) -> Responses.create( "OK" );
        Router r = StandardRouters.path( "GET", "/foo/bar", h );
        RoutingContext ctx = new RoutingContextImpl( new RequestHeaderImpl(
                null, "GET", null,
                createList( "foo", "bar" )
        ), null );
        RoutingResult result = r.route( ctx );

        assertThat( result.handler() ).isSameInstanceAs( h );
    }

    @Test
    public void testPath2() {

        Handler h = ( c, req ) -> Responses.create( "OK" );
        Router r = StandardRouters.path( "GET", "/foo/bar", h );
        RoutingContext ctx = new RoutingContextImpl( new RequestHeaderImpl(
                null, "GET", null,
                createList( "foo" )
        ), null );
        RoutingResult result = r.route( ctx );

        assertThat( result ).isNull();
    }

    @Test
    public void testPath3() {

        Handler h = ( c, req ) -> Responses.create( "OK" );
        Router r = StandardRouters.path( "GET", "/foo/bar", h );
        RoutingContext ctx = new RoutingContextImpl( new RequestHeaderImpl(
                null, "GET", null,
                createList( "foo", "bar", "buz" )
        ), null );
        RoutingResult result = r.route( ctx );

        assertThat( result ).isNull();
    }
}
