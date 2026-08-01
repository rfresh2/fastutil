plugins {
    `java-platform`
    `maven-publish`
    signing
}

dependencies {
    constraints {
        api(project(":modules:fastutil-all"))
        api(project(":modules:core"))
        api(project(":modules:io"))

        for (type in Type.TYPES) {
            api(project(":modules:commons:$type-common"))
            api(project(":modules:sets:$type-sets"))
            api(project(":modules:big-lists:$type-big-lists"))

            if (type != Type.REFERENCE && type != Type.BOOLEAN) {
                api(project(":modules:queues:$type-queues"))
            }

            if (type != Type.BOOLEAN) {
                for (valueType in Type.TYPES) {
                    api(project(":modules:maps:$type-$valueType-maps"))
                }
            }
        }
    }
}

publishing {
    repositories {
        maven {
            name = "vc"
            url = uri("https://maven.2b2t.vc/releases")
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }

    publications {
        create<MavenPublication>("maven") {
            from(components["javaPlatform"])
            version = project.version.toString()
            System.getenv("PUBLISH_VERSION")?.let {
                version = it
            }

            pom {
                packaging = "pom"
                url.set("https://github.com/rfresh2/fastutil")

                scm {
                    connection.set("scm:git:git://github.com/rfresh2/fastutil.git")
                    developerConnection.set("scm:git:ssh://github.com/rfresh2/fastutil.git")
                    url.set("https://github.com/rfresh2/fastutil")
                }

                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        name.set("rfresh2")
                        organization.set("rfresh2")
                        organizationUrl.set("https://github.com/rfresh2")
                    }
                }
            }
        }
    }
}
