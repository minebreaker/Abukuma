package rip.deadcode.abukuma3.collection.traverse;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;

class TraversalTest {

    @Test
    void test() {
        List<String> param = ImmutableList.of( "foo", "bar", "buz" );
        Traversal<List<String>, String> t = ( applicative, object ) ->
                object.stream().map( applicative ).collect( toList() );

        System.out.println( t.modify( String::toUpperCase, param ) );
    }
}
