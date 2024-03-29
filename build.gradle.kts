import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {

    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.dependencyUpdates)
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        // Don't allow non-stable versions, unless we are already using one for this dependency
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}

/**
 * Decides if this version is stable or not.
 */
fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return !isStable
}
