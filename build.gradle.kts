plugins {
    id("org.jetbrains.intellij") version "1.4.0"
    java

    id("org.sonarqube") version "3.3"
    jacoco
}

group = "de.php_perfect.intellij.ddev"
version = "1.0-ALPHA-1"

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
    version.set("2021.3.3")
    type.set("IU")
    plugins.add("com.jetbrains.php:213.6777.58")
    plugins.add("org.jetbrains.plugins.phpstorm-remote-interpreter:213.5744.125")
    plugins.add("org.jetbrains.plugins.remote-run")
    plugins.add("org.jetbrains.plugins.phpstorm-docker:213.5744.125")
    plugins.add("com.intellij.database")
    plugins.add("Docker:213.6461.58")
    updateSinceUntilBuild.set(false)
}
tasks {
    runIde {
        ideDir.set(File("C:\\Users\\nl\\AppData\\Local\\JetBrains\\Toolbox\\apps\\PhpStorm\\ch-0\\213.7172.28"))
    }
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
