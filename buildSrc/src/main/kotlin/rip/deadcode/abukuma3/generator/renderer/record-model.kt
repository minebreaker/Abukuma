package rip.deadcode.abukuma3.generator.renderer


fun mapToRecord(map: Map<String, Any>): Record {
    @Suppress("UNCHECKED_CAST")
    return Record(
        map["package"].toString(),
        map["name"].toString(),
        map["import"] as List<String>? ?: listOf(),
        mapRecordInterface(map["interface"] as Map<String, Any>),
        mapRecordConstructor(map["constructor"] as Map<String, Any>?),
        (map["property"] as List<Map<String, Any>>).map {
            mapRecordProperty(
                it
            )
        },
        (map["method"] as List<Map<String, Any>>?)?.map {
            mapRecordMethod(
                it
            )
        } ?: listOf()
    )
}

fun mapRecordInterface(map: Map<String, Any>): RecordInterface = RecordInterface(
    map["package"].toString(),
    map["name"].toString()
)

fun mapRecordConstructor(map: Map<String, Any>?): RecordConstructor? =
    if (map == null) null else RecordConstructor(
        map["noArg"].bool(),
        map["requiredArg"].bool(),
        map["allArg"].bool()
    )

fun mapRecordProperty(map: Map<String, Any>): RecordProperty {
    val optional = map["optional"].bool()
    val nullable = map["nullable"].bool()
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
            mapRecordPropertyAccessor(getterObj)
        }
        else -> null
    }

    return RecordProperty(
        map["name"].toString(),
        map["type"].toString(),
        nullable,
        optional,
        map["default"]?.toString(),
        getter,
        javadoc
    )
}

fun mapRecordPropertyAccessor(map: Map<String, Any>): RecordPropertyAccessor =
    RecordPropertyAccessor(
        map["name"]?.toString(),
        map["type"]?.toString(),
        map["argument"]?.toString(),
        map["implementation"].toString()
    )

private fun mapRecordMethod(map: Map<String, Any>): RecordMethod = RecordMethod(
    map["name"].toString(),
    map["type"].toString(),
    map["interface"] != "false",
    map["annotation"]?.toString(),
    map["argument"].toString(),
    map["implementation"].toString(),
    map["javadoc"]?.toString()
)

data class Record(
    val `package`: String,
    val name: String,
    val imports: List<String>,
    val `interface`: RecordInterface,
    val constructor: RecordConstructor?,
    val properties: List<RecordProperty>,
    val methods: List<RecordMethod>
)

data class RecordInterface(
    val `package`: String,
    val name: String
)

data class RecordConstructor(
    val noArg: Boolean,
    val requiredArg: Boolean,
    val allArg: Boolean
//        , val staticFactory:
)

data class RecordProperty(
    val name: String,
    val type: String,
    val nullable: Boolean,
    val optional: Boolean,
    val default: String?,
    val getter: RecordPropertyAccessor?,
    val javadoc: PropertyJavadoc?
)

data class RecordPropertyAccessor(
    val name: String?,
    val type: String?,
    val argument: String?,
    val implementation: String
)

data class RecordMethod(
    val name: String,
    val type: String,
    val `interface`: Boolean,
    val annotation: String?,
    val argument: String,
    val implementation: String,
    val javadoc: String?
)

data class PropertyJavadoc(
    val getter: String?,
    val setter: String?
)


fun Any?.bool() = this != null && this.toString().toBoolean()
