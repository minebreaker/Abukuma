package rip.deadcode.abukuma3.value;

import com.google.common.collect.Multimap;
import rip.deadcode.abukuma3.collection.PersistentMultimapView;
import rip.deadcode.abukuma3.value.internal.UrlEncodedImpl;


/**
 * Represents HTTP URL encoded body requests, typically used in a HTML POST form.
 */
public interface UrlEncoded extends PersistentMultimapView<String, String, UrlEncoded> {

    public static UrlEncoded create() {
        return UrlEncodedImpl.create();
    }

    public static UrlEncoded create( Multimap<String, String> delegate ) {
        return UrlEncodedImpl.create( delegate );
    }
}
