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
    if (map == null)
        RecordConstructor(requiredArg = false, allArg = true)
    else RecordConstructor(
        map["requiredArg"].boolFalseIfNull(),
        map["allArg"].boolTrueIfNull()
    )

fun mapRecordProperty(map: Map<String, Any>): RecordProperty {
    val optional = map["optional"].boolFalseIfNull()
    val nullable = map["nullable"].boolFalseIfNull()
    val javadoc = when (val javadocObj = map["javadoc"]) {
        is Map<*, *> -> {
            @Suppress("UNCHECKED_CAST")
            javadocObj as Map<String, Any>
            RecordPropertyJavadoc(
                javadocObj["getter"].toString(),
                javadocObj["setter"].toString()
            )
        }
        is String -> RecordPropertyJavadoc(
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
        when (val listObj = map["list"]) {
            true -> RecordPropertyList(
                add = true,
                addMultiple = true,
                plural = map["name"].toString() + "s"  // I know this is dumb and you should always set "plural" property
            )
            is Map<*, *> -> RecordPropertyList(
                add = listObj["add"].boolTrueIfNull(),
                addMultiple = listObj["addMultiple"].boolTrueIfNull(),
                plural = listObj["name"]?.toString() ?: map["name"].toString() + "s"
            )
            else -> null
        },
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
    map["interface"].boolTrueIfNull(),
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
    val requiredArg: Boolean,
    val allArg: Boolean
//        , val staticFactory:
)

data class RecordProperty(
    val name: String,
    val type: String,
    val nullable: Boolean,
    val optional: Boolean,
    val list: RecordPropertyList?,
    val default: String?,
    val getter: RecordPropertyAccessor?,
    val javadoc: RecordPropertyJavadoc?
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

data class RecordPropertyJavadoc(
    val getter: String?,
    val setter: String?
)

data class RecordPropertyList(
    val add: Boolean,
    val addMultiple: Boolean,
    val plural: String
)
