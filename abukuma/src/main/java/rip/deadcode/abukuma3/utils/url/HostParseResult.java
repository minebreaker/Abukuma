package rip.deadcode.abukuma3.utils.url;

import rip.deadcode.abukuma3.collection.PersistentList;


public interface HostParseResult {

    public static interface Success extends HostParseResult {

        public UrlModel result();

        public PersistentList<HostParseException> validationErrors();
    }

    public static interface Error extends HostParseResult {
        public PersistentList<HostParseException> validationErrors();
    }
}
