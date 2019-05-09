package rip.deadcode.abukuma3.multipart;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import rip.deadcode.abukuma3.parser.AbuParser;
import rip.deadcode.abukuma3.value.AbuRequestHeader;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static rip.deadcode.abukuma3.internal.utils.Uncheck.uncheck;


public final class AbuMultipartParser implements AbuParser<Multipart> {

    @Nullable @Override public Multipart parse(
            Class<?> convertTo, InputStream body, AbuRequestHeader header ) throws IOException {

        if ( !convertTo.equals( Multipart.class ) ) {
            return null;
        }

        if ( !ServletFileUpload.isMultipartContent( (HttpServletRequest) header.rawRequest() ) ) {
            return null;
        }

        ServletFileUpload upload = new ServletFileUpload();
        Map<String, List<FileItem>> items =
                uncheck( () -> upload.parseParameterMap( (HttpServletRequest) header.rawRequest() ) );

        return Multipart.create( items );
    }
}
