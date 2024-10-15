plugins {
    id("io.papermc.paperweight.userdev") version "1.7.2"
}

dependencies {
    implementation(project(":dungeon-common"))

    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")
    compileOnly(libs.packetevents)

    compileOnly(libs.mongodb)
    compileOnly(libs.inventory.framework)
    compileOnly(libs.configuration)
    compileOnly(libs.adventure.platform.bukkit)
    compileOnly("com.github.patheloper.pathetic:pathetic-mapping:3.1")
    compileOnly(libs.inventory.framework)
    compileOnly(libs.event.bus)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}