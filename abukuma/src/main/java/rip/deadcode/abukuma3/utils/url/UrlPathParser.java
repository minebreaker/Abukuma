package rip.deadcode.abukuma3.utils.url;


import rip.deadcode.abukuma3.collection.PersistentList;


/**
 *
 */
public interface UrlPathParser {

    public UrlPathParseResult parse( String urlPath );

    public static interface Success extends UrlParseResult {

        public UrlModel result();

        public PersistentList<UrlParseException> validationErrors();
    }

    public static interface Error extends UrlParseResult {
        public PersistentList<UrlParseException> validationErrors();
    }
}
