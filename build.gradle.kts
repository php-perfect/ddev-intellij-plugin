import org.jetbrains.changelog.Changelog
import org.jetbrains.intellij.tasks.ListProductsReleasesTask

plugins {
    id("org.jetbrains.changelog") version "2.1.2"
    id("org.jetbrains.intellij") version "1.15.0"
    java

    id("org.sonarqube") version "4.3.0.3225"
    jacoco
}

group = "de.php_perfect.intellij.ddev"
version = System.getenv("GIT_TAG_NAME") ?: "0.0.1-dev"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("io.sentry:sentry:6.28.0")

    val junitVersion = "5.10.0"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.10.0")
    testImplementation("org.mockito:mockito-core:5.5.0")
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
    version.set("IU-232.8660.185") // https://www.jetbrains.com/de-de/idea/download/other.html
    plugins.add("com.intellij.database") // bundled
    plugins.add("org.jetbrains.plugins.terminal") // bundled
    plugins.add("com.jetbrains.plugins.webDeployment") // bundled
    plugins.add("org.jetbrains.plugins.remote-run") // bundled
    plugins.add("Docker") // bundled
    plugins.add("NodeJS") // bundled
    plugins.add("org.jetbrains.plugins.node-remote-interpreter") // bundled
    plugins.add("com.jetbrains.php:232.8660.185") // https://plugins.jetbrains.com/plugin/6610-php/versions
    plugins.add("org.jetbrains.plugins.phpstorm-remote-interpreter:232.8660.142") // https://plugins.jetbrains.com/plugin/7511-php-remote-interpreter/versions
    plugins.add("org.jetbrains.plugins.phpstorm-docker:232.8660.142") // https://plugins.jetbrains.com/plugin/8595-php-docker/versions
}

tasks {
    patchPluginXml {
        changeNotes.set(provider {
            changelog.getOrNull(version.get())
                ?.let { changelog.renderItem(it, Changelog.OutputType.HTML) }
        })
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
