package rip.deadcode.abukuma3.multipart.internal;


import com.google.common.collect.Multimap;
import org.apache.commons.fileupload.FileItem;
import rip.deadcode.abukuma3.collection.AbstractPersistentMultimap;
import rip.deadcode.abukuma3.multipart.Multipart;


public final class MultipartImpl
        extends AbstractPersistentMultimap<String, FileItem, Multipart>
        implements Multipart {

    private MultipartImpl( Envelope<String, FileItem> envelope ) {
        super( envelope );
    }

    private MultipartImpl( Multimap<String, FileItem> delegate ) {
        super( delegate );
    }

    public static MultipartImpl create( Multimap<String, FileItem> delegate ) {
        return new MultipartImpl( delegate );
    }

    @Override protected final Multipart constructor( Envelope<String, FileItem> delegate ) {
        return new MultipartImpl( delegate );
    }
}
