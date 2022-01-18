## Features

* Test utilities
* Exception handler with type-based dispatch
* Lens
    * Strict lenses (Prism, Lens, Optional...) or more easy-to-use ones (`getIn()` `setIn()` things)?
* SSL Cert
* Memoizer
* PersistentSet


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
* Write tests
* Replace Collections to internal PersistentCollections
    * Replace Guava immutable collections
    * Replace java.util collections
* Request body should be pre-parsed by the framework?
    * When called multiple times...
    * Type must be specified at compile time. That's not good.
* Request should be lazy?
    * Apparently this requires what to be parsed must be determined in advance.
      Required parameters should be configurable for the sake of performance.
* Better Renderer/Parser/Router
    * Special lists which is easy to modify
    * Monoid
    * Both of them

* Modular routing mechanism
    * Reusable routing path system 
* `MessageResolver` `ResourceBundleMessageResolver` for internal messages/logs
* Documentation for the generator plugin
* Need to think about collection nullability
    * `PersistentNullableList`
* Better DI and lifecycle hooks
* Rewrite `HandlerAdapter`
* Remove all Exceptions and replace to Try monad
  * Make `Try` monad
* Lint and formatter for the yaml and markdown files


## Ideas

* Async Wrapper (`Future`)
    * F<~>
* Kotlin-friendliness (api, collection, helper...)
* Integrated web framework
* Netty backend
    * Vert.x
* Graal VM Compatibility check
* JDK support - Oracle LTS lines?
* `Option` `Either` rather than exceptions?
* Maybe service loader is not a great idea


## Graveyard

* Content-type based dispatcher
->
    * Useless if actual parsers are not installed
    * Actually, what needed is a content-type based parser?
    * Maybe frameworks should have those

* Problem: Static imports are tedious
    * Single factory class for all?
        * `rip.deadcode.abukuma3.utils.StaticImports`
->
    * This is not necessary

* ~~Type hierarchy should be expressed as trees, not lists~~ This is not as easy as it seems - need more considerations
    * e.g.) parsers, renderers...
    * Searching should be faster
