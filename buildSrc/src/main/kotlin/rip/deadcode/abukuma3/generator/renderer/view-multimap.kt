package rip.deadcode.abukuma3.generator.renderer


fun renderViewMultimapInterface(model: ViewMultimap) =
    """
        package ${model.`interface`.`package`};
        
        ${defaultImports}

        ${model.imports.joinToString("\n") { "import ${it};" }}

        public interface ${model.`interface`.name}
        extends PersistentMultimapView<String, ${model.genericType}, ${model.`interface`.name}> {
        
            ${model.properties.joinToString("\n") { renderViewMultimapInterfaceProperty(model, it) }}
        
            ${renderViewMultimapInterfacePredefinedMethods(model)}
        
            ${model.`interface`.code ?: ""}
        }
        
    """.trimIndent()

fun renderViewMultimapInterfaceProperty(model: ViewMultimap, property: ViewMultimapProperty) =
    """
        public Optional<${model.genericType}> ${property.name}();
        
        public ${model.`interface`.name} ${property.name}( ${model.genericType} value );
    """.trimIndent()

fun renderViewMultimapInterfacePredefinedMethods(model: ViewMultimap) =
    """
        public static ${model.`interface`.name} create() {
            return ${model.name}.create();
        }
        
        public static ${model.`interface`.name} cast( Multimap<String, ${model.genericType}> map ) {
            return ${model.name}.cast( map );
        }
    """.trimIndent()

fun renderViewMultimap(model: ViewMultimap) =
    """
        package ${model.`package`};
        
        ${defaultImports}
        
        ${model.imports.joinToString("\n") { "import ${it};" }}
        
        public final class ${model.name}
        extends AbstractPersistentMultimap<String, ${model.genericType}, ${model.`interface`.name}>
        implements ${model.`interface`.name} {
        
            ${renderConstructors(model)}
        
            @Override protected final ${model.name} constructor( Envelope<String, ${model.genericType}> delegate ) {
                return new ${model.name}( delegate );
            }
        
            ${model.properties.joinToString("\n") { renderViewMultimapProperty(model, it) }}
        
            ${renderViewMultimapPredefinedMethods(model)}
        }
    """.trimIndent()

fun renderConstructors(model: ViewMultimap) =
    """
        private ${model.name}() {
            super();
        }
        
        private ${model.name}( Multimap<String, ${model.genericType}> delegate ) {
            super( delegate );
        }
        
        private ${model.name}( Envelope<String, ${model.genericType}> delegate ) {
            super( delegate );
        }
    """.trimIndent()

// TODO should implement `mayGetValue` on Multimap and use it
fun renderViewMultimapProperty(model: ViewMultimap, property: ViewMultimapProperty) =
    """
        @Override public Optional<${model.genericType}> ${property.name}() {
            return Optional.ofNullable( getValue( ${property.key} ) ); 
        }
        
        @Override public ${model.`interface`.name} ${property.name}( ${model.genericType} value ) {
            return set( ${property.key}, value );
        }
    """.trimIndent()

fun renderViewMultimapPredefinedMethods(model: ViewMultimap) =
    """
        public static ${model.`interface`.name} create() {
            return new ${model.name}();
        }
        
        public static ${model.`interface`.name} cast( Multimap<String, ${model.genericType}> delegate ) {
            return new ${model.name}( delegate );
        }
    """.trimIndent()
