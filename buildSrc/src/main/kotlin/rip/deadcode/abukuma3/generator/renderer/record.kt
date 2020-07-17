package rip.deadcode.abukuma3.generator.renderer

import rip.deadcode.abukuma3.generator.PropertyJavadoc
import rip.deadcode.abukuma3.generator.Record
import rip.deadcode.abukuma3.generator.RecordMethod
import rip.deadcode.abukuma3.generator.RecordProperty


fun renderRecordInterface(model: Record) =
    """
            package ${model.`interface`.`package`};
            
            ${defaultImports}
            
            ${model.imports.joinToString("\n") { "import ${it};" }}
            
            
            public interface ${model.`interface`.name}
            extends PersistentMap<String, Object> {
            
                ${model.properties.joinToString("\n") { renderRecordInterfaceProperty(model, it) }}
                
                ${model.methods.joinToString("\n") { renderRecordInterfaceMethod(it) }}
                
                ${renderRecordInterfacePredefinedMethods(model)}
            }
        """.trimIndent()

fun renderRecordInterfaceProperty(model: Record, property: RecordProperty) =
    """
            ${property.javadoc.getter()}
            ${annotateNullable(property)}
            public
            ${renderRecordInterfaceGetter(property)}
            
            ${property.javadoc.setter()}
            public ${model.`interface`.name} ${property.name}( ${property.type} ${property.name} );
        """.trimIndent()

fun renderRecordInterfaceGetter(property: RecordProperty) =
    when {
        property.getter != null -> {
            val g = property.getter
            "${g.type ?: property.type} ${g.name ?: property.name}( ${g.argument ?: ""} );"
        }
        property.optional -> {
            "Optional<${property.type}> ${property.name}();"
        }
        else -> {
            "${property.type} ${property.name}();"
        }
    }

fun renderRecordInterfaceMethod(method: RecordMethod) =
    if (method.`interface`)
        "public ${method.annotation ?: ""} ${method.type} ${method.name}( ${method.argument} );"
    else
        ""

fun renderRecordInterfacePredefinedMethods(model: Record) =
    """
        public static ${model.`interface`.name} cast( Map<String, Object> map ) {
            return ${model.name}.cast( map );
        }
    """.trimIndent()


fun renderRecord(model: Record) =
    """
            package ${model.`package`};
            
            ${defaultImports}
            import com.google.common.collect.*;
            
            import static com.google.common.base.Preconditions.checkNotNull;
            
            ${model.imports.joinToString("\n") { "import ${it};" }}
            
            
            public final class ${model.name} implements ${model.`interface`.`package`}.${model.`interface`.name} {
                
                ${renderConstructors(model)}
                
                ${renderCopy(model)}
                
                ${model.properties.joinToString("\n") { renderProperty(model, it) }}
                
                ${model.methods.joinToString("\n") { renderMethod(it) }}
                
                ${renderRecordMapOverride(model)}
                
                ${renderRecordPredefinedMethods(model)}
            }
        """.trimIndent()

fun renderConstructors(model: Record) =
    """
            ${if (model.constructor?.requiredArg == true) renderRequiredArgConstructor(model) else ""}
            
            ${renderAllArgConstructor(model)}
        """.trimIndent()

fun renderRequiredArgConstructor(model: Record) =
    """
            public ${model.name}(
                ${model.properties
        .filter {
            !it.nullable && !it.optional && it.default == null
        }
        .joinToString { annotateNullable(it) + " " + it.type + " " + it.name }}
            ) {
                ${model.properties
        .filter { !it.nullable && !it.optional }
        .joinToString("\n") {
            "this.${it.name} = ${it.default ?: checkingNotNull(it)};"
        }}
            }
        """.trimIndent()

fun renderAllArgConstructor(model: Record) =
    """
            public ${model.name}(
                ${model.properties.joinToString { annotateNullable(it) + " " + it.type + " " + it.name }}
            ) {
                ${model.properties.joinToString("\n") { "this.${it.name} = ${checkingNotNull(it)};" }}
            }
        """.trimIndent()


fun renderCopy(model: Record) =
    """
            private ${model.name} copy() {
                return new ${model.name}( ${model.properties.joinToString { it.name }} );
            }
        """.trimIndent()

fun renderProperty(model: Record, property: RecordProperty) =
    """
            ${if (property.nullable && !property.isPrimitive()) "@Nullable" else ""}
            private ${property.type} ${property.name};
            
            ${renderGetter(property)}
            
            ${renderSetter(model, property)}
        """.trimIndent()

fun renderGetter(property: RecordProperty) =
    when {
        property.getter != null -> {
            val g = property.getter
            """
                    @Override
                    public ${g.type ?: property.type} ${g.name ?: property.name}( ${g.argument ?: ""} ) {
                        ${g.implementation}
                    }
                """.trimIndent()
        }
        property.optional -> """
                @Override
                public Optional<${property.type}> ${property.name}() {
                    return Optional.ofNullable( ${property.name} );
                }
            """.trimIndent()
        else -> """
                @Override ${if (property.nullable && !property.isPrimitive()) "@Nullable" else ""}
                public ${property.type} ${property.name}() {
                    return ${property.name};
                }
            """.trimIndent()
    }

fun renderSetter(model: Record, property: RecordProperty) =
    """
            @Override
            public ${model.name} ${property.name}(
                ${if (property.nullable) "@Nullable" else ""} ${property.type} ${property.name}
            ) {
                ${if (!property.nullable && !property.isPrimitive()) "checkNotNull( ${property.name} );" else ""}
                ${model.name} copy = copy();
                copy.${property.name} = ${property.name};
                return copy;
            }
        """.trimIndent()

