plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

gradlePlugin {
    val fastutilPlugin by plugins.creating {
        id = "com.github.rfresh2.fastutil-plugin"
        implementationClass = "FastutilSettingsPlugin"
        displayName = "Fastutil plugin"
        description = "A no-op plugin to expose classes from the plugin"
        tags.set(listOf("gradle", "fastutil"))
    }
}
