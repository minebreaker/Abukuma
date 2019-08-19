package rip.deadcode.abukuma3.collection;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth8.assertThat;
import static rip.deadcode.izvestia.Core.expect;


class AbuPersistentListMultimapTest {

    @Test
    void testGetValue() {

        Multimap<String, String> params = ImmutableListMultimap.<String, String>builder()
                .putAll( "k1", "v1", "v2" )
                .build();
        AbuPersistentListMultimap<String, String> s = AbuPersistentListMultimap.create( params );

        assertThat( s.getValue( "k1" ) ).isEqualTo( "v1" );

        expect( () -> {
            s.getValue( "k2" );
        } ).throwsException( NoSuchElementException.class );

        expect( () -> {
            s.getValue( null );
        } ).throwsException( NullPointerException.class );
    }

    @Test
    void testGet() {

        Multimap<String, String> params = ImmutableListMultimap.<String, String>builder()
                .putAll( "k1", "v1", "v2" )
                .build();
        AbuPersistentListMultimap<String, String> s = AbuPersistentListMultimap.create( params );

        assertThat( s.get( "k1" ) ).containsExactly( "v1", "v2" ).inOrder();
        assertThat( s.get( "k2" ) ).isEmpty();

        expect( () -> {
            s.getValue( null );
        } ).throwsException( NullPointerException.class );
    }

    @Test
    void testMayGet() {

        Multimap<String, String> params = ImmutableListMultimap.<String, String>builder()
                .putAll( "k1", "v1", "v2" )
                .build();
        AbuPersistentListMultimap<String, String> s = AbuPersistentListMultimap.create( params );

        assertThat( s.mayGet( "k1" ) ).hasValue( "v1" );
        assertThat( s.mayGet( "k2" ) ).isEmpty();

        expect( () -> {
            s.mayGet( null );
        } ).throwsException( NullPointerException.class );
    }

    @Test
    void testAdd() {

        AbuPersistentListMultimap<String, String> e = AbuPersistentListMultimap.create();
        AbuPersistentListMultimap<String, String> s;

        assertThat( e.add( "k1", "v1" ) ).containsEntry( "k1", "v1" );
        assertThat( e.add( "k1", "v1" ).add( "k1", "v2" ) )
                .valuesForKey( "k1" ).containsExactly( "v1", "v2" ).inOrder();
        s = e.add( "k1", "v1" ).add( "k2", "v2" );
        assertThat( s ).containsEntry( "k1", "v1" );
        assertThat( s ).containsEntry( "k2", "v2" );

        expect( () -> {
            e.add( "k1", (String) null );
        } ).throwsException( NullPointerException.class );

        assertThat( e.add( "k1", ImmutableList.of( "v1" ) ) ).containsEntry( "k1", "v1" );
        assertThat( e.add( "k1", ImmutableList.of( "v1" ) ).add( "k1", ImmutableList.of( "v2" ) ) )
                .valuesForKey( "k1" ).containsExactly( "v1", "v2" );
        s = e.add( "k1", ImmutableList.of( "v1" ) ).add( "k2", ImmutableList.of( "v2" ) );
        assertThat( s ).containsEntry( "k1", "v1" );
        assertThat( s ).containsEntry( "k2", "v2" );

        expect( () -> {
            e.add( "k1", (List<String>) null );
        } ).throwsException( NullPointerException.class );
    }

    @Test
    void testSet() {

        AbuPersistentListMultimap<String, String> e = AbuPersistentListMultimap.create();
        AbuPersistentListMultimap<String, String> s;

        assertThat( e.set( "k1", "v1" ) ).containsEntry( "k1", "v1" );
        assertThat( e.set( "k1", "v1" ).set( "k1", "v2" ) )
                .valuesForKey( "k1" ).containsExactly( "v2" );
        s = e.set( "k1", "v1" ).set( "k2", "v2" );
        assertThat( s ).containsEntry( "k1", "v1" );
        assertThat( s ).containsEntry( "k2", "v2" );

        expect( () -> {
            e.set( "k1", (String) null );
        } ).throwsException( NullPointerException.class );

        assertThat( e.set( "k1", ImmutableList.of( "v1" ) ) ).containsEntry( "k1", "v1" );
        assertThat( e.set( "k1", ImmutableList.of( "v1" ) ).set( "k1", ImmutableList.of( "v2" ) ) )
                .valuesForKey( "k1" ).containsExactly( "v2" );
        s = e.set( "k1", ImmutableList.of( "v1" ) ).set( "k2", ImmutableList.of( "v2" ) );
        assertThat( s ).containsEntry( "k1", "v1" );
        assertThat( s ).containsEntry( "k2", "v2" );

        expect( () -> {
            e.set( "k1", (List<String>) null );
        } ).throwsException( NullPointerException.class );
    }

    @Test
    void testDelete() {

        assertThat( AbuPersistentListMultimap.create().add( "k1", "v1" ).delete( "k1" ) ).isEmpty();

        expect( () -> {
            AbuPersistentListMultimap.create().delete( "k1" );
        } ).throwsException( NoSuchElementException.class );

        expect( () -> {
            AbuPersistentListMultimap.create().delete( null );
        } ).throwsException( NullPointerException.class );
    }

    @Test
    void testDelegate() {

        assertThat( AbuPersistentListMultimap.create()
                                             .add( "k1", "v1" )
                                             .add( "k1", "v2" )
                                             .add( "k2", "v3" ) )
                .hasSize( 3 );
    }

    @Test
    void testMutable() {

        ListMultimap<String, String> param = AbuPersistentListMultimap.<String, String>create()
                .add( "k1", "v1" ).mutable();
        param.get( "k1" ).add( "v2" );

        assertThat( param ).containsExactly( "k1", "v1", "k1", "v2" );
    }
}
