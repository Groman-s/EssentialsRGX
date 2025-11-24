plugins {
    kotlin("jvm") version "2.1.20"
}

group = "com.goyanov"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://hub.spigotmc.org/nexus/content/groups/public/")
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    compileOnly("org.spigotmc:spigot-api:1.20.2-R0.1-SNAPSHOT")
}

tasks.register<Copy>("jarToPluginsFolder") {
    from(tasks.jar)
    into("C:\\Users\\Roman\\Documents\\Minecraft\\Сервер разработки\\plugins")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

tasks.build {
    finalizedBy("jarToPluginsFolder")
}