package rip.deadcode.abukuma3.multipart.internal;

import com.google.common.collect.Multimap;
import org.apache.commons.fileupload.FileItem;
import rip.deadcode.abukuma3.collection.AbstractPersistentMultimap;
import rip.deadcode.abukuma3.multipart.Multipart;


public final class MultipartImpl
        extends AbstractPersistentMultimap<String, FileItem, Multipart>
        implements Multipart {

    private MultipartImpl() {
        super();
    }

    private MultipartImpl( Multimap<String, FileItem> delegate ) {
        super( delegate );
    }

    private MultipartImpl( Envelope<String, FileItem> delegate ) {
        super( delegate );
    }

    @Override protected final MultipartImpl constructor( Envelope<String, FileItem> delegate ) {
        return new MultipartImpl( delegate );
    }

    public static Multipart create() {
        return new MultipartImpl();
    }

    public static Multipart cast( Multimap<String, FileItem> delegate ) {
        return new MultipartImpl( delegate );
    }
}
