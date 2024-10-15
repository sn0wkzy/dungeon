plugins {
    id("io.papermc.paperweight.userdev") version "1.7.2"
}

dependencies {
    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")

    compileOnly(libs.annotations)
    compileOnly(libs.configuration)

    compileOnly(libs.mongodb)
    compileOnly(libs.luckperms.api)
    compileOnly(libs.packetevents)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    implementation(libs.caffeine)
    implementation(libs.adventure.platform.bukkit)

    implementation(libs.inventory.framework)
    implementation("com.github.patheloper.pathetic:pathetic-mapping:3.1")

    implementation(libs.event.bus)
}