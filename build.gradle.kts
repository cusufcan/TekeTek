plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

    // Ksp
    alias(libs.plugins.ksp) apply false

    // Hilt
    alias(libs.plugins.hilt.android) apply false

    // Navigation Safeargs
    alias(libs.plugins.navigation.safeargs.kotlin) apply false

    // Parcelize
    alias(libs.plugins.kotlin.parcelize) apply false

    // Google Services
    alias(libs.plugins.google.services) apply false
}