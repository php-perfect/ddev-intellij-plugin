plugins {
    id("org.jetbrains.changelog") version "1.3.1"
    id("org.jetbrains.intellij") version "1.5.2"
    java

    id("org.sonarqube") version "3.3"
    jacoco
}

group = "de.php_perfect.intellij.ddev"
version = System.getenv("GIT_TAG_NAME") ?: "0.0.1-dev"

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
        changeNotes.set(provider { changelog.getOrNull(version.get())?.toHTML() })
    }
    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN") ?: "")
        privateKey.set(System.getenv("PRIVATE_KEY") ?: "")
        password.set(System.getenv("PRIVATE_KEY_PASSWORD") ?: "")
    }
    publishPlugin {
        token.set(System.getenv("JETBRAINS_TOKEN") ?: "")
        if (System.getenv("PUBLISH_CHANNEL") != null && System.getenv("PUBLISH_CHANNEL") != "") {
            channels.set(listOf(System.getenv("PUBLISH_CHANNEL")))
        }
    }
    runPluginVerifier {
        ideVersions.set(listOf("IC-2022.1.1", "PS-2022.1.1", "DB-2022.1.1"))
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
