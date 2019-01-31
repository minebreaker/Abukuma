# Session Extension

TODO

```java
public interface Session {
    public Optional<Object> get(Object key);
    public Session put(Object key, Object value);
    public String getSessionId();
    public boolean isSessionEnabled();
    public Session renew();
}

public interface ContextInterceptor {
    public Context provide(Context context);
}

public interface SessionProvider extends ContextInterceptor {
    public Context provide(Context context);
}

public interface AbuSession {
    public static AbuSession getInstance() {}
    public SessionManager getSessionController();
}

public interface SessionManager {

    public String get(String sessionId, Object key);
    public void put(String sessionId, Object key, Object value);

    public String publishNewSessionId();
    public boolean isSessionEnabled(String sessionId);
    public String renewSessionId(String oldSessionId);
    public void removeSessionId(String sessionId);
}



public interface GuiceInterceptor extends CntextInterceptor {
    public Context provide(Context context);
}

@SessionScoped
public class FooService extends GuiceService {

    @Inject
    private Context context;

    @Override
    public Context execute() {
        return context;
    }

}
```
