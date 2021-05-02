plugins {
    java
}

group = "nl.codebulb"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    implementation("org.apache.logging.log4j", "log4j-api", "2.14.1")
    implementation("org.apache.logging.log4j", "log4j-core","2.14.1")
    implementation("org.slf4j", "slf4j-log4j12", "1.7.29")
    implementation(project(":daedalus-engine"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}