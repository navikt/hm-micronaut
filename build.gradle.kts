import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.21"
    id("java")
    id("maven-publish")
}


subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("java")
        plugin("maven-publish")
    }

    val jvmTarget = "17"
    val junitJupiterVersion = "5.9.1"
    val logbackClassicVersion = "1.4.5"
    val logbackEncoderVersion = "7.2"
    val awaitilityVersion = "4.2.0"
    val jacksonVersion = "2.14.1"
    val mockkVersion = "1.13.4"
    val kotestVersion = "5.5.5"

    group = "com.github.navikt"
    version = properties["version"] ?: "local-build"

    dependencies {
        api("ch.qos.logback:logback-classic:$logbackClassicVersion")
        api("net.logstash.logback:logstash-logback-encoder:$logbackEncoderVersion")
        api("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
        api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.6.4")
        testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
        testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
        testImplementation("io.mockk:mockk:$mockkVersion")
        testImplementation("io.micronaut.test:micronaut-test-kotest5")
        testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
        testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
        testImplementation("org.awaitility:awaitility:$awaitilityVersion")
    }

    java {
        sourceCompatibility = JavaVersion.toVersion(jvmTarget)
        targetCompatibility = JavaVersion.toVersion(jvmTarget)

        withSourcesJar()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = jvmTarget
    }

    tasks.named<KotlinCompile>("compileTestKotlin") {
        kotlinOptions.jvmTarget = jvmTarget
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("skipped", "failed")
            showExceptions = true
            showStackTraces = true
            showCauses = true
            exceptionFormat = TestExceptionFormat.FULL
            showStandardStreams = true
        }
    }

    tasks.withType<Wrapper> {
        gradleVersion = "8.5"
    }

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://packages.confluent.io/maven/")
    }
}
