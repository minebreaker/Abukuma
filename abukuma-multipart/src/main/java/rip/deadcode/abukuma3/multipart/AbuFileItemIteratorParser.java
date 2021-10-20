package rip.deadcode.abukuma3.multipart;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import rip.deadcode.abukuma3.parser.Parser;
import rip.deadcode.abukuma3.value.RequestHeader;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

import static rip.deadcode.abukuma3.internal.utils.Uncheck.uncheck;


// TODO `FileItemIterator` should be wrapped
public final class AbuFileItemIteratorParser implements Parser<FileItemIterator> {

    private static final ServletFileUpload upload = new ServletFileUpload();

    @Nullable @Override public FileItemIterator parse( Class<?> convertTo, InputStream body, RequestHeader header ) {

        if ( !convertTo.equals( FileItemIterator.class ) ) {
            return null;
        }

        if ( !ServletFileUpload.isMultipartContent( (HttpServletRequest) header.rawRequest() ) ) {
            return null;
        }

        return uncheck( () -> upload.getItemIterator( (HttpServletRequest) header.rawRequest() ) );
    }
}
