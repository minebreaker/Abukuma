## Features

* Test utilities
* Content-type based dispatcher
* Exception handler with type-based dispatch
* Lens
    * Strict lens (Prism, Lens, Optional...) or more easy-to-use ones (`getIn()` `setIn()` things)?
* SSL Cert


## Bugs

* ~~Case-insensitive header~~
    * Lower case for internal store


## Refactoring

* Builder -> Immutable copying object
* Flexible configuration (config for modules)
* Single factory class for all?
* Remove prefixes?
* Value classes
    * Auto generation
    * Config implementing Map
* Fluent header API
* `Request` should be lazy or not?
* Specify server class (not factory) in config
* Write tests
* Move collections to a new independent project


## Ideas

* Async Wrapper (`Future`)
* Kotlin-friendliness (api, collection, helper...)
* Integrated web framework
* Netty backend
    * Vert.x
* Graal VM Compatibility check
* JDK support - Oracle LTS lines?
