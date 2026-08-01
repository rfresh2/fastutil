plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:4.13.2")

    rootProject.subprojects
        .filter { it.path.startsWith(":modules:") && it.childProjects.isEmpty() }
        .forEach { testImplementation(it) }
}

sourceSets.test {
    java.setSrcDirs(listOf(rootProject.file("test")))
}

tasks.compileTestJava {
    options.release.set(8)
}

tasks.test {
    useJUnit()
    maxHeapSize = "3g"
}
