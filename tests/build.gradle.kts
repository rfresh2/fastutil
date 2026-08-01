import org.gradle.api.tasks.compile.JavaCompile

plugins {
    java
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:4.13.2")

    rootProject.subprojects
        .filter {
            it.path.startsWith(":modules:") &&
                it.childProjects.isEmpty() &&
                it.name != "fastutil-bom"
        }
        .forEach { testImplementation(it) }
}

sourceSets.test {
    java.setSrcDirs(listOf(rootProject.file("test")))
}

tasks.withType<JavaCompile>().configureEach {
    options.release = 8
}

tasks.test {
    useJUnit()
    maxHeapSize = "3g"
}
