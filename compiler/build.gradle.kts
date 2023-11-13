plugins {
    application
    antlr
}

group = "org.example"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    antlr("org.antlr:antlr4:4.11.1")

    implementation("info.picocli:picocli:4.7.0")
    annotationProcessor("info.picocli:picocli-codegen:4.7.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.1")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainModule.set("de.uulm.compiler.app")
    mainClass.set("de.uulm.compiler.Main")
}

tasks.generateGrammarSource {
    arguments = arguments + listOf("-listener", "-visitor")
}

tasks.test {
    useJUnitPlatform()
}
