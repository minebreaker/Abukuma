package rip.deadcode.abukuma3.generator


fun mapToViewClass(map: Map<String, Any>): ViewClass {
    @Suppress("UNCHECKED_CAST")
    return ViewClass(
            map["package"].toString(),
            map["name"].toString(),
            map["import"] as List<String>? ?: listOf(),
            mapToViewClassInterface(map["interface"] as Map<String, Any>),
            mapViewClassConstructor(map["constructor"] as Map<String, Any>?),
            (map["property"] as List<Map<String, Any>>).map { mapViewClassProperty(it) },
            (map["method"] as List<Map<String, Any>>?)?.map { mapViewClassMethod(it) } ?: listOf()
    )
}

fun mapToViewClassInterface(map: Map<String, Any>): ViewClassInterface {
    return ViewClassInterface(
            map["package"].toString(),
            map["name"].toString()
    )
}

fun mapViewClassConstructor(map: Map<String, Any>?): ViewClassConstructor? {
    return if (map == null) null else ViewClassConstructor(
            map["noArg"].bool(),
            map["requiredArg"].bool(),
            map["allArg"].bool()
    )
}

fun mapViewClassProperty(map: Map<String, Any>): ViewClassProperty {
    val optional = map["optional"].bool()
    val nullable = optional || map["nullable"].bool()
    val javadoc = when (val javadocObj = map["javadoc"]) {
        is Map<*, *> -> {
            @Suppress("UNCHECKED_CAST")
            javadocObj as Map<String, Any>
            PropertyJavadoc(
                    javadocObj["getter"].toString(),
                    javadocObj["setter"].toString()
            )
        }
        is String -> PropertyJavadoc(
                javadocObj,
                "@see #${map["name"]}()"
        )
        else -> null
    }

    val getter = when (val getterObj = map["getter"]) {
        is Map<*, *> -> {
            @Suppress("UNCHECKED_CAST")
            getterObj as Map<String, Any>
            mapViewClassPropertyAccessor(getterObj)
        }
        else -> null
    }

    return ViewClassProperty(
            map["name"].toString(),
            map["type"].toString(),
            nullable,
            optional,
            map["default"]?.toString(),
            getter,
            javadoc
    )
}

fun mapViewClassPropertyAccessor(map: Map<String, Any>): ViewClassPropertyAccessor =
        ViewClassPropertyAccessor(
                map["name"]?.toString(),
                map["type"]?.toString(),
                map["argument"]?.toString(),
                map["implementation"].toString()
        )

fun mapViewClassMethod(map: Map<String, Any>): ViewClassMethod {
    return ViewClassMethod(
            map["name"].toString(),
            map["type"].toString(),
            map["argument"].toString(),
            map["implementation"].toString(),
            map["javadoc"]?.toString()
    )
}

data class ViewClass(
        val `package`: String,
        val name: String,
        val imports: List<String>,
        val `interface`: ViewClassInterface,
        val constructor: ViewClassConstructor?,
        val properties: List<ViewClassProperty>,
        val methods: List<ViewClassMethod>
)

data class ViewClassInterface(
        val `package`: String,
        val name: String
)

data class ViewClassConstructor(
        val noArg: Boolean,
        val requiredArg: Boolean,
        val allArg: Boolean
//        , val staticFactory:
)

data class ViewClassProperty(
        val name: String,
        val type: String,
        val nullable: Boolean,
        val optional: Boolean,
        val default: String?,
        val getter: ViewClassPropertyAccessor?,
        val javadoc: PropertyJavadoc?
)

data class ViewClassPropertyAccessor(
        val name: String?,
        val type: String?,
        val argument: String?,
        val implementation: String
)

data class ViewClassMethod(
        val name: String,
        val type: String,
        val argument: String,
        val implementation: String,
        val javadoc: String?
)

data class PropertyJavadoc(
        val getter: String?,
        val setter: String?
)


fun Any?.bool() = this != null && this.toString().toBoolean()
