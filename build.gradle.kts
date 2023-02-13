import org.jetbrains.intellij.tasks.ListProductsReleasesTask

plugins {
    id("org.jetbrains.changelog") version "2.0.0"
    id("org.jetbrains.intellij") version "1.13.0"
    java

    id("org.sonarqube") version "3.5.0.2730"
    jacoco
}

group = "de.php_perfect.intellij.ddev"
version = System.getenv("GIT_TAG_NAME") ?: "0.0.1-dev"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("io.sentry:sentry:6.11.0")

    val junitVersion = "5.9.2"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.9.2")
    testImplementation("org.mockito:mockito-core:5.1.1")
    testImplementation("org.assertj:assertj-core:3.24.2")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    type.set("IU")
    version.set("IU-223.7571.182") // https://www.jetbrains.com/de-de/idea/download/other.html
    plugins.add("com.intellij.database") // bundled
    plugins.add("org.jetbrains.plugins.terminal") // bundled
    plugins.add("com.jetbrains.plugins.webDeployment") // bundled
    plugins.add("org.jetbrains.plugins.remote-run") // bundled
    plugins.add("Docker") // bundled
    plugins.add("com.jetbrains.php:223.7571.182") // https://plugins.jetbrains.com/plugin/6610-php/versions
    plugins.add("org.jetbrains.plugins.phpstorm-remote-interpreter:223.7571.117") // https://plugins.jetbrains.com/plugin/7511-php-remote-interpreter/versions
    plugins.add("org.jetbrains.plugins.phpstorm-docker:223.7571.117") // https://plugins.jetbrains.com/plugin/8595-php-docker/versions
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

    listProductsReleases {
        types.set(listOf("IC", "IU", "PS", "WS", "DB"))
        releaseChannels.set(listOf(ListProductsReleasesTask.Channel.RELEASE))
    }

    /* Tests */
    test {
        ignoreFailures = System.getProperty("test.ignoreFailures")?.toBoolean() ?: false

        useJUnitPlatform()
    }

    jacocoTestReport {
        dependsOn(test)

        reports {
            xml.required.set(true)
        }
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
