package rip.deadcode.abukuma3.multipart;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.fileupload.FileItem;
import rip.deadcode.abukuma3.internal.AbuAbstractMultimap;

import java.util.List;
import java.util.Map;


public final class Multipart extends AbuAbstractMultimap<String, FileItem, Multipart> {

    private final Multimap<String, FileItem> delegate;

    private Multipart( Multimap<String, FileItem> delegate ) {
        this.delegate = delegate;
    }

    static Multipart create( Map<String, List<FileItem>> items ) {

        Multimap<String, FileItem> temp = items
                .entrySet()
                .stream()
                .collect(
                        HashMultimap::create,
                        ( acc, entry ) -> acc.putAll( entry.getKey(), entry.getValue() ),
                        ( acc, other ) -> acc.putAll( other )
                );
        return new Multipart( ImmutableMultimap.copyOf( temp ) );
    }

    @Override public Multipart constructor( Multimap<String, FileItem> delegate ) {
        return new Multipart( delegate );
    }

    @Override protected Multimap<String, FileItem> delegate() {
        return delegate;
    }
}
