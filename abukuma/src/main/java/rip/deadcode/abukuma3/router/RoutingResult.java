package rip.deadcode.abukuma3.router;

import com.google.common.collect.Multimap;
import rip.deadcode.abukuma3.handler.Handler;

import java.util.Map;


public interface RoutingResult {

    public Handler handler();

    public Multimap<String, String> queryParameters();

    public Map<String, String> pathParameters();
}
