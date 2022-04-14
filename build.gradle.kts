plugins {
    id("org.jetbrains.intellij") version "1.5.2"
    java

    id("org.sonarqube") version "3.3"
    jacoco
}

group = "de.php_perfect.intellij.ddev"
version = "1.0-ALPHA-2"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version.set("2022.1")
    type.set("IU")
    plugins.add("com.jetbrains.php:221.5080.224")
    plugins.add("org.jetbrains.plugins.phpstorm-remote-interpreter:221.5080.169")
    plugins.add("org.jetbrains.plugins.remote-run")
    plugins.add("org.jetbrains.plugins.phpstorm-docker:221.5080.169")
    plugins.add("com.intellij.database")
    plugins.add("Docker:221.5080.126")
    updateSinceUntilBuild.set(false)
}
tasks {
    patchPluginXml {
        changeNotes.set(
            """
            v1.0.0:
            <ul>
              <li>Initial release of the plugin</li>
            </ul>
            """.trimIndent()
        )
    }
    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN") ?: "")
        privateKey.set(System.getenv("PRIVATE_KEY") ?: "")
        password.set(System.getenv("PRIVATE_KEY_PASSWORD") ?: "")
    }
    publishPlugin {
        channels.set(if (System.getenv("PUBLISH_CHANNEL") != null && System.getenv("PUBLISH_CHANNEL") != "") listOf(System.getenv("PUBLISH_CHANNEL")) else listOf())
        token.set(System.getenv("JETBRAINS_TOKEN") ?: "")
    }
}

/* Tests */
tasks.test {
    ignoreFailures = System.getProperty("test.ignoreFailures")?.toBoolean() ?: false

    useJUnitPlatform()
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        xml.required.set(true)
    }
}

/* SonarCloud */
tasks.sonarqube {
    dependsOn(tasks.build, tasks.jacocoTestReport)

    sonarqube {
        properties {
            property("sonar.projectKey", "php-perfect_ddev-intellij-plugin")
            property("sonar.organization", "php-perfect")
            property("sonar.host.url", "https://sonarcloud.io/")
        }
    }
}
