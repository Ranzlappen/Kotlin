package io.github.ranzlappen.template.feature.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

/**
 * Languages the app ships. Extend this list together with the matching
 * `values-<tag>` resource folders and `res/xml/locales_config.xml`.
 */
enum class AppLanguage(
    val tag: String?,
) {
    SYSTEM(null),
    ENGLISH("en"),
    GERMAN("de"),
    ;

    companion object {
        /** Currently applied per-app language, resolved to a known entry. */
        fun current(): AppLanguage {
            val locales = AppCompatDelegate.getApplicationLocales()
            if (locales.isEmpty) return SYSTEM
            val tag = locales.toLanguageTags()
            return entries.firstOrNull { it.tag != null && tag.startsWith(it.tag) } ?: SYSTEM
        }
    }

    /** Applies this language; the framework persists it and recreates the UI. */
    fun apply() {
        AppCompatDelegate.setApplicationLocales(
            if (tag == null) {
                LocaleListCompat.getEmptyLocaleList()
            } else {
                LocaleListCompat.forLanguageTags(tag)
            },
        )
    }
}
