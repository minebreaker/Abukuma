# Lens

Actually it's more like a traversal.

* `Pathable` is an object that supports `Lens`.


## Path query syntax

```
path : segment ( '/' segment )* ;

segment : string
        | regex_segment
        | '*'
        ;

regex_segment : '{' regex '}'
```

TODO: Should support more sophisticated syntax.
