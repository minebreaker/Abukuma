package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.router.Route;
import rip.deadcode.abukuma3.router.Router;
import rip.deadcode.abukuma3.router.RoutingContext;
import rip.deadcode.abukuma3.router.RoutingResult;

import javax.annotation.Nullable;
import java.util.List;


public final class StandardRouter implements Router {

    private StandardRouter() {

    }

    public static StandardRouter create( List<Route> routes ) {
        return new StandardRouter();
    }

    @Nullable @Override public RoutingResult route( RoutingContext context ) {

//        QueryParameterParser queryParameterParser =
//                checkNotNull( context.executionContext().get( QueryParameterParser.class ) );
//        // FIXME: 直接クエリ―を取得
//        PersistentMultimap<String, String> queryParams =
//                queryParameterParser.parse( context.header().url().getQuery() );
//
//
//
//        return new RoutingResultImpl( ,  queryParams,  );
        throw new RuntimeException();
    }
}


// /abc/def/ghi/jkl/mno
//
// /abc/**
// Springは↓みたいなの認めていない
// /abc/**/:name
// /abc/**/:name/mno






