plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

group = "com.example"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("app.ApplicationKt")
}

dependencies {
    implementation(project(":lib"))
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
}

tasks {
    kotlin {
        compilerOptions {
            optIn.add("io.ktor.utils.io.ExperimentalKtorApi")
            freeCompilerArgs.add("-Xcontext-parameters")
        }
    }
}
