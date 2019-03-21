package rip.deadcode.abukuma3;


import java.util.function.Supplier;


public interface Registry {

    public <T> T get( Class<T> cls );

    public <T> T get( Class<T> cls, String name );

    public <T> Registry setSingleton( Class<T> cls, T instance );

    public <T> Registry set( Class<T> cls, Supplier<? extends T> supplier );

    public <T> Registry set( Class<T> cls, String name, Supplier<? extends T> supplier );
}
