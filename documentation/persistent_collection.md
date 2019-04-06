# Persistent collection

Since Abukuma embraces immutability, we use thin wrapper of [Paguro](https://github.com/GlenKPeterson/Paguro) for our
primary data structure.
In addition, we provide persistent
[`ListMultimap`](https://github.com/google/guava/wiki/NewCollectionTypesExplained#multimap)
of Google Guava.

* All mutating operations will throw `UnsupportedOperationException`
* Rejects `null` values


## Methods for `PersistentList`
## Methods for `PersistentMap`

## Methods for `PersistentListMultimap`

### Get

getValue(K): V

    `{k1: [v1, v2]}.getValue(k1) -> v1`
    `{k1: [v1]}.getValue(k2) -> throw exception`

get(K): List<V>  (Guava default)

    `{k1: [v1, v2]}.getValue(k1) -> [v1, v2]`
    `{k1: [v1]}.getValue(k2) -> []`

mayGet(K): Optional<V>

    `{k1: [v1, v2]}.getValue(k1) -> Optional[v1]`
    `{k1: [v1]}.getValue(k2) -> Optional.Empty`


### Modify

add(K, V): T

    `{}.add(k1, v1) -> {k1: [v1]}`
    `{k1: [v1]}.add(k1, v2) -> {k1: [v1, v2]}`
    `{k1: [v1]}.add(k2, v2) -> {k1: [v1], k2: [v2]}`
    `{}.add(k1, null) -> throw exception`

add(K, Iterable<V>): T

    `{}.addAll(k1, [v1]) -> {k1: [v1]}`
    `{k1: [v1]}.addAll(k1, [v2]) -> {k1: [v1, v2]}`
    `{k1: [v1]}.addAll(k2, [v2]) -> {k1: [v1], k2: [v2]}`
    `{}.addAll(k1, null) -> throw exception`

set(K, V): T

    `{}.set(k1, v1) -> {k1: [v1]}`
    `{k1: [v1]}.set(k1, v2) -> {k1: [v2]}`
    `{k1: [v1]}.set(k2, v2) -> {k1: [v1], k2: [v2]}`
    `{}.set(k1, null) -> throw exception`

set(K, Iterable<V>): T

    `{}.set(k1, [v1]) -> {k1: [v1]}`
    `{k1: [v1]}.set(k1, [v2]) -> {k1: [v2]}`
    `{k1: [v1]}.set(k2, [v2]) -> {k1: [v1], k2: [v2]}`
    `{}.set(k1, null) -> throw exception`

<!--
merge(Multimap<K, V>): T

    `{k1: [v1]}.merge({k1, [v2]}) -> {k1: [v1, v2]}`
    `{k1: [v1]}.merge({k2, [v2]}) -> {k1: [v1], k2: [v2]}`
    `{}.merge(null) -> throw exception`
-->


### Remove

delete(K): T

    `{k1: [v1]}.delete(k1) -> {}`
    `{}.delete(k1) -> throw exception`
