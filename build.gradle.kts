plugins {
    kotlin("jvm") version "2.1.20"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.goyanov"
version = "1.4.0"

repositories {
    maven("https://hub.spigotmc.org/nexus/content/groups/public/")
    maven("https://jitpack.io")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    compileOnly("org.spigotmc:spigot-api:1.20.2-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.5")
    implementation("com.github.Groman-s:RGLib:v1.18")
}

tasks.register<Copy>("jarToPluginsFolder") {
    from(tasks.shadowJar)
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