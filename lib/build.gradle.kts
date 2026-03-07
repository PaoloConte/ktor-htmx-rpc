import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.dokka)
}

group = "io.paoloconte"
version = "1.0"

kotlin {
    jvmToolchain(11)
}
repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.core)
}


configure<com.vanniktech.maven.publish.MavenPublishBaseExtension> {
    configure(
        KotlinJvm(
            javadocJar = JavadocJar.Dokka("dokkaGeneratePublicationHtml"),
            sourcesJar = true,
        )
    )

    pom {
        inceptionYear.set("2026")
        url.set("https://github.com/PaoloConte/ktor-htmx-rpc")
        licenses {
            license {
                name.set("MIT License")
                url.set("https://github.com/PaoloConte/ktor-htmx-rpc/blob/main/LICENSE")
                distribution.set("https://github.com/PaoloConte/ktor-htmx-rpc/blob/main/LICENSE")
            }
        }
        developers {
            developer {
                id.set("PaoloConte")
                name.set("Paolo Conte")
                url.set("https://github.com/PaoloConte/")
            }
        }
        scm {
            url.set("https://github.com/PaoloConte/ktor-htmx-rpc")
            connection.set("scm:git:git@github.com:PaoloConte/ktor-htmx-rpc.git")
            developerConnection.set("scm:git:ssh://git@github.com/PaoloConte/ktor-htmx-rpc.git")
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()
}
