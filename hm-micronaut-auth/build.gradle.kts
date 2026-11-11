val micronautVersion="5.0.2"

plugins {
    id("com.google.devtools.ksp") version "2.3.7"
    id("io.micronaut.library") version "5.0.2"
}

dependencies {
    runtimeOnly("org.yaml:snakeyaml")
    ksp("io.micronaut:micronaut-inject")
    ksp("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    // security
    implementation("io.micronaut.security:micronaut-security-jwt")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive")
    ksp("io.micronaut.security:micronaut-security-annotations")
    testImplementation("io.micronaut.test:micronaut-test-junit5")
}

micronaut {
    version.set(micronautVersion)
    testRuntime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("no.nav.hm.micronaut.auth.*")
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
                name.set("hm-micronaut-auth")
                description.set("hm micronaut auth")
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
