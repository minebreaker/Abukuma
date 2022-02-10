package rip.deadcode.abukuma3.utils.url.internal;

import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.utils.url.UrlModel;
import rip.deadcode.abukuma3.utils.url.UrlParseException;
import rip.deadcode.abukuma3.utils.url.UrlParseResult;


public final class UrlParseResultImpl {

    private UrlParseResultImpl() {
        throw new Error();
    }

    public static final class SuccessImpl implements UrlParseResult.Success {

        private final UrlModel result;
        private final PersistentList<UrlParseException> validationErrors;

        public SuccessImpl( UrlModel result, PersistentList<UrlParseException> validationErrors ) {
            this.result = result;
            this.validationErrors = validationErrors;
        }

        @Override public UrlModel result() {
            return result;
        }

        @Override public PersistentList<UrlParseException> validationErrors() {
            return validationErrors;
        }
    }

    public static final class ErrorImpl implements UrlParseResult.Error {

        private final PersistentList<UrlParseException> validationErrors;

        public ErrorImpl( PersistentList<UrlParseException> validationErrors ) {
            this.validationErrors = validationErrors;
        }

        @Override public PersistentList<UrlParseException> validationErrors() {
            return validationErrors;
        }
    }
}
