package rip.deadcode.abukuma3.multipart;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.fileupload.FileItem;
import rip.deadcode.abukuma3.collection.PersistentMultimapView;
import rip.deadcode.abukuma3.multipart.internal.MultipartImpl;

import java.util.List;
import java.util.Map;


public interface Multipart
        extends PersistentMultimapView<String, FileItem, Multipart> {

    public static Multipart create() {
        return MultipartImpl.create();
    }

    public static Multipart cast( Multimap<String, FileItem> map ) {
        return MultipartImpl.cast( map );
    }

    // TODO use a persistent multimap
    public static Multipart create( Map<String, List<FileItem>> items ) {

        ListMultimap<String, FileItem> temp = items
                .entrySet()
                .stream()
                .collect(
                        ArrayListMultimap::create,
                        ( acc, entry ) -> acc.putAll( entry.getKey(), entry.getValue() ),
                        ( acc, other ) -> acc.putAll( other )
                );
        return MultipartImpl.cast( temp );
    }
}
