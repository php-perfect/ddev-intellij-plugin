import org.jetbrains.changelog.Changelog
import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.intellij.platform.gradle.tasks.VerifyPluginTask

fun properties(key: String) = providers.gradleProperty(key)
fun environment(key: String) = providers.environmentVariable(key)

plugins {
    id("org.jetbrains.changelog") version "2.2.0"
    id("org.jetbrains.intellij.platform.migration") version "2.0.0-beta4"
    id("java")

    id("org.sonarqube") version "5.0.0.4638"
    id("jacoco")
}

group = properties("pluginGroup").get()
version = System.getenv("GIT_TAG_NAME") ?: "0.0.1-dev"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("io.sentry:sentry:7.9.0")

    val junitVersion = "5.10.2"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.10.2")
    testImplementation("org.mockito:mockito-core:5.12.0")
    testImplementation("org.assertj:assertj-core:3.25.3")

    intellijPlatform {
        phpstorm(properties("platformVersion"))
        pluginVerifier()
        zipSigner()
        instrumentationTools()
        testFramework(TestFrameworkType.Platform.JUnit4)

        bundledPlugins(
            "com.intellij.database",
            "org.jetbrains.plugins.terminal",
            "com.jetbrains.plugins.webDeployment",
            "com.jetbrains.plugins.webDeployment",
            "org.jetbrains.plugins.remote-run",
            "Docker",
            "NodeJS",
            "org.jetbrains.plugins.node-remote-interpreter"
        )

        /**
         * @link https://plugins.jetbrains.com/plugin/6610-php/versions
         * @link https://plugins.jetbrains.com/plugin/7511-php-remote-interpreter/versions
         * @link https://plugins.jetbrains.com/plugin/8595-php-docker/versions
         * */
        plugins(
            "com.jetbrains.php:241.14494.240",
            "org.jetbrains.plugins.phpstorm-remote-interpreter:241.14494.158",
            "org.jetbrains.plugins.phpstorm-docker:241.14494.158"
        )
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

intellijPlatform {
    pluginConfiguration {
        name = properties("pluginName")
        changeNotes.set(provider {
            changelog.getOrNull(version.toString())
                ?.let { changelog.renderItem(it, Changelog.OutputType.HTML) }
        })
    }

    publishing {
        token.set(environment("JETBRAINS_TOKEN"))
        if (System.getenv("PUBLISH_CHANNEL") != null && System.getenv("PUBLISH_CHANNEL") != "") {
            channels.set(listOf(System.getenv("PUBLISH_CHANNEL")))
        }
    }

    signing {
        certificateChain.set(environment("CERTIFICATE_CHAIN"))
        privateKey.set(environment("PRIVATE_KEY"))
        password.set(environment("PRIVATE_KEY_PASSWORD"))
    }

    verifyPlugin {
        ignoredProblemsFile = file("ignoredProblems.txt")
        failureLevel.set(VerifyPluginTask.FailureLevel.NONE)
        ides {
            ide(IntelliJPlatformType.PhpStorm, "2024.1")
            ide(IntelliJPlatformType.WebStorm, "2024.1")
            ide(IntelliJPlatformType.DataGrip, "2024.1")
            ide(IntelliJPlatformType.IntellijIdeaUltimate, "2024.1")
        }
    }
}

tasks {
    wrapper {
        gradleVersion = properties("gradleVersion").get()
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
