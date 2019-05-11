package rip.deadcode.abukuma3.collection;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth8.assertThat;


class AbuPersistentListTest {

    @Test
    void test() {

        AbuPersistentList<String> list = AbuPersistentList.create();
        assertThat( list ).isEmpty();

        list = list.addLast( "bar" ).addLast( "buz" ).addFirst( "foo" );
        assertThat( list ).containsExactly( "foo", "bar", "buz" ).inOrder();
        assertThat( list.first() ).hasValue( "foo" );
        assertThat( list.last() ).hasValue( "buz" );
    }
}
