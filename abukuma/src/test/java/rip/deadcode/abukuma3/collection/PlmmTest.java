package rip.deadcode.abukuma3.collection;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

class PlmmTest {

    @Test
    void test() {

        Plmm m = new Plmm().add("foo", "bar").add("foo", "buz");

        assertThat(m.get("foo")).containsExactly("bar", "buz").inOrder();
        assertThat(m.keySet()).hasSize(1);
        assertThat(m.keys()).hasCount("foo", 2);
    }
}
