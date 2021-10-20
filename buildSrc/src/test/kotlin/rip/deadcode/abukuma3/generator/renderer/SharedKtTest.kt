package rip.deadcode.abukuma3.generator.renderer

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test


class SharedKtTest {

    @Test
    fun testBoolTrueIfNull() {
        assertThat("true".boolTrueIfNull()).isTrue()
        assertThat("false".boolTrueIfNull()).isFalse()
        assertThat(null.boolTrueIfNull()).isTrue()
    }

    @Test
    fun testBoolFalseIfNull() {
        assertThat("true".boolFalseIfNull()).isTrue()
        assertThat("false".boolFalseIfNull()).isFalse()
        assertThat(null.boolFalseIfNull()).isFalse()
    }
}
