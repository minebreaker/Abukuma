# Lens

Actually it's more like a traversal.

* `Pathable` is an object that supports `Lens`.


Lens: (E, O) => { getter: O -> E, setter: (O, E) -> O }
Path: T => T -> Lens

```
PersistentMap<String, Integer> m = PersistentCollections.createMap("foo", 123, "bar", 456);
// { "foo": 123, "bar": 456 }

Lens<Integer, Map<String, Integer>> l = Lenses.createForMap("foo");
Lens<Integer, Map<String, Integer>> l = Lenses.path("foo");
// or
Pathable<Integer, Map<String, Integer>> p = m;  // Persistent objects are mappable
Lens<Integer, Map<String, Integer>> l = p.path("foo");

l.get(m);  // 123
l.set(m, 789);  // { "foo", 789, "bar": 456 }
l.getter();
l.setter();

GenericLens l = Lenses.generic("foo");
Integer r = l.get<>(m);
Map<String, Integer> m = l.set<>(m, 789);
```



## Pathable

`Pathable` is an object that supports `Lens` when given a string *path*. 

### Path query syntax

```
path : segment ( '/' segment )* ;

segment : string
        | regex_segment
        | '*'
        ;

regex_segment : '{' regex '}'
```

TODO: Should support more sophisticated syntax.
