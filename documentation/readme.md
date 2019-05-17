# 阿武隈 - Simple web framework for Java/Kotlin

[![CircleCI](https://circleci.com/gh/minebreaker/Abukuma.svg?style=svg)](https://circleci.com/gh/minebreaker/Abukuma)
[![codecov](https://codecov.io/gh/minebreaker/Abukuma/branch/master/graph/badge.svg)](https://codecov.io/gh/minebreaker/Abukuma)

Abukuma is a simple and elegant web framework for Java/Kotlin.

Abukuma is a thin wrapper for the [Eclipse Jetty](https://www.eclipse.org/jetty).
It's aim is to provide fluent routing functions and simple way to manipulate HTTP request/response.


* Immutable objects (request, response, etc)
* Persistent map and list
* Function-oriented (router as a function, handler as a function)
* Explicit configuration. No magics.
* No XML, (mostly) no Annotations


## Getting started

```java
class Application {

    public static void main( String[] args ) {
        Abukuma.config( AbuConfig.create() )
               .router( AbuRouters.get("/", AbuResponses.create( "<h1>hello, world</h1>" )
                                                        .header( h -> h.contentType( "text/html" ) ) ) )
               .build()
               .run();
    }
}
```
