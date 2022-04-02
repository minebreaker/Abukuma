package rip.deadcode.abukuma3.utils.url;

import rip.deadcode.abukuma3.collection.PersistentList;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;


/**
 * Represents the model of URL.
 * Modeling is mostly based on the <a href="https://url.spec.whatwg.org/#url-representation">WHATWG URL
 * representation</a>, but full compatibility is not our aim nor guaranteed.
 *
 * Each field returns the raw, non encoded values.
 * {@link UrlRenderer} is responsible for how to escape them.
 */
public interface UrlModel {

    public Optional<String> scheme();

    public UrlModel scheme( String scheme );

    /**
     * Username part of the url.
     * Unlike the WHATWG standard, {@link UrlModel} distinguishes null between empty string.
     */
    public Optional<String> username();

    public UrlModel username( String username );

    public Optional<String> password();

    public UrlModel password( String password );

    public Optional<String> host();

    public UrlModel host( String host );

    public OptionalInt port();

    public UrlModel port( int port );

    public PersistentList<String> path();

    public UrlModel path( List<String> path );

    /**
     * @return Optional of the {@link UrlQuery} object.
     *         Empty if the query part is not set.
     *         Empty query (i.e. <code>?</code> with trailing empty string) will return a {@link UrlQuery} with no
     *         parameters.
     */
    public Optional<UrlQuery> query();

    public UrlModel query( UrlQuery query );

    public Optional<String> fragment();

    public UrlModel fragment( String fragment );
}
