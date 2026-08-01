import java.util.*
import org.gradle.api.tasks.PathSensitivity

plugins {
    base
}

val props = Properties()
file("build.properties").inputStream().use { props.load(it) }

val release = property("release") as String

val generatedFastutilRoot = layout.buildDirectory.dir("generated/sources/fastutil")
val generatedFastutilSources = generatedFastutilRoot.map { it.dir("src") }
val cCompiler = providers.environmentVariable("CC").orElse("cc")

tasks.register<Exec>("generateFastutilSources") {
    group = "build"
    description = "Generates fastutil Java sources using the upstream Makefile"

    inputs.files(
        layout.projectDirectory.file("makefile"),
        layout.projectDirectory.file("build.properties"),
        layout.projectDirectory.file("gencsource.sh"),
        fileTree("drv") {
            include("**/*.drv")
        },
    ).withPathSensitivity(PathSensitivity.RELATIVE)
    inputs.property("cCompiler", cCompiler)

    outputs.dir(generatedFastutilSources)
    outputs.cacheIf("fastutil source generation is deterministic") { true }

    workingDir(layout.projectDirectory)
    commandLine(
        "make",
        "--jobs=1",
        "-f", "makefile",
        "dirs",
        "sources",
        "GEN_SRCDIR=${generatedFastutilSources.get().asFile.absolutePath}",
        "CC=${cCompiler.get()} -I${generatedFastutilRoot.get().asFile.absolutePath}",
        "SHELL=/bin/bash",
        ".SHELLFLAGS=-o pipefail -c",
        "TEST=",
        "ASSERTS=",
        "MINIMAL_TYPES=",
        "SMALL_TYPES=1",
        "IO_TYPES=1",
    )

    doFirst {
        check(generatedFastutilSources.get().asFile.deleteRecursively()) {
            "Could not clear generated fastutil sources"
        }
    }

    doLast {
        generatedFastutilSources.get().asFile.walkBottomUp()
            .filter { it.isFile && it.extension in setOf("c", "h") }
            .forEach { file ->
                check(file.delete()) { "Could not delete intermediate file $file" }
            }
    }
}

allprojects {
    group = "com.github.rfresh2.fastutil"
    version = props.getProperty("version") + if(release.toBoolean()) "" else "-SNAPSHOT"
}
