# 阿武隈 - Simple web framework for Java/Kotlin

![GitHub](https://github.com/github/docs/actions/workflows/main.yml/badge.svg?branch=master)
[![CircleCI](https://circleci.com/gh/minebreaker/Abukuma.svg?style=svg)](https://circleci.com/gh/minebreaker/Abukuma)
[![codecov](https://codecov.io/gh/minebreaker/Abukuma/branch/master/graph/badge.svg)](https://codecov.io/gh/minebreaker/Abukuma)

Abukuma is a simple and elegant web framework for Java/Kotlin.

Abukuma is a thin wrapper and interfaces for the [Eclipse Jetty](https://www.eclipse.org/jetty)
and other web server implementations.
Its aim is to provide fluent routing functions and simple way to manipulate HTTP request/response.


* Immutable objects (request, response, etc)
* Persistent map and list
* Function-oriented (router as a function, handler as a function)
* Explicit configuration. No magics.
* No magics. No XML, (mostly) no Annotations
* Modular. Configurable. Extendable.


## Getting started

Requires Java 11+

```java
class Application {

    public static void main( String[] args ) {
        Abukuma.router( Routers.get("/", Responses.create( "<h1>hello, world</h1>" )
                                                  .header( h -> h.contentType( "text/html" ) ) ) )
               .run();
    }
}
```
