plugins {
    id("application")
    id("antlr")
}

group = "org.example"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    antlr("org.antlr:antlr4:4.11.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.1")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainModule.set("de.uulm.compiler.app")
    mainClass.set("de.uulm.buehler.julian.compiler.Main")
}

tasks.generateGrammarSource {
    arguments = arguments + listOf("-visitor")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
