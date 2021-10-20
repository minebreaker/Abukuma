package rip.deadcode.abukuma3.internal.utils;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;


// TODO accept nulls?
public abstract class Try<T, E extends Exception> {

    private Try() {}

    public static <T> Try<T, Exception> of( T value ) {
        return new Success<>( value );
    }

    public static <T, E extends Exception> Try<T, Exception> except( E exception ) {
        return new Failure<>( exception );
    }

    public static <T, E extends Exception> Try<T, Exception> possibly( CheckedSupplier<? extends T, E> supplier ) {
        try {
            return new Success<>( supplier.get() );
        } catch ( Exception e ) {
            return new Failure<>( e );
        }
    }

    @SuppressWarnings( "unchecked" )
    public static <T, E extends Exception> Try<T, E> possibly( CheckedSupplier<? extends T, E> supplier, Class<E> typeToken ) {
        try {
            return new Success<>( supplier.get() );
        } catch ( Exception e ) {
            if ( typeToken.isInstance( e ) ) {
                return new Failure<>( (E) e );  // safe to cast
            } else {
                throw new RuntimeException( e );
            }
        }
    }

    public abstract T get();

    public abstract T orElse( T other );

    public abstract T orElse( Function<? super E, ? extends T> other );

    public abstract boolean isPresent();

    @CanIgnoreReturnValue
    public abstract Try<T, E> ifPresent( Consumer<? super T> consumer );

    public abstract <U> Try<U, E> map( Function<? super T, U> mapper );

    public abstract <U> Try<U, E> flatMap( Function<? super T, Try<U, E>> mapper );

    public abstract Optional<T> asOptional();

    private static final class Success<T, E extends Exception> extends Try<T, E> {

        private final T value;

        private Success( T value ) {
            this.value = requireNonNull( value );
        }

        @Override public T get() {
            return value;
        }

        @Override public T orElse( T other ) {
            return value;
        }

        @Override public T orElse( Function<? super E, ? extends T> other ) {
            return value;
        }

        @Override public boolean isPresent() {
            return true;
        }

        @Override public Try<T, E> ifPresent( Consumer<? super T> consumer ) {
            consumer.accept( this.value );
            return this;
        }

        @Override public <U> Try<U, E> map( Function<? super T, U> mapper ) {
            return new Success<>( mapper.apply( value ) );
        }

        @Override public <U> Try<U, E> flatMap( Function<? super T, Try<U, E>> mapper ) {
            return mapper.apply( value );
        }

        @Override public Optional<T> asOptional() {
            return Optional.of( value );
        }
    }

    private static final class Failure<T, E extends Exception> extends Try<T, E> {

        private final E e;

        private Failure( E exception ) {
            this.e = requireNonNull( exception );
        }

        @Override public T get() {
            RuntimeException throwing = new NoSuchElementException( "Failed Try" );
            throwing.initCause( e );
            throw throwing;
        }

        @Override public T orElse( T other ) {
            return other;
        }

        @Override public T orElse( Function<? super E, ? extends T> other ) {
            return other.apply( e );
        }

        @Override public boolean isPresent() {
            return false;
        }

        @Override public Try<T, E> ifPresent( Consumer<? super T> consumer ) {
            return this;
        }

        @SuppressWarnings( "unchecked" )
        @Override public <U> Try<U, E> map( Function<? super T, U> mapper ) {
            return (Try<U, E>) this;  // Don't care U because it'll be never used.
        }

        @SuppressWarnings( "unchecked" )
        @Override public <U> Try<U, E> flatMap( Function<? super T, Try<U, E>> mapper ) {
            return (Try<U, E>) this;
        }

        @Override public Optional<T> asOptional() {
            return Optional.empty();
        }
    }
}
