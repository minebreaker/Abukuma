# 阿武隈 - シンプルなWebフレームワーク

アブクマは、シンプルさとスタイルを兼ねそろえたJava/Kotlin向けのHTTPライブラリーです。


## 1. Preface

### Motivation

> シンプル != 簡単

アブクマはJettyの小さなラッパーであり、ルーティング機能を提供することと、簡潔なHTTPリクエスト/レスポンスの操作を可能とすることに注力します。  

シンプルは簡単とイコールではありません。
「簡単」なWebアプリケーションフレームワークはたくさんあります。
代表例がRuby on Railsでしょう。
しかしRailsを使ったことのある人はみな経験しているように、簡単であることは、我々が解決しようとしているまさにその問題において簡単であることを意味しません。
アブクマは代わりにシンプルであろうとします。


## 2. Design Goal

### シンプル

* 不変オブジェクト
* 関数指向

### 暗黙的であるより明示的に

### Good old Java

* No XML, No Annotations
* Slightly opinionated


## 3. Overview

### コンポーネント

* Application
    * Config
* Router
    * Routers
* Handler
    * ErrorHandler
* Renderer
    * StringRenderer
    * OutputStreamRenderer
    * ThymeleafRenderer
    * JSON
* Utilities
    * UrlBuilder


## 4. Getting started

```java
class Application {

    private static Config config = Configs.builder()
                                          .port(8080)
                                          .build();

    private static Router router =
            (request) -> ((ctx) -> Response.body("<h1>hello, world</h1>"));

    public static void main(String[] args) {
        Abukuma.config(config())
               .router(router())
               .run();
    }
}
```
