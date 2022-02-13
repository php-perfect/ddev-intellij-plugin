plugins {
    id("org.jetbrains.intellij") version "1.3.1"
    java
}

group = "de.php_perfect.intellij.ddev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.8.9")
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
