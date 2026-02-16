plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "com.example"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(11)
}

dependencies {
    implementation(libs.ktor.server.core)
}