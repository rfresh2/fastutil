plugins {
    id("java-library")
    id("maven-publish")
    id("signing")
}

java {
    withJavadocJar()
    withSourcesJar()

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
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
            from(components["java"])
            version = project.version.toString()
            System.getenv("PUBLISH_VERSION")?.let {
                version = it
            }
            pom {
                packaging = "jar"
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

tasks.javadoc {
    options {
        (this as CoreJavadocOptions).addStringOption("Xdoclint:none", "-quiet")
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.compileJava {
    options.encoding = Charsets.UTF_8.name();
    dependsOn(rootProject.tasks.named("generateFastutilSources"))
}

tasks.javadoc {
    dependsOn(rootProject.tasks.named("generateFastutilSources"))
}

tasks.named("sourcesJar") {
    dependsOn(rootProject.tasks.named("generateFastutilSources"))
}
