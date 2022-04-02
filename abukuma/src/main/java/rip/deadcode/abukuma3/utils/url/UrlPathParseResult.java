package rip.deadcode.abukuma3.utils.url;


import rip.deadcode.abukuma3.collection.PersistentList;


public interface UrlPathParseResult {

    public static interface Success extends UrlPathParseResult {

        public PersistentList<String> result();

        public PersistentList<UrlParseException> validationErrors();
    }

    public static interface Error extends UrlPathParseResult {
        public PersistentList<UrlParseException> validationErrors();
    }
}
