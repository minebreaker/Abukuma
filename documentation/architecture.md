# 阿武隈
## Abukuma - Simple-Stupid Web Framework

アブクマは、シンプルさとスタイルを兼ねそろえたJava/Kotlin向けのHTTPライブラリーです。


## 1. Preface

### Motivation

アブクマはFinagleの小さなラッパーであり、ルーティング機能を提供することと、簡潔なHTTPリクエスト/レスポンスの操作を可能とすることに注力します。  
(もし`com.twitter.finagle.http.service.RoutingService`を気に入っているのなら、ただのFinagleを使うことをお勧めします)  

もし信頼できる高性能なライブラリーを探しているのであれば、以下を検討してみてください。

* [Finatra](https://twitter.github.io/finatra)
* [Vert.x](http://vertx.io)
* [Ratpack]()


## 2. Design Goal

### Simple

* API easily understandable

### Explicitness over implicitness

* No implicit 404
* No implicit 

### Good old Java

* No XML, No Annotations
* Slightly opinionated


## 3. Overview

### コンポーネント

* Application
    * Config
* Router
* Component
    * Service
    * Repository
* View
    * Template
    * JSON


## 4. Getting started

```java
class Application {

    public static void main(String[] args) {
        Abukuma.config(config())
               .route(router())
               .run();
    }

    private static Config config() {
        return Config.builder()
                     .port(8080)
                     .build();
    }

    private static Router router() {
        return Router.builder()
                     .get("/", ctx -> "<h1>hello, world</h1>")
                     .notFound("/", ctx -> "<div>not found</div>")
                     .build();
    }

}
```


## 5. RxJavaインテグレーション


## 6. Guiceインテグレーション


## 7. Sessionエクステンション

