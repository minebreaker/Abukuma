## Features

* Test utilities
* Exception handler with type-based dispatch
* Lens
    * Strict lenses (Prism, Lens, Optional...) or more easy-to-use ones (`getIn()` `setIn()` things)?
* SSL Cert
* Memoizer


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
* ~~Type hierarchy should be expressed as trees, not lists~~ This is not as easy as it seems - need more considerations
    * e.g.) parsers, renderers...
    * Searching should be faster
* Request body should be pre-parsed by the framework?
    * When called multiple times...
    * Type must be specified at compile time. That's not good.
* Request should be lazy?
    * Apparently this requires what to be parsed must be determined in advance.
      Required parameters should be configurable for the sake of performance.
* Even data classes should be exposed as interfaces, not classes
* Renderer/Parser chains should be special lists, since it's much easier to modify
* Modular routing mechanism
    * Reusable routing path system 
* `MessageResolver` `ResourceBundleMessageResolver` for internal messages/logs
* Documentation for the generator plugin
* Need to think about collection nullability
* Context should not extend Registry
* Better DI and lifecycle hooks


## Ideas

* Async Wrapper (`Future`)
* Kotlin-friendliness (api, collection, helper...)
* Integrated web framework
* Netty backend
    * Vert.x
* Graal VM Compatibility check
* JDK support - Oracle LTS lines?


## Graveyard

* Content-type based dispatcher
->
    * Useless if actual parses are not installed
    * Actually, what needed is a content-type based parser?
    * Maybe frameworks should have those

* Problem: Static imports are tedious
    * Single factory class for all?
        * `rip.deadcode.abukuma3.utils.StaticImports`
->
    * This is not necessary
