package rip.deadcode.abukuma3.multipart;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import rip.deadcode.abukuma3.parser.AbuParser;
import rip.deadcode.abukuma3.value.AbuRequestHeader;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;

import static rip.deadcode.abukuma3.internal.utils.Uncheck.uncheck;


// TODO `FileItemIterator` should be wrapped
public final class AbuFileItemIteratorParser implements AbuParser<FileItemIterator> {

    private static final ServletFileUpload upload = new ServletFileUpload();

    @Nullable @Override public FileItemIterator parse( Class<?> convertTo, InputStream body, AbuRequestHeader header ) throws IOException {

        if ( !convertTo.equals( FileItemIterator.class ) ) {
            return null;
        }

        if ( !ServletFileUpload.isMultipartContent( header.jettyRequest() ) ) {
            return null;
        }

        return uncheck( () -> upload.getItemIterator( header.jettyRequest() ) );
    }
}
