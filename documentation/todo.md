## Features

* Test utilities
* Lens
    * Strict lenses (Prism, Lens, Optional...) or more easy-to-use ones (`getIn()` `setIn()` things)?
* SSL Cert
* PersistentSet


## Bugs

* Problem: case-sensitive header key is confusing
    * ~~Case-insensitive header~~
        * Lower case for internal store
        * Specific methods are made case-insensitive
        * Map methods are case-sensitive


## Refactoring

* Builder -> Immutable copying object
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
* Lint and formatter for the yaml and markdown files
* Strict header size limit to avoid DDoSes
* Java 11


## Ideas

* Async Wrapper (`Future`)
    * F<~>
* Kotlin-friendliness (api, collection, helper, componentsN...)
* Integrated web framework
* Netty backend
    * Vert.x
* Graal VM Compatibility check
* JDK support - Oracle LTS lines?
* Maybe service loader is not a great idea
* I gave up code generation
    * Hard to refactor
* Remove all Exceptions and replace to Try monad
    * `Option` `Either` rather than exceptions?
    * Make `Try` monad
    * Should stick to standard Java?
* Rewrite all int calculation with `Math.exact`
* Memoizer
* PersistentCollectors
* WebSockets


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

* Automatic code generation
    * First, I thought automatic code generations for data classes are good idea,
      turned out as cumbersome as hand-writing them.
    * Refactoring and debugging got very hard.

* Exception handler with type-based dispatch
    * I don't recommend using `ExceptionHandler` for handling exceptions.
      Catching all errors inside the handler makes the code much simpler and cleaner.
      `ExceptionHandler` should be used for truly exceptional situations.
