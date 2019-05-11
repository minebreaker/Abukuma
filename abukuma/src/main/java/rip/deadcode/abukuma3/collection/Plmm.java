package rip.deadcode.abukuma3.collection;

import com.google.common.collect.*;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.organicdesign.fp.collections.ImList;
import org.organicdesign.fp.collections.ImMap;
import org.organicdesign.fp.collections.PersistentHashMap;

import java.util.*;

import static java.util.stream.Collectors.*;
import static org.organicdesign.fp.StaticImports.vec;


@SuppressWarnings("SuspiciousMethodCalls")
public class Plmm implements ListMultimap<String, String> {

    private final ImMap<String, ImList<String>> delegate;

    public Plmm() {
        this.delegate = PersistentHashMap.empty();
    }

    private Plmm(ImMap<String, ImList<String>> delegate) {
        this.delegate = delegate;
    }

    public Plmm add(String key, String value) {
        if (delegate.containsKey(key)) {
            return new Plmm(delegate.assoc(key, delegate.get(key).append(value)));
        } else {
            return new Plmm(delegate.assoc(key, vec(value)));
        }
    }

    @Override
    public List<String> get(@Nullable String key) {
        return delegate.containsKey(key) ? delegate.get(key) : ImmutableList.of();
    }

    @Override
    public int size() {
        //noinspection SimplifyStreamApiCallChains  // ImMap deprecating #values()
        return delegate.entrySet().stream().mapToInt(e -> e.getValue().size()).sum();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public boolean containsKey(@Nullable Object key) {
        //noinspection SuspiciousMethodCalls
        return delegate.containsKey(key);
    }

    @Override
    public boolean containsValue(@Nullable Object value) {
        return delegate.entrySet().stream().anyMatch(e -> contains(e.getValue(), value));
    }

    @Override
    public boolean containsEntry(@Nullable Object key, @Nullable Object value) {
        return delegate.containsKey(key) && contains(delegate.get(key), value);
    }

    @Override
    public Set<String> keySet() {
        return delegate.keySet();
    }

    @Override
    public Multiset<String> keys() {
        return entries().stream()
                .collect(groupingBy(Map.Entry::getKey))
                .entrySet().stream()
                .collect(
                        HashMultiset::create,
                        (acc, e) -> acc.add(e.getKey(),
                                e.getValue().size()),
                        Multisets::sum
                );
    }

    @Override
    public Collection<String> values() {
        return delegate.entrySet().stream().flatMap(e -> e.getValue().stream()).collect(toList());
    }

    @Override
    public Collection<Map.Entry<String, String>> entries() {
        return delegate.entrySet().stream()
                .flatMap(e -> e.getValue().stream().map(v -> Maps.immutableEntry(e.getKey(), v)))
                .collect(toSet());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Collection<String>> asMap() {
        return ((Map<String, Collection<String>>) (Object) delegate);
    }

    @Override
    public List<String> removeAll(@Nullable Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean put(@Nullable String key, @Nullable String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(@Nullable Object key, @Nullable Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean putAll(@Nullable String key, Iterable<? extends String> values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean putAll(Multimap<? extends String, ? extends String> multimap) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> replaceValues(String key, Iterable<? extends String> values) {
        throw new UnsupportedOperationException();
    }

    private static boolean contains(ImList<?> list, Object value) {
        for (Object e : list) {
            if (Objects.equals(e, value)) {
                return true;
            }
        }
        return false;
    }
}
