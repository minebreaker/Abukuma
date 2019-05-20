package rip.deadcode.abukuma3.generator

import org.apache.velocity.Template
import org.apache.velocity.VelocityContext
import org.apache.velocity.app.VelocityEngine
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.TaskAction
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.Writer
import java.nio.file.Files
import java.nio.file.Path
import java.util.*


val velocity = VelocityEngine(Properties().apply {
    setProperty("resource.loaders", "cp")
    setProperty("resource.loader.cp.class", ClasspathResourceLoader::class.java.canonicalName)
})
val template: Template = velocity.getTemplate("view-class-template.vm")


@Suppress("unused")
open class GeneratorPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.tasks.create("generateDataClasses", GenerateDataClassTask::class.java)
        target.tasks.getByName("compileJava").dependsOn("generateDataClasses")
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
                .map { mapToModel(it) }
                .forEach { write(it, generatedSrcPath) }
    }
}

fun parse(file: File): Map<String, Any> {
    val yaml = Yaml()
    return file.reader().use {
        yaml.load(it)
    }
}

fun write(model: ViewClassOutput, generatedSrcPath: Path) {

    val destination = generatedSrcPath.resolve(model.`package`.replace('.', '/')).resolve(model.name + ".java")
    Files.createDirectories(destination.parent)
    Files.newBufferedWriter(destination).use {
        render(model, it)
    }
}

fun render(model: ViewClassOutput, writer: Writer) {

    val context = VelocityContext()
    context.put("m", model)
    template.merge(context, writer)
}
