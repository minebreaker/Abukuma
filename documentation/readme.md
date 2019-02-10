# 阿武隈 - Simple web framework for Java/Kotlin

Abukuma is a simple and elegant web framework for Java/Kotlin.


## 1. Preface

### Motivation

> Simple != Easy

Abukuma is a tiny wrapper for the [Eclipse Jetty](https://www.eclipse.org/jetty).
It's aim is to provide fluent routing functions and simple way to manipulate HTTP request/response.


## 2. Design Goal

### Simplicity

* Immutable objects
* Function-oriented(Router as a function, handler as a function)

### Explicit rather than implicit

* But with carefully chosen defaults

### Good old Java

* No XML, no Annotations
* Slightly opinionated


## 4. Getting started

```java
class Application {

    public static void main( String[] args ) {
        Abukuma.config( AbuConfig.create() )
               .router( AbuRouters.builder()
                                  .get("/", request -> AbuResponse.create( "<h1>hello, world</h1>" )
                                                                  .header( h -> h.contentType( "text/html" ) ) )
                                  .build() )
               .build()
               .run();
    }
}
```