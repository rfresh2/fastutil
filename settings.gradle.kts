pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
    includeBuild("gradle/plugin")
    plugins {
        id("fastutil")
    }
}

plugins {
    id("com.github.rfresh2.fastutil-plugin")
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

fun addModule(name: String) {
    include(":modules:$name")
}


addModule("fastutil-bom")
addModule("fastutil-all")
addModule("core")
addModule("io")
include(":tests")

for (type in Type.TYPES) {
    addModule("commons:$type-common")
    if (type != Type.REFERENCE && type != Type.BOOLEAN) { // No reference queue implementation
        addModule("queues:$type-queues")
    }
    addModule("sets:$type-sets")
    addModule("big-lists:$type-big-lists")

    if (type != Type.BOOLEAN) {
        for (mapType in Type.TYPES) {
            addModule("maps:$type-$mapType-maps")
        }
    }
}
