package rip.deadcode.abukuma3.utils.internal;

import rip.deadcode.abukuma3.utils.FileSystemProvider;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;


public class DefaultFileSystemProvider implements FileSystemProvider {

    @Override public FileSystem provideFileSystem() {
        return FileSystems.getDefault();
    }
}
