package rip.deadcode.abukuma3.value;

import com.google.common.collect.Multimap;
import rip.deadcode.abukuma3.collection.PersistentMultimapView;
import rip.deadcode.abukuma3.utils.url.internal.UrlEncodedImpl;


public interface UrlEncoded
        extends PersistentMultimapView<String, String, UrlEncoded> {

    public static UrlEncoded create() {
        return UrlEncodedImpl.create();
    }

    public static UrlEncoded cast( Multimap<String, String> map ) {
        return UrlEncodedImpl.cast( map );
    }
}
