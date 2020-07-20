package rip.deadcode.abukuma3.value;

import com.google.common.collect.Multimap;
import rip.deadcode.abukuma3.collection.PersistentMultimapView;
import rip.deadcode.abukuma3.value.internal.HeaderImpl;


public interface Header extends PersistentMultimapView<String, String, Header> {

    // FIXME: common factory class?
    public static Header create() {
        return HeaderImpl.create();
    }

    public static Header create( Multimap<String, String> delegate ) {
        return HeaderImpl.create( delegate );
    }

    public String contentType();

    public Header contentType( String value );
}
