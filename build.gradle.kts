import org.jetbrains.changelog.Changelog
import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType
import org.jetbrains.intellij.platform.gradle.TestFrameworkType

fun properties(key: String) = providers.gradleProperty(key)
fun environment(key: String) = providers.environmentVariable(key)

plugins {
    id("org.jetbrains.changelog") version "2.2.0"
    id("org.jetbrains.intellij.platform") version "2.0.1"
    id("java")
    id("org.sonarqube") version "5.1.0.4882"
    id("jacoco")
}

val pluginVersion = environment("GIT_TAG_NAME").orElse("0.0.1-dev").get()
group = properties("pluginGroup").get()
version = pluginVersion

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("io.sentry:sentry:7.14.0")

    val junitVersion = "5.10.2"
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.10.2")
    testImplementation("org.mockito:mockito-core:5.12.0")
    testImplementation("org.assertj:assertj-core:3.26.0")

    intellijPlatform {
        phpstorm(properties("platformVersion"))
        pluginVerifier("1.307")
        zipSigner()
        instrumentationTools()
        testFramework(TestFrameworkType.Platform)

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
            "com.jetbrains.php:242.21829.15",
            "org.jetbrains.plugins.phpstorm-remote-interpreter:242.20224.159",
            "org.jetbrains.plugins.phpstorm-docker:242.20224.159"
        )
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

intellijPlatform {
    autoReload.set(true)

    pluginConfiguration {
        name = properties("pluginName")
        changeNotes.set(provider {
            changelog.getOrNull(pluginVersion)
                ?.let { changelog.renderItem(it, Changelog.OutputType.HTML) }
        })
    }

    publishing {
        token.set(environment("JETBRAINS_TOKEN"))
        if (environment("PUBLISH_CHANNEL").orNull != null) {
            channels.set(listOf(environment("PUBLISH_CHANNEL").get()))
        }
    }

    signing {
        certificateChain.set(environment("CERTIFICATE_CHAIN"))
        privateKey.set(environment("PRIVATE_KEY"))
        password.set(environment("PRIVATE_KEY_PASSWORD"))
    }

    pluginVerification {
        ignoredProblemsFile = file("ignoredProblems.txt")
        ides {
            ide(IntelliJPlatformType.PhpStorm, properties("platformVersion").get())
            ide(IntelliJPlatformType.WebStorm, properties("platformVersion").get())
            ide(IntelliJPlatformType.DataGrip, properties("platformVersion").get())
            ide(IntelliJPlatformType.IntellijIdeaUltimate, properties("platformVersion").get())
        }
    }

    changelog {
        groups.empty()
        repositoryUrl = properties("pluginRepositoryUrl")
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
