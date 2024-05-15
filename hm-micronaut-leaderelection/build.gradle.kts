import org.gradle.internal.execution.history.changes.ExecutionStateChanges.incremental

val micronautVersion="4.4.2"

plugins {
    kotlin("kapt")
    id("io.micronaut.library") version "4.3.8"
}

dependencies {
    runtimeOnly("org.yaml:snakeyaml")
    kapt("io.micronaut:micronaut-inject")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    testImplementation("io.micronaut.test:micronaut-test-junit5")
}

micronaut {
    version.set(micronautVersion)
    testRuntime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("no.nav.hm.micronaut.leaderelection.*")
    }
}

val githubUser: String? by project
val githubPassword: String? by project

publishing {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/navikt/hm-micronaut")
            credentials {
                username = githubUser
                password = githubPassword
            }
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {

            pom {
                name.set("hm-micronaut-leaderelection")
                description.set("hm micronaut leaderlection")
                url.set("https://github.com/navikt/hm-micronaut")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                scm {
                    connection.set("scm:git:https://github.com/navikt/hm-micronaut.git")
                    developerConnection.set("scm:git:https://github.com/navikt/hm-micronaut.git")
                    url.set("https://github.com/navikt/hm-micronaut")
                }
            }
            from(components["java"])
        }
    }
}
