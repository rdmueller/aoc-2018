
plugins {
    id("org.jetbrains.kotlin.jvm").version("1.3.10")
    application
}

repositories {
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    testImplementation("junit:junit:4.12")
    testImplementation("io.kotlintest:kotlintest-assertions:3.1.10")
}

application {
    mainClassName = "MainKt"
}
