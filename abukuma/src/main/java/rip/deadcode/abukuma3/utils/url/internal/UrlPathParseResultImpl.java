package rip.deadcode.abukuma3.utils.url.internal;

import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.utils.url.UrlParseException;
import rip.deadcode.abukuma3.utils.url.UrlPathParseResult;


public final class UrlPathParseResultImpl {

    private UrlPathParseResultImpl() {
        throw new Error();
    }

    public static final class SuccessImpl implements UrlPathParseResult.Success {

        private final PersistentList<String> result;
        private final PersistentList<UrlParseException> validationErrors;

        public SuccessImpl( PersistentList<String> result, PersistentList<UrlParseException> validationErrors ) {
            this.result = result;
            this.validationErrors = validationErrors;
        }

        @Override public PersistentList<String> result() {
            return result;
        }

        @Override public PersistentList<UrlParseException> validationErrors() {
            return validationErrors;
        }
    }

    public static final class ErrorImpl implements UrlPathParseResult.Error {

        private final PersistentList<UrlParseException> validationErrors;

        public ErrorImpl( PersistentList<UrlParseException> validationErrors ) {
            this.validationErrors = validationErrors;
        }

        @Override public PersistentList<UrlParseException> validationErrors() {
            return validationErrors;
        }
    }
}
