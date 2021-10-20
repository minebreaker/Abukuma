package rip.deadcode.abukuma3.utils.internal;

import rip.deadcode.abukuma3.Module;
import rip.deadcode.abukuma3.Registry;
import rip.deadcode.abukuma3.utils.FileSystemProvider;
import rip.deadcode.abukuma3.utils.MimeDetector;


public class DefaultModule implements Module {

    @Override
    public Registry apply( Registry registry ) {
        return registry
                .setSingleton( FileSystemProvider.class, new DefaultFileSystemProvider() )
                .setSingleton( MimeDetector.class, new DefaultMimeDetector() );
    }
}
