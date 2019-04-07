package rip.deadcode.abukuma3;


import java.util.function.UnaryOperator;


@FunctionalInterface
public interface Module extends UnaryOperator<Registry> {

    @Override
    public Registry apply( Registry registry );
}
