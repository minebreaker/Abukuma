package rip.deadcode.abukuma3.collection.traverse;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class QPathTest {

    @Test
    public void experiment() {

        Map<String, Map<String, String>> obj = new HashMap<>();
        Map<String, String> foo = obj.get("foo");
        String bar = foo.get("bar");

//        Map<String, Map<String, String>> obj = new HashMap<>();
//
//        Map<String, String> foo = obj.get("foo");
//        Map<String, String> newFoo = obj.assoc("bar", "buz");
//        Map<String, Map<String, String>> newObj = newFoo.assoc("foo", newFoo);
    }
}
