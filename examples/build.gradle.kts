plugins {
    id("java")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(19))
    }
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("--enable-preview")
}
tasks.withType<Test> {
    jvmArgs("--enable-preview")
}
tasks.withType<JavaExec> {
    jvmArgs("--enable-preview")
}
