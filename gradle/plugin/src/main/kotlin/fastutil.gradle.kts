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
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
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
}
