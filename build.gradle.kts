plugins {
    id("org.jetbrains.intellij") version "1.3.1"
    java
    jacoco
}

group = "de.php_perfect.intellij.ddev"
version = "1.0-SNAPSHOT"

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
    version.set("2021.3.2")
    type.set("IU")
    plugins.add("com.jetbrains.php:213.6777.58")
    plugins.add("org.jetbrains.plugins.phpstorm-remote-interpreter:213.5744.125")
    plugins.add("org.jetbrains.plugins.remote-run")
    plugins.add("org.jetbrains.plugins.phpstorm-docker:213.5744.125")
    plugins.add("com.intellij.database")
    plugins.add("Docker:213.6461.58")
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
}
tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
