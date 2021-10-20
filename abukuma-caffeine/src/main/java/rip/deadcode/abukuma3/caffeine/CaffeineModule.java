package rip.deadcode.abukuma3.caffeine;

import rip.deadcode.abukuma3.Module;
import rip.deadcode.abukuma3.Registry;

import javax.cache.Caching;
import javax.cache.spi.CachingProvider;


public final class CaffeineModule implements Module {

    @Override public Registry apply( Registry registry ) {

        CachingProvider cachingProvider = Caching.getCachingProvider(
                "com.github.benmanes.caffeine.jcache.spi.CaffeineCachingProvider" );

        return registry.setSingleton( CachingProvider.class, cachingProvider );
    }
}
