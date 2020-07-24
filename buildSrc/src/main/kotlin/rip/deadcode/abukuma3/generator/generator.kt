package rip.deadcode.abukuma3.generator

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.TaskAction
import org.yaml.snakeyaml.Yaml
import rip.deadcode.abukuma3.generator.renderer.Record
import rip.deadcode.abukuma3.generator.renderer.ViewMultimap
import rip.deadcode.abukuma3.generator.renderer.mapToRecord
import rip.deadcode.abukuma3.generator.renderer.mapToViewMultimap
import rip.deadcode.abukuma3.generator.renderer.renderRecord
import rip.deadcode.abukuma3.generator.renderer.renderRecordInterface
import rip.deadcode.abukuma3.generator.renderer.renderViewMultimap
import rip.deadcode.abukuma3.generator.renderer.renderViewMultimapInterface
import java.io.File
import java.nio.file.Files
import java.nio.file.Path


@Suppress("unused")
open class GeneratorPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val task = target.tasks.create("generateDataClasses", GenerateDataClassTask::class.java)
        target.tasks.getByName("compileJava").dependsOn(task)
        task.dependsOn("processResources")
    }
}

open class GenerateDataClassTask : DefaultTask() {

    private val javaPlugin = project.convention.getPlugin(JavaPluginConvention::class.java)
    private val sourceSet = javaPlugin.sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME)

    var input: List<File> = sourceSet.resources.srcDirs.map { it.resolve("META-INF/generator") }
        @InputFiles get

    @Suppress("UnstableApiUsage")  // Beta but no alternative
    var output: File = sourceSet.output.generatedSourcesDirs.singleFile
        @OutputDirectory get

    @TaskAction
    fun run() {

        val definitionFiles = sourceSet.resources.filter { input.contains(it.parentFile) }
        val generatedSrcPath = output.toPath()

        definitionFiles
            .map { parse(it) }
            .forEach {
                when {
                    it["type"] == "record" -> write(mapToRecord(it), generatedSrcPath)
                    it["type"] == "view-multimap" -> write(mapToViewMultimap(it), generatedSrcPath)
                    else -> throw RuntimeException()
                }
            }
    }
}

fun parse(file: File): Map<String, Any> {
    val yaml = Yaml()
    return file.reader().use {
        yaml.load(it)
    }
}

fun write(model: Record, generatedSrcPath: Path) {

    // Class
    val classDestination = generatedSrcPath.resolve(model.`package`.asPath()).resolve(model.name + ".java")
    write(renderRecord(model), classDestination)

    // Interface
    val interfaceDestination =
        generatedSrcPath.resolve(model.`interface`.`package`.asPath()).resolve(model.`interface`.name + ".java")
    write(renderRecordInterface(model), interfaceDestination)
}

fun write(model: ViewMultimap, generatedSrcPath: Path) {

    // Class
    val classDestination = generatedSrcPath.resolve(model.`package`.asPath()).resolve(model.name + ".java")
    write(renderViewMultimap(model), classDestination)

    // Interface
    val interfaceDestination =
        generatedSrcPath.resolve(model.`interface`.`package`.asPath()).resolve(model.`interface`.name + ".java")
    write(renderViewMultimapInterface(model), interfaceDestination)
}

fun write(string: String, destination: Path) {

    Files.createDirectories(destination.parent)
    Files.newBufferedWriter(destination).use {
        it.write(string)
    }
}


private fun String.asPath(): String {
    return this.replace(".", System.getProperty("file.separator"))
}
