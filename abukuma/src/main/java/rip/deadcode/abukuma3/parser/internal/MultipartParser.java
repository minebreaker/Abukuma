package rip.deadcode.abukuma3.parser.internal;

import rip.deadcode.abukuma3.parser.AbuParser;
import rip.deadcode.abukuma3.parser.Multipart;
import rip.deadcode.abukuma3.value.AbuRequestHeader;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;

public final class MultipartParser implements AbuParser<Multipart> {

    @Nullable @Override public Multipart parse(
            Class<?> convertTo, InputStream body, AbuRequestHeader header ) throws IOException {


        if (!convertTo.equals( Multipart.class ) ) {
            return null;
        }


        return null;
    }
}
