package rip.deadcode.abukuma3.collection;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth8.assertThat;
import static rip.deadcode.izvestia.Core.expect;


@SuppressWarnings( "ConstantConditions" )
class PersistentListImplTest {

    @Test
    void testMayGet() {

        PersistentList<String> param = PersistentListImpl.<String>create().addFirst( "foo" );
        assertThat( param.mayGet( 0 ) ).hasValue( "foo" );
        assertThat( param.mayGet( -1 ) ).isEmpty();
        assertThat( param.mayGet( 1 ) ).isEmpty();
    }

    @Test
    void testFirst() {

        PersistentList<String> param = PersistentListImpl.<String>create().addFirst( "bar" ).addFirst( "foo" );
        assertThat( param ).containsExactly( "foo", "bar" ).inOrder();
        assertThat( param.first() ).isEqualTo( "foo" );

        expect( () -> {
            PersistentListImpl.create().first();
        } ).throwsException( IndexOutOfBoundsException.class );

        expect( () -> {
            PersistentListImpl.create().addFirst( null );
        } ).throwsException( NullPointerException.class );
    }

    @Test
    void testLast() {

        PersistentList<String> param = PersistentListImpl.<String>create().addLast( "foo" ).addLast( "bar" );
        assertThat( param ).containsExactly( "foo", "bar" ).inOrder();
        assertThat( param.last() ).isEqualTo( "bar" );

        expect( () -> {
            PersistentListImpl.create().last();
        } ).throwsException( IndexOutOfBoundsException.class );

        expect( () -> {
            PersistentListImpl.create().addFirst( null );
        } ).throwsException( NullPointerException.class );
    }

    @Test
    void testInsert() {

        PersistentList<String> param = PersistentListImpl.<String>create().addLast( "foo" ).addLast( "bar" );

        assertThat( param.insert( 0, "buz" ) ).containsExactly( "buz", "foo", "bar" ).inOrder();
        assertThat( param.insert( 1, "buz" ) ).containsExactly( "foo", "buz", "bar" ).inOrder();
        assertThat( param.insert( 2, "buz" ) ).containsExactly( "foo", "bar", "buz" ).inOrder();

        expect( () -> {
            param.insert( -1, "buz" );
        } ).throwsException( IndexOutOfBoundsException.class );

        expect( () -> {
            param.insert( 3, "buz" );
        } ).throwsException( IndexOutOfBoundsException.class );

        expect( () -> {
            param.insert( 0, null );
        } ).throwsException( NullPointerException.class );
    }

    @Test
    void testReplace() {

        PersistentList<String> param = PersistentListImpl.<String>create().addLast( "foo" ).addLast( "bar" );

        assertThat( param.replace( 0, "buz" ) ).containsExactly( "buz", "bar" ).inOrder();
        assertThat( param.replace( 1, "buz" ) ).containsExactly( "foo", "buz" ).inOrder();

        expect( () -> {
            param.replace( -1, "buz" );
        } ).throwsException( IndexOutOfBoundsException.class );

        expect( () -> {
            param.replace( 2, "buz" );
        } ).throwsException( IndexOutOfBoundsException.class );

        expect( () -> {
            param.replace( 0, null );
        } ).throwsException( NullPointerException.class );
    }

    @Test
    void testDelete() {

        PersistentList<String> param = PersistentListImpl.<String>create().addLast( "foo" ).addLast( "bar" );
        assertThat( param.delete( 0 ) ).containsExactly( "bar" );
        assertThat( param.delete( 1 ) ).containsExactly( "foo" );

        expect( () -> {
            param.delete( -1 );
        } ).throwsException( IndexOutOfBoundsException.class );

        expect( () -> {
            param.delete( 2 );
        } ).throwsException( IndexOutOfBoundsException.class );
    }

    @Test
    void testConcat() {

        PersistentList<String> param = PersistentListImpl.<String>create()
                .addLast( "foo" )
                .concat( ImmutableList.of( "bar" ) );

        assertThat( param ).containsExactly( "foo", "bar" ).inOrder();
    }

    @Test
    void testMutable() {

        List<String> param = PersistentListImpl.<String>create().addLast( "foo" ).mutable();
        param.add( "bar" );

        assertThat( param ).containsExactly( "foo", "bar" ).inOrder();
    }
}
