package rip.deadcode.abukuma3.generator.renderer


fun mapToViewMultimap(map: Map<String, Any>): ViewMultimap =
    @Suppress("UNCHECKED_CAST")
    ViewMultimap(
        map["package"].toString(),
        map["name"].toString(),
        map["import"] as List<String>? ?: listOf(),
        map["genericType"]?.toString() ?: "Any",
        mapViewMultimapInterface(map["interface"] as Map<String, Any>),
        if (map["property"] != null) {
            (map["property"] as List<Map<String, Any>>).map {
                mapViewMultimapProperty(
                    it
                )
            }
        } else listOf()
    )

fun mapViewMultimapInterface(map: Map<String, Any>): ViewMultimapInterface = ViewMultimapInterface(
    map["package"].toString(),
    map["name"].toString(),
    map["code"]?.toString()
)

fun mapViewMultimapProperty(map: Map<String, Any>): ViewMultimapProperty = ViewMultimapProperty(
    map["name"].toString(),
    map["key"].toString()
)


data class ViewMultimap(
    val `package`: String,
    val name: String,
    val imports: List<String>,
    val genericType: String,
    val `interface`: ViewMultimapInterface,
    val properties: List<ViewMultimapProperty>
)

data class ViewMultimapInterface(
    val `package`: String,
    val name: String,
    val code: String?
)

data class ViewMultimapProperty(
    val name: String,
    val key: String
)
