plugins {
    java
}

group = "nl.codebulb"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val lwjglVersion = "3.2.3"
val lwjglNatives = "natives-windows"

dependencies {
    implementation (
        platform("org.lwjgl:lwjgl-bom:$lwjglVersion")
    )
    implementation("org.lwjgl:lwjgl")
    implementation("org.lwjgl:lwjgl-glfw")
    implementation("org.lwjgl:lwjgl-opengl")
    implementation("org.lwjgl:lwjgl-openal")
    implementation("org.lwjgl:lwjgl-stb")
    runtimeOnly("org.lwjgl:lwjgl::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-glfw::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-opengl::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-openal::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-stb::$lwjglNatives")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    implementation("org.apache.logging.log4j", "log4j-api", "2.14.1")
    implementation("org.apache.logging.log4j", "log4j-core","2.14.1")
    implementation("org.slf4j", "slf4j-log4j12", "1.7.29")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}