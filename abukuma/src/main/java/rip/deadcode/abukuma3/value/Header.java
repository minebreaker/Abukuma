package rip.deadcode.abukuma3.value;

import com.google.common.collect.Multimap;
import rip.deadcode.abukuma3.collection.PersistentMultimapView;
import rip.deadcode.abukuma3.utils.url.internal.HeaderImpl;

import java.util.Optional;


public interface Header extends PersistentMultimapView<String, String, Header> {

    public Optional<String> contentType();

    public Header contentType( String value );

    public static Header create() {
        return HeaderImpl.create();
    }

    public static Header cast( Multimap<String, String> map ) {
        return HeaderImpl.cast( map );
    }
}
