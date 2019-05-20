package rip.deadcode.abukuma3.generator


fun mapToModel(map: Map<String, Any>): ViewClassOutput {
    return ViewClassOutput(
            map["package"].toString(),
            map["name"].toString()
    )
}

data class ViewClassOutput(
        val `package`: String,
        val name: String
)