fun renderMethod(method: RecordMethod) =
    """
            ${if (method.`interface`) "@Override" else method.annotation ?: ""}
            public ${method.type} ${method.name}( ${method.argument} ) {
                ${method.implementation}
            }
        """.trimIndent()


fun renderRecordMapOverride(record: Record) =
    // number of properties
    """
            @Override public int size() {
                return ${record.properties.size};
            }
        """ +
            // always false (even if all properties are null, considers it has a key of the property with null value)
            """
            @Override public boolean isEmpty() {
                return false;
            }
            
            @Override public boolean containsKey( Object key ) {
                return ${record.properties.joinToString(" || ") { """Objects.equals( key, "${it.name}" )""" }};
            }
            
            @Override public boolean containsValue( Object value ) {
                return ${record.properties.joinToString(" || ") { """Objects.equals( value, this.${it.name} )""" }};
            }
            
            @Nullable @Override public Object get( Object key ) {
                if ${record.properties.joinToString(" else if ") {
                """
                        ( Objects.equals( key, "${it.name}" ) ) {
                            return ${it.name};
                    }
                     """.trimIndent()
            }} else {
                    return null;
                }
            }
            
            @Deprecated @Override public Object put( String key, Object value ) {
                throw new UnsupportedOperationException();
            }
            @Deprecated @Override public Object remove( Object key ) {
                throw new UnsupportedOperationException();
            }
            @Deprecated @Override public void putAll( Map<? extends String, ?> m ) {
                throw new UnsupportedOperationException();
            }
            @Deprecated @Override public void clear() {
                throw new UnsupportedOperationException();
            }
            
            private static final Set<String> keySet =
                    ImmutableSet.of( ${record.properties.joinToString { "\"" + it.name + "\"" }} );
            
            @Override public Set<String> keySet() {
                return keySet;
            }
            
            @Override public Collection<Object> values() {
                return ImmutableList.of( ${record.properties.joinToString { it.name }} );
            }
            
            @Override public Set<Entry<String, Object>> entrySet() {
                return ImmutableSet.of(
                        ${record.properties.joinToString { """Maps.immutableEntry( "${it.name}", ${it.name} )""" }}
                );
            }
            
            @Override public boolean equals( Object o ) {
                return o instanceof Map
                        && ( (Map) o ).size() == ${record.properties.size}
                        && ${record.properties.joinToString("&& ") {
                """Objects.equals( ${it.name}, ( (Map) o ).get( "${it.name}" ) )"""
            }};
            }
            
            @Override public int hashCode() {
                return this.entrySet().hashCode();
            }
            
            @Override public Optional<Object> mayGet( String key ) {
                if ${record.properties.joinToString(" else if ") {
                """
                        ( Objects.equals( key, "${it.name}" ) ) {
                            ${if (!it.nullable || it.isPrimitive()) {
                    """return Optional.of( ${it.name} );"""
                } else {
                    """return Optional.ofNullable( ${it.name} );"""
                }}
                    }
                     """.trimIndent()
            }} else {
                    return Optional.empty();
                }
            }
            
            @Override public PersistentMap<String, Object> set( String key, Object value ) {
                checkNotNull( key );
                return PersistentCollections.<String, Object>create()
                                            .merge( this )
                                            .set( key, value );
            }
            
            @Override public PersistentMap<String, Object> delete( String key ) {
                if ( containsKey( key ) ) {
                    return PersistentCollections.<String, Object>create()
                                                .merge( this )
                                                .delete( key );
                } else {
                    throw new NoSuchElementException( key );
                }
            }
            
            @Override public PersistentMap<String, Object> merge( Map<String, Object> map ) {
                return PersistentCollections.<String, Object>create()
                                            .merge( this )
                                            .merge( map );
            }
            
            @Override public Map<String, Object> mutable() {
                return new HashMap<>( this );
            }
        """.trimIndent()

fun renderRecordPredefinedMethods(model: Record) =
    """
        public static ${model.name} cast( Map<String, Object> map ) {
            
            ${model.properties.joinToString("\n") {
        """${it.type} ${it.name} = (${it.type}) map.get( "${it.name}" );"""
    }}
            
            return new ${model.name} ( ${model.properties.joinToString { it.name }} );
        }
    """.trimIndent()


private fun annotateNullable(property: RecordProperty) =
    if (!property.acceptsNull())
        "@Nullable"
    else
        ""

private fun checkingNotNull(property: RecordProperty) =
    if (!property.isPrimitive() && !property.acceptsNull())
        "checkNotNull( ${property.name} )"
    else
        property.name

private fun RecordProperty.isPrimitive() =
    when (this.type) {
        "boolean", "short", "int", "long", "float", "double", "char", "byte" -> true
        else -> false
    }

private fun RecordProperty.acceptsNull() = this.nullable || this.optional || this.isPrimitive()

private fun PropertyJavadoc?.getter() =
    if (this != null && this.getter != null) javadoc(this.getter) else ""

private fun PropertyJavadoc?.setter() =
    if (this != null && this.setter != null) javadoc(this.setter) else ""

private fun javadoc(doc: String) =
    doc.lines().joinToString("\n", prefix = "/**\n", postfix = "\n */") { " * ${it}" }


private val defaultImports = """
    import java.util.*;
    import java.util.function.*;
    import rip.deadcode.abukuma3.collection.*;
    import javax.annotation.Nullable;
""".trimIndent()
