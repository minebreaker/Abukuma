package rip.deadcode.abukuma3.service;

import rip.deadcode.abukuma3.route.RoutingResult;

@FunctionalInterface
public interface ContentService<T> {

    public RoutingResult feed(Context context, T target);

}
