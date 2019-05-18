package rip.deadcode.abukuma3.collection;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;


class AbuPersistentListTest {

    @Test
    void test() {

        PersistentList<String> list = AbuPersistentList.create();
        assertThat( list ).isEmpty();

        list = list.addLast( "bar" ).addLast( "buz" ).addFirst( "foo" );
        assertThat( list ).containsExactly( "foo", "bar", "buz" ).inOrder();
        assertThat( list.first() ).isEqualTo( "foo" );
        assertThat( list.last() ).isEqualTo( "buz" );
    }
}
