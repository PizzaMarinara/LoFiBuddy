import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Libs.androidGradlePlugin)
        classpath(Libs.Kotlin.gradlePlugin)
        classpath(Libs.Hilt.gradlePlugin)
    }
}

plugins {
    id(Libs.Plugins.Spotless.pluginId) version Libs.Plugins.Spotless.version
    id(Libs.Plugins.GradleVersions.pluginId) version Libs.Plugins.GradleVersions.version
}
allprojects {

    apply {
        plugin(Libs.Plugins.Spotless.pluginId)
    }
    repositories {
        mavenCentral()
        google()
        maven("https://jitpack.io")
    }
    spotless {
        kotlin {
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            targetExclude("bin/**/*.kt")
            // targetExclude("buildSrc/**/*.kt")
            ktlint(Libs.ktLintVersion).userData(mapOf("android" to "true"))
        }
    }
}
tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}

fun isNonStable(version: String): Boolean {
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    return !regex.matches(version)
}
