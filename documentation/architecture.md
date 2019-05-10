# Architecture

## Flow

1. A user sends a request.
2. The server receives the request and parse headers.
3. The `Router` investigates the request URI and headers,
    decide which `Handler` to invoke to handle the request.
4. `Filter` may modify headers that handlers will receive, or can interrupt and return its own response.
5. The `Handler` receives the request, and returns a response to it.
6. `Handler`s can call `Parser` to parse the http body into an appropriate class.
7. The body object inside the response is converted to the output stream by `Renderer`.


## Components

* Application
    * Abukuma
    * AbuServer
    * Config
* Routing
    * Router
    * Routers
    * RoutingContext
* Filter
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


### Routing

```
Router :: RoutingContext -> RoutingResult
RoutingContext :: ( Header, ContextPath )
RoutingResult :: ( Parameters, Handler )

Route :: ( RouteMatcher, Handler )

RouteMatcher :: RoutingContext -> Parameters
PathMatcher :: Path -> Parameters
```


### `Config`

* `AbuConfig.create()` `AbuConfig.development()` `AbuConfig.production()`
* `AbuPojoConfig`
* `AbuConfig.json()` `AbuConfig.yaml()` `AbuConfig.properties()`
* `AbuTypeSafeConfig.load()`
