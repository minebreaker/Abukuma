package rip.deadcode.abukuma3.generator.renderer


val defaultImports = """
    import java.util.*;
    import java.util.function.*;
    import rip.deadcode.abukuma3.collection.*;
    import javax.annotation.Nullable;
    import com.google.common.collect.*;
""".trimIndent()

fun Any?.boolTrueIfNull() = this == null || this.toString().toBoolean()
fun Any?.boolFalseIfNull() = this != null && this.toString().toBoolean()
