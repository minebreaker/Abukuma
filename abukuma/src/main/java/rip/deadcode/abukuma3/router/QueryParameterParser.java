package rip.deadcode.abukuma3.router;

import rip.deadcode.abukuma3.collection.Tuple2;

import java.util.List;


@FunctionalInterface
public interface QueryParameterParser {

    /**
     * Parse the URL Query into the Map instance.
     * RFC3986 does not specify how query parameters should be parsed,
     * so users can plug-in their preferred implementation through this class.
     *
     * {@value urlQuery} should be the RFC3986 §3.4 Query component.
     * The Query should NOT begin with `?`.
     * Since the Query is HTTP transported, it never ends with `#`
     * since the fragment part should not be sent.
     *
     * The values of the returned map is url-decoded.
     *
     * @param urlQuery URL Query component
     * @return Parsed query parameters
     */
    public List<Tuple2<String, String>> parse( String urlQuery );
}
