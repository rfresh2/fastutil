plugins {
    id("fastutil")
}

sourceSets.main {
    java {
        setSrcDirs(getFastutilSourceDirs())

        include(getCoreIncludes())
    }
}

sourceSets.test {
    java {
        include(
            "$PKG/ArraysTest.java",
            "$PKG/HashCommonTest.java",
        )
    }
}

tasks.jar {
    manifest.attributes["Automatic-Module-Name"] = "fastutil.core"
}
