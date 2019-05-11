package rip.deadcode.abukuma3.collection.traverse;

import java.util.Map;
import java.util.Set;

public interface QPath<T> {

    public static <T> QPath<T> compile(String path, Class<T> cls) {
        return null;
    }

    public Set<T> get(Map<?, ?> from, Class<T> cls);

    public Set<T> get(Iterable<?> from, Class<T> cls);

    public <K, V> Map<K, V> set(Map<K, V> to, T value);

    public <I> Iterable<I> set(Iterable<I> to, T value);
}
