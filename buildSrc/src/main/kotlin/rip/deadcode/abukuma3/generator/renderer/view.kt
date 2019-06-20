package rip.deadcode.abukuma3.generator.renderer

import rip.deadcode.abukuma3.generator.PropertyJavadoc
import rip.deadcode.abukuma3.generator.ViewClass
import rip.deadcode.abukuma3.generator.ViewClassMethod
import rip.deadcode.abukuma3.generator.ViewClassProperty


fun renderViewClassInterface(model: ViewClass) =
        """
            package ${model.`interface`.`package`};
            
            ${defaultImports}
            
            ${model.imports.joinToString("\n") { "import ${it};" }}
            
            
            public interface ${model.`interface`.name} {
            
                ${model.properties.joinToString("\n") { renderViewClassInterfaceProperty(model, it) }}
                
                ${model.methods.joinToString("\n") { renderViewClassInterfaceMethod(it) }}
            }
        """.trimIndent()

fun renderViewClassInterfaceProperty(model: ViewClass, property: ViewClassProperty) =
        """
            ${property.javadoc.getter()}
            ${nullable(property)}
            public
            ${renderViewClassInterfaceGetter(property)}
            
            ${property.javadoc.setter()}
            public ${model.`interface`.name} ${property.name}( ${property.type} ${property.name} );
        """.trimIndent()

fun renderViewClassInterfaceGetter(property: ViewClassProperty) =
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

fun renderViewClassInterfaceMethod(method: ViewClassMethod) =
        "public ${method.type} ${method.name}( ${method.argument} );"


fun renderViewClass(model: ViewClass) =
        """
            package ${model.`package`};
            
            ${defaultImports}
            
            import static com.google.common.base.Preconditions.checkNotNull;
            
            ${model.imports.joinToString("\n") { "import ${it};" }}
            
            
            public final class ${model.name} implements ${model.`interface`.`package`}.${model.`interface`.name} {
                
                ${renderConstructors(model)}
                
                ${renderCopy(model)}
                
                ${model.properties.joinToString("\n") { renderProperty(model, it) }}
                
                ${model.methods.joinToString("\n") { renderMethod(it) }}
            }
        """.trimIndent()

fun renderConstructors(model: ViewClass) =
        """
            ${if (model.constructor?.requiredArg == true) renderRequiredArgConstructor(model) else ""}
            
            ${renderAllArgConstructor(model)}
        """.trimIndent()

fun renderRequiredArgConstructor(model: ViewClass) =
        """
            public ${model.name}(
                ${model.properties
                .filter { !it.nullable && it.default == null }
                .joinToString { it.type + " " + it.name }}
            ) {
                ${model.properties
                .filter { !it.nullable }
                .joinToString("\n") {
                    "this.${it.name} = ${it.default ?: checkNotNull(it)};"
                }}
            }
        """.trimIndent()

fun renderAllArgConstructor(model: ViewClass) =
        """
            public ${model.name}(
                ${model.properties.joinToString { it.type + " " + it.name }}
            ) {
                ${model.properties.joinToString("\n") { "this.${it.name} = ${checkNotNull(it)};" }}
            }
        """.trimIndent()


fun renderCopy(model: ViewClass) =
        """
            private ${model.name} copy() {
                return new ${model.name}( ${model.properties.joinToString { it.name }} );
            }
        """.trimIndent()

fun renderProperty(model: ViewClass, property: ViewClassProperty) =
        """
            ${if (property.nullable && !property.isPrimitive()) "@Nullable" else ""}
            private ${property.type} ${property.name};
            
            ${renderGetter(property)}
            
            ${renderSetter(model, property)}
        """.trimIndent()

fun renderGetter(property: ViewClassProperty) =
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
                @Override ${nullable(property)}
                public ${property.type} ${property.name}() {
                    return ${property.name};
                }
            """.trimIndent()
        }

fun renderSetter(model: ViewClass, property: ViewClassProperty) =
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

fun renderMethod(method: ViewClassMethod) =
        """
            @Override public ${method.type} ${method.name}( ${method.argument} ) {
                ${method.implementation}
            }
        """.trimIndent()


private fun nullable(property: ViewClassProperty) =
        if (property.nullable && !property.isPrimitive() && !property.optional)
            "@Nullable"
        else
            ""

private fun checkNotNull(property: ViewClassProperty) =
        if (!property.isPrimitive())
            "checkNotNull( ${property.name} )"
        else
            property.name

private fun ViewClassProperty.isPrimitive() =
        when (this.type) {
            "boolean", "short", "int", "long", "float", "double", "char", "byte" -> true
            else -> false
        }

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
