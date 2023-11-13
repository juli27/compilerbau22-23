plugins {
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("de.uulm:lib")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

//repositories {
//    mavenCentral()
//}

//dependencies {
//    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
//    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
//}

//tasks.getByName<Test>("test") {
//    useJUnitPlatform()
//}
