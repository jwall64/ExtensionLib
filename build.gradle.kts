import buildlogic.Utils

plugins {
    id("build.fabric")
    id("build.publish")
}

Utils.setupResources(project, rootProject, "fabric.mod.json")

dependencies {

    minecraft("com.mojang:minecraft:${project.findProperty("minecraft-version")}")
    implementation("net.fabricmc:fabric-loader:${project.findProperty("fabric-loader-version")}")

    // Fabric API
    listOf(
        "fabric-networking-api-v1"
    ).forEach {
        api(include(fabricApi.module(it, "${project.findProperty("fabric-api-version")}"))!!)
    }

    compileOnly(libs.jetbrains.annotations)

    implementation("dev.jwall64:midnightcfg-platform-minecraft:3.5.6")

    implementation(libs.semver)
    include(libs.semver)

    implementation("dev.jwall64:databridge:0.12.2")
}
