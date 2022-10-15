## Features

- Test utilities
- SSL Cert

## Bugs

- Problem: case-sensitive header key is confusing
  - ~~Case-insensitive header~~
    - Lower case for internal store
    - Specific methods are made case-insensitive
    - Map methods are case-sensitive

## Refactoring

- Problem: Modules can't have configurations
  - Flexible configuration schema
- Write tests
- Better Renderer/Parser/Router
  - Special lists which is easy to modify
  - Monoid
  - Both of them
- Need to think about collection nullability
  - `PersistentNullableList`
- Better DI and lifecycle hooks
- Rewrite `HandlerAdapter`
- Strict header size limit to avoid DDoSes
- Move implementations in static methods

## Do it later

- Lint and formatter for the yaml and markdown files
- Path param / query param should be in header object?
  - `RequestHeader` and `RequestHeaderWithParams`?
- Add additional header getter methods for the `Header`
- `MessageResolver` `ResourceBundleMessageResolver` for internal messages/logs
- Java module
  - Jetty 11
  - SLF4J 2
- Find markdown formatter which allows multiple line breaks before headings

## Ideas

- Async Wrapper (`Future`)
  - F\<~>
- Kotlin-friendliness (api, collection, helper, componentsN...)
- Integrated web framework
- Netty backend
  - Vert.x
- Graal VM Compatibility check
- JDK support - Oracle LTS lines?
- Maybe service loader is not a great idea
- Remove all Exceptions and replace to Try monad
  - `Option` `Either` rather than exceptions?
  - Make `Try` monad
  - Should stick to standard Java?
- Rewrite all int calculation with `Math.exact`
- Memoizer
- WebSockets
- Sphinx documentations
- Lens
  - Strict lenses (Prism, Lens, Optional...) or more easy-to-use ones (`getIn()`
    `setIn()` things)?

## Graveyard

- Content-type based dispatcher ->

  - Useless if actual parsers are not installed
  - Actually, what needed is a content-type based parser?
  - Maybe frameworks should have those

- Problem: Static imports are tedious

  - Single factory class for all?
    - `rip.deadcode.abukuma3.utils.StaticImports` ->
  - This is not necessary

- ~~Type hierarchy should be expressed as trees, not lists~~ This is not as easy
  as it seems - need more considerations

  - e.g.) parsers, renderers...
  - Searching should be faster

- Automatic code generation

  - First, I thought automatic code generations for data classes are good idea,
    turned out as cumbersome as hand-writing them.
  - Refactoring and debugging got very hard.

- Exception handler with type-based dispatch

  - I don't recommend using `ExceptionHandler` for handling exceptions. Catching
    all errors inside the handler makes the code much simpler and cleaner.
    `ExceptionHandler` should be used for truly exceptional situations.

- Request body should be pre-parsed by the framework?

  - When called multiple times...
  - Type must be specified at compile time. That's not good.
    - Is it really?
    - This will guarantee runtime type safety, so you don't need to type
      validity every time

- Request should be lazy?

  - Apparently this requires what to be parsed must be determined in advance.
    Required parameters should be configurable for the sake of performance.
    - Since 1. the project goal is usability over the performance, and 2. some
      object creation will not be *that* slow.
