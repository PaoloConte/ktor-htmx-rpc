plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.dokka)
}

allprojects {
    repositories {
        mavenCentral()
    }
}


dependencies {
    dokka(project(":lib"))
}

dokka {
    moduleName.set("kotlin-htmx-rpc")
}