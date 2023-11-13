plugins {
    id("java-library")
}

group = "de.uulm"
version = "0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
