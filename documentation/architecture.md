# 阿武隈
## Abukuma - Simple-Stupid Web Framework

アブクマは、シンプルさとスタイルを兼ねそろえたJava/Kotlin向けのWebフレームワークです。


## 1. Preface

### Motivation

[Finatra](https://twitter.github.io/finatra)は優れたWebサービスフレームワークではありますが、
複雑で大きく、一般的な用途には機能過多に見えます。  
[Vert.x](http://vertx.io)は素晴らしいライブラリーですが、
単なる(特に小規模の)HTTPサーバーには不必要に高機能です。  
[Ratpack]()は非常によくできたライブラリーですが、非オピニオネーテッドであろうとする目標が、APIの簡潔さを損なっています。

アブクマはFinagleの小さなラッパーであり、ルーティング機能を提供することと、簡潔なHTTPリクエスト/レスポンスの操作を可能とすることに注力します。  


## 2. Design Goal

### Simple

* 分かりやすいAPI

### Explicity over implicity

* 驚き最小の原則

### Good old Java / Kotlin

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

```kotlin
object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Abukuma.config(config())
               .route(router())
               .run()
    }

    fun config(): Config {
        return Config.create({
            port(8080)
        })
    }

    fun router(): Router {
        return Router.create({
            "/" to  object: Service<String> {
                fun apply(): String {
                    return Future.value("<h1>hello, world</h1>")
                }
            }
            notFound to object: Service<String> {
                fun apply(): String {
                    return Future.value("<div>not found</div>")
                }
            }
        })
    }

}
```


## 5. RxJavaインテグレーション


## 6. Guiceインテグレーション
