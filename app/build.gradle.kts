plugins {
    java
    application
    kotlin("jvm") version "1.9.0"
    id("org.openjfx.javafxplugin").version("0.0.13")

}

group = "edu.austral.dissis.chess"
version = "1.0.0"

repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/austral-ingsis/chess-ui")
        credentials {
            println("GitHub User: " + project.property("gpr.user"))
            println("GitHub Token: " +  project.property("gpr.token"))

            username = System.getenv("GITHUB_USER") ?: project.property("gpr.user") as String
            password = System.getenv("GITHUB_TOKEN") ?: project.property("gpr.token") as String
        }
    }
    maven {
        url = uri("https://maven.pkg.github.com/austral-ingsis/simple-client-server")
        credentials {
            username = System.getenv("GITHUB_USER") ?: project.property("gpr.user") as String
            password = System.getenv("GITHUB_TOKEN") ?: project.property("gpr.token") as String
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.yaml:snakeyaml:2.0")
    implementation("edu.austral.dissis.chess:chess-ui:2.0.1")
    implementation("edu.austral.dissis.chess:simple-client-server:1.2.0")
}

javafx {
    version = "18"
    modules = listOf("javafx.graphics")
}

application {
    mainClass.set("edu.austral.dissis.chess.AppKt")
}
