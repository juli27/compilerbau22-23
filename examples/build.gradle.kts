plugins {
    id("java")
}

dependencies {
    implementation("de.uulm:lib")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
