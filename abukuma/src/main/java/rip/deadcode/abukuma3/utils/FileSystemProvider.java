package rip.deadcode.abukuma3.utils;

import java.nio.file.FileSystem;


@FunctionalInterface
public interface FileSystemProvider {

    public FileSystem provideFileSystem();
}
