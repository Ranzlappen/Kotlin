// Root build file. Per-module configuration lives in each module's
// build.gradle.kts; shared dependency versions live in
// gradle/libs.versions.toml (the single source of truth — never inline a
// version in a module file).
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.spotless)
}

// ---------------------------------------------------------------------------
// Code quality: Spotless (ktlint formatting) + detekt (static analysis).
// Run locally with:  ./gradlew spotlessApply detekt
// CI runs:           ./gradlew spotlessCheck detekt
// ---------------------------------------------------------------------------

spotless {
    kotlin {
        target("**/*.kt")
        targetExclude("**/build/**")
        ktlint(libs.versions.ktlint.get())
            .editorConfigOverride(
                mapOf(
                    // Compose naming: composables are PascalCase functions.
                    "ktlint_function_naming_ignore_when_annotated_with" to "Composable",
                ),
            )
    }
    kotlinGradle {
        target("**/*.gradle.kts")
        targetExclude("**/build/**")
        ktlint(libs.versions.ktlint.get())
    }
    format("misc") {
        target("**/*.md", "**/.gitignore")
        targetExclude("**/build/**")
        trimTrailingWhitespace()
        endWithNewline()
    }
}

detekt {
    buildUponDefaultConfig = true
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    source.setFrom(
        files(
            "app/src/main/java",
            "core/common/src/main/java",
            "core/model/src/main/kotlin",
            "core/designsystem/src/main/java",
            "core/data/src/main/java",
            "feature/home/src/main/java",
            "feature/settings/src/main/java",
            "feature/feedback/src/main/java",
            "feature/manual/src/main/java",
        ),
    )
    parallel = true
}
