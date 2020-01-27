## Features

* Test utilities
* Content-type based dispatcher
* Exception handler with type-based dispatch
* Lens
    * Strict lenses (Prism, Lens, Optional...) or more easy-to-use ones (`getIn()` `setIn()` things)?
* SSL Cert
* Better DI and lifecycle hooks


## Bugs

* Problem: case-sensitive header key is confusing
    * ~~Case-insensitive header~~
        * Lower case for internal store
* `generateDataClass` execution order problem causing build failure


## Refactoring

* Builder -> Immutable copying object
* Value classes
    * Auto generation
    * Config implementing Map
* Add additional header getter methods for the `Header`
* Problem: Modules can't have configurations
    * Flexible configuration schema
* Problem: Static imports are tedious
    * Single factory class for all?
        * `rip.deadcode.abukuma3.utils.StaticImports`
* Problem: Are `Abu` prefixes necessary?
    * Remove prefixes?
* `Request` should be lazy or not?
* Problem: Specifying server factory is odd
    * Specify server class (not factory) in config
* Write tests
* Move collections to a new independent project
* Replace Guava ImmutableCollection to internal ones
* Type hierarchy should be expressed as trees, not lists
    * e.g.) parsers, renderers...
    * Searching should be faster
* Request body should be pre-parsed by the framework?
    * When called multiple times...
    * Type must be specified at compile time. That's not good.
* Request should be lazy?
    * Apparently this requires what to be parsed must be determined in advance.
      Required parameters should be configurable for the sake of performance.


## Ideas

* Async Wrapper (`Future`)
* Kotlin-friendliness (api, collection, helper...)
* Integrated web framework
* Netty backend
    * Vert.x
* Graal VM Compatibility check
* JDK support - Oracle LTS lines?
