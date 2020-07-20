package rip.deadcode.abukuma3.parser;

import com.google.common.collect.Multimap;
import rip.deadcode.abukuma3.collection.PersistentMultimapView;
import rip.deadcode.abukuma3.value.internal.UrlEncodedImpl;


public interface UrlEncoded extends PersistentMultimapView<String, String, UrlEncoded> {

    public static UrlEncoded create() {
        return UrlEncodedImpl.create();
    }

    public static UrlEncoded create( Multimap<String, String> delegate ) {
        return UrlEncodedImpl.create( delegate );
    }
}
