# Lens

Actually it's more like a traversal.

* `Pathable` is a object that supports `Lens`.


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
