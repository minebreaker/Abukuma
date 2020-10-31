# Lens

Actually it's more like a traversal.

* `Pathable` is an object that supports `Lens`.


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
