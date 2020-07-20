package rip.deadcode.abukuma3.multipart;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.apache.commons.fileupload.FileItem;
import rip.deadcode.abukuma3.collection.PersistentMultimapView;
import rip.deadcode.abukuma3.multipart.internal.MultipartImpl;

import java.util.List;
import java.util.Map;


// TODO `FIleItem` should be wrapped
public interface Multipart extends PersistentMultimapView<String, FileItem, Multipart> {

    // TODO: necessary?
    public static Multipart create( Map<String, List<FileItem>> items ) {

        ListMultimap<String, FileItem> temp = items
                .entrySet()
                .stream()
                .collect(
                        ArrayListMultimap::create,
                        ( acc, entry ) -> acc.putAll( entry.getKey(), entry.getValue() ),
                        ( acc, other ) -> acc.putAll( other )
                );
        return MultipartImpl.create( temp );
    }

    public static Multipart create( ListMultimap<String, FileItem> items ) {
        return MultipartImpl.create( items );
    }
}
