package rip.deadcode.abukuma3.router.internal;

import org.junit.jupiter.api.Test;
import rip.deadcode.abukuma3.router.Router;
import rip.deadcode.abukuma3.router.RoutingContext;
import rip.deadcode.abukuma3.router.RoutingResult;
import rip.deadcode.abukuma3.router.StandardRouters;
import rip.deadcode.abukuma3.value.Responses;
import rip.deadcode.abukuma3.value.internal.RequestHeaderImpl;

import java.net.URI;

import static com.google.common.truth.Truth.assertThat;
import static rip.deadcode.abukuma3.collection.PersistentCollections.createList;
import static rip.deadcode.abukuma3.handler.Handlers.createHandler;
import static rip.deadcode.abukuma3.internal.utils.Uncheck.uncheck;


class StandardRouterImplTest {

    @Test
    public void testPath() {

        // Path matches
        var h = createHandler( ( c, req ) -> Responses.create( "OK" ) );
        Router r = StandardRouters.path( "GET", "/foo/bar", h );
        RoutingContext ctx = new RoutingContextImpl( new RequestHeaderImpl(
                null, "GET", null,
                createList( "foo", "bar" )
        ), null );
        RoutingResult result = r.route( ctx );

        assertThat( result.handler() ).isSameInstanceAs( h );


        // Path matches to root
        h = createHandler( ( c, req ) -> Responses.create( "OK" ) );
        r = StandardRouters.path( "GET", "/", h );
        ctx = new RoutingContextImpl( new RequestHeaderImpl(
                null, "GET", null,
                createList( "" )
        ), null );
        result = r.route( ctx );

        assertThat( result.handler() ).isSameInstanceAs( h );


        // Path matches with empty parts
        h = createHandler( ( c, req ) -> Responses.create( "OK" ) );
        r = StandardRouters.path( "GET", "/foo///bar/", h );
        ctx = new RoutingContextImpl( new RequestHeaderImpl(
                null, "GET", null,
                createList( "foo", "", "", "bar", "" )
        ), null );
        result = r.route( ctx );

        assertThat( result.handler() ).isSameInstanceAs( h );


        // Path matches to asterisk
        h = createHandler( ( c, req ) -> Responses.create( "OK" ) );
        r = StandardRouters.path( "OPTIONS", "*", h );
        ctx = new RoutingContextImpl( new RequestHeaderImpl(
                null, "OPTIONS", uncheck( () -> new URI( "*" ) ),
                createList( "*" )
        ), null );
        result = r.route( ctx );

        assertThat( result.handler() ).isSameInstanceAs( h );


        // Request URL is too short
        h = createHandler( ( c, req ) -> Responses.create( "OK" ) );
        r = StandardRouters.path( "GET", "/foo/bar", h );
        ctx = new RoutingContextImpl( new RequestHeaderImpl(
                null, "GET", null,
                createList( "foo" )
        ), null );
        result = r.route( ctx );

        assertThat( result ).isNull();


        // Request URL is same size but not matching
        h = createHandler( ( c, req ) -> Responses.create( "OK" ) );
        r = StandardRouters.path( "GET", "/foo/bar", h );
        ctx = new RoutingContextImpl( new RequestHeaderImpl(
                null, "GET", null,
                createList( "foo", "bar", "buz" )
        ), null );
        result = r.route( ctx );

        assertThat( result ).isNull();
    }
}
