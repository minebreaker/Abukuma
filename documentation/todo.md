## Features

* Test utilities
* Content-type based dispatcher
* Exception handler with type-based dispatch
* Lens
    * Strict lens (Prism, Lens, Optional...) or more easy-to-use ones (`getIn()` `setIn()` things)?


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
* Write tests


## Ideas

* Async Wrapper (`Future`)
* Kotlin-friendliness (api, collection, helper...)
* Integrated web framework
* Netty backend
    * Vert.x
