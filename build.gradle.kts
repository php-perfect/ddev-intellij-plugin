import org.jetbrains.intellij.tasks.ListProductsReleasesTask

plugins {
    id("org.jetbrains.changelog") version "1.3.1"
    id("org.jetbrains.intellij") version "1.6.0"
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
    implementation("io.sentry:sentry:5.7.4")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.mockito:mockito-core:4.6.1")
    testImplementation("org.assertj:assertj-core:3.23.1")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    type.set("IU")
    version.set("IU-221.5787.30") // https://www.jetbrains.com/de-de/idea/download/other.html
    plugins.add("com.intellij.database") // bundled
    plugins.add("org.jetbrains.plugins.terminal") // bundled
    plugins.add("com.jetbrains.plugins.webDeployment") // bundled
    plugins.add("org.jetbrains.plugins.remote-run") // bundled
    plugins.add("Docker") // bundled
    plugins.add("com.jetbrains.php:221.5787.33") // https://plugins.jetbrains.com/plugin/6610-php/versions
    plugins.add("org.jetbrains.plugins.phpstorm-remote-interpreter:221.5787.20") // https://plugins.jetbrains.com/plugin/7511-php-remote-interpreter/versions
    plugins.add("org.jetbrains.plugins.phpstorm-docker:221.5787.20") // https://plugins.jetbrains.com/plugin/8595-php-docker/versions
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
            html.required.set(true)
            xml.required.set(true)
        }

        classDirectories.setFrom("build/classes/java/main")
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
