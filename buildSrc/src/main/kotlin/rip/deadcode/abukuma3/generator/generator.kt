package rip.deadcode.abukuma3.generator

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.TaskAction
import org.yaml.snakeyaml.Yaml
import rip.deadcode.abukuma3.generator.renderer.renderViewClass
import rip.deadcode.abukuma3.generator.renderer.renderViewClassInterface
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

    @TaskAction
    fun run() {

        val javaPlugin = project.convention.getPlugin(JavaPluginConvention::class.java)
        val sourceSet = javaPlugin.sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME)

        val definitionRoots = sourceSet.resources.srcDirs.map { it.resolve("META-INF/generator") }
        val definitionFiles = sourceSet.resources.filter { definitionRoots.contains(it.parentFile) }
        @Suppress("UnstableApiUsage")  // Beta but no alternative
        val generatedSrcPath = sourceSet.output.generatedSourcesDirs.singleFile.toPath()

        definitionFiles
                .map { parse(it) }
                .map { mapToViewClass(it) }
                // TODO Occasional build failure. Maybe specifying file output will solve that?
                .forEach { write(it, generatedSrcPath) }
    }
}

fun parse(file: File): Map<String, Any> {
    val yaml = Yaml()
    return file.reader().use {
        yaml.load(it)
    }
}

fun write(model: ViewClass, generatedSrcPath: Path) {

    // Class
    val classDestination = generatedSrcPath.resolve(model.`package`.asPath()).resolve(model.name + ".java")
    write(renderViewClass(model), classDestination)

    // Interface
    val interfaceDestination =
            generatedSrcPath.resolve(model.`interface`.`package`.asPath()).resolve(model.`interface`.name + ".java")
    write(renderViewClassInterface(model), interfaceDestination)
}

fun write(string: String, destination: Path) {

    Files.createDirectories(destination.parent)
    Files.writeString(destination, string)
}


private fun String.asPath(): String {
    return this.replace(".", System.getProperty("file.separator"))
}
