# Persistent collection

Since Abukuma embraces immutability, we use thin wrappers of
[Paguro](https://github.com/GlenKPeterson/Paguro) for our primary data
structures. In addition, we provide persistent
[`ListMultimap`](https://github.com/google/guava/wiki/NewCollectionTypesExplained#multimap)
of Google Guava.

- All mutating operations will throw `UnsupportedOperationException`
- Rejects `null` keys but accepts `null` values # TODO
- Clojure, the original implementation of Paguro, does not provide non-O(1)
  methods because of performance reasons, but that is a little inconvenient.

## Methods for `PersistentList`

mayGet(int): Optional<V>

first(): V last(): V addFirst(V): List<V> addLast(V): List<V>

```
`[v1, v2].assoc(v3) -> [v1, v2, v3]`
```

insert(int, V): List<V> replace(int, V): List<V> delete(int): List<V>

concat(Iterable<V>): List<V>

```
`[v1, v2].concat([v1, v2]) -> [v1, v2, v3, v4]`
```

## Methods for `PersistentMap`

mayGet(K): Optional<V> set(K, V): T delete(K, V): T merge(Map\<K, V>)

## Methods for `PersistentListMultimap`

### Get

getValue(K): V

```
`{k1: [v1, v2]}.getValue(k1) -> v1`
`{k1: [v1]}.getValue(k2) -> throw exception`
```

get(K): List<V> (Guava default)

```
`{k1: [v1, v2]}.getValue(k1) -> [v1, v2]`
`{k1: [v1]}.getValue(k2) -> []`
```

mayGet(K): Optional<V>

```
`{k1: [v1, v2]}.getValue(k1) -> Optional[v1]`
`{k1: [v1]}.getValue(k2) -> Optional.Empty`
```

### Modify

add(K, V): T

```
`{}.add(k1, v1) -> {k1: [v1]}`
`{k1: [v1]}.add(k1, v2) -> {k1: [v1, v2]}`
`{k1: [v1]}.add(k2, v2) -> {k1: [v1], k2: [v2]}`
`{}.add(k1, null) -> throw exception`
```

add(K, Iterable<V>): T

```
`{}.add(k1, [v1]) -> {k1: [v1]}`
`{k1: [v1]}.add(k1, [v2]) -> {k1: [v1, v2]}`
`{k1: [v1]}.add(k2, [v2]) -> {k1: [v1], k2: [v2]}`
`{}.addAll(k1, null) -> throw exception`
```

set(K, V): T

```
`{}.set(k1, v1) -> {k1: [v1]}`
`{k1: [v1]}.set(k1, v2) -> {k1: [v2]}`
`{k1: [v1]}.set(k2, v2) -> {k1: [v1], k2: [v2]}`
`{}.set(k1, null) -> throw exception`
```

set(K, Iterable<V>): T

```
`{}.set(k1, [v1]) -> {k1: [v1]}`
`{k1: [v1]}.set(k1, [v2]) -> {k1: [v2]}`
`{k1: [v1]}.set(k2, [v2]) -> {k1: [v1], k2: [v2]}`
`{}.set(k1, null) -> throw exception`
```

<!--
merge(Multimap<K, V>): T

    `{k1: [v1]}.merge({k1, [v2]}) -> {k1: [v1, v2]}`
    `{k1: [v1]}.merge({k2, [v2]}) -> {k1: [v1], k2: [v2]}`
    `{}.merge(null) -> throw exception`
-->

### Remove

delete(K): T

```
`{k1: [v1]}.delete(k1) -> {}`
`{}.delete(k1) -> throw exception`
```
