# Architecture

## Flow

1. A user sends a request.
2. The server receives the request and parse headers.
3. The `Router` investigates the request URI and headers,
    decide which `Handler` to invoke to handle the request.
4. The `Handler` receives the request, and returns a response to it.
5. `Handler`s can call `Parser` to parse the http body into an appropriate class.
6. The body object inside the response is converted to the output stream by `Renderer`.


## Components

* Application
    * Abukuma
    * AbuServer
    * Config
* Routing
    * Router
    * Routers
    * RoutingContext
* Handler
    * ErrorHandler
* Parser
    * StringParser
    * InputStreamParser
* Renderer
    * CharSequenceRenderer
    * OutputStreamRenderer
    * ThymeleafRenderer
    * JsonRenderer
* Utilities
    * UriBuilder


### `Config`

* `AbuConfig.create()` `AbuConfig.development()` `AbuConfig.production()`
* `AbuPojoConfig`
* `AbuConfig.json()` `AbuConfig.yaml()` `AbuConfig.properties()`
* `AbuTypeSafeConfig.load()`
