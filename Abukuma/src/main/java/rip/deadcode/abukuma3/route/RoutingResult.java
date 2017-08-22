package rip.deadcode.abukuma3.route;

import rip.deadcode.abukuma3.service.Context;

import java.util.Optional;

public interface RoutingResult {

    public boolean routeMatched();

    public Optional<Context> result();

    static final class NonMatch implements RoutingResult {

        private static final NonMatch instance = new NonMatch();

        private NonMatch() {}

        @Override
        public boolean routeMatched() {
            return false;
        }

        @Override
        public Optional<Context> result() {
            return Optional.empty();
        }

    }

    public static RoutingResult notMatched() {
        return NonMatch.instance;
    }

}
