plugins {
    `java-library`
    `maven-publish`
}

group = "org.glavo"
version = "0.1.1".let {
    if (System.getenv("JITPACK") == "true") it
    else "$it-SNAPSHOT"
}

java {
    withSourcesJar()
}

tasks.compileJava {
    options.release.set(16)
}

tasks.withType<GenerateModuleMetadata>().configureEach {
    enabled = false
}

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            version = project.version.toString()
            artifactId = project.name
            from(components["java"])

            pom {
                licenses {
                    license {
                        name.set("Apache-2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("glavo")
                        name.set("Glavo")
                        email.set("zjx001202@gmail.com")
                    }
                }
            }
        }
    }
}