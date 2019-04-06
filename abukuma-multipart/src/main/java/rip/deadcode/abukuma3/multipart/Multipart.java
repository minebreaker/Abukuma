package rip.deadcode.abukuma3.multipart;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import org.apache.commons.fileupload.FileItem;
import rip.deadcode.abukuma3.collection.AbstractPersistentListMultimap;

import java.util.List;
import java.util.Map;


// TODO `FIleItem` should be wrapped
public final class Multipart extends AbstractPersistentListMultimap<String, FileItem, Multipart> {

    private Multipart( Envelope<String, FileItem> envelope ) {
        super( envelope );
    }

    private Multipart( ListMultimap<String, FileItem> delegate ) {
        super( delegate );
    }

    static Multipart create( Map<String, List<FileItem>> items ) {

        ListMultimap<String, FileItem> temp = items
                .entrySet()
                .stream()
                .collect(
                        ArrayListMultimap::create,
                        ( acc, entry ) -> acc.putAll( entry.getKey(), entry.getValue() ),
                        ( acc, other ) -> acc.putAll( other )
                );
        return new Multipart( ImmutableListMultimap.copyOf( temp ) );
    }

    @Override public Multipart constructor( Envelope<String, FileItem> delegate ) {
        return new Multipart( delegate );
    }
}
