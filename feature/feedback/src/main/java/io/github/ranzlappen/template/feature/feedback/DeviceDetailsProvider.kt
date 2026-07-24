package io.github.ranzlappen.template.feature.feedback

import android.content.Context
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.ranzlappen.template.core.model.DeviceDetails
import java.util.Locale
import javax.inject.Inject

/**
 * Collects the device/app snapshot attached to feedback reports. Ported from
 * the HardwareDash legacy bug-report screen: only coarse, non-identifying
 * facts a maintainer needs to reproduce an issue — never logs, never IDs.
 */
class DeviceDetailsProvider @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun collect(): DeviceDetails {
        val metrics = context.resources.displayMetrics
        return DeviceDetails(
            entries = listOf(
                "App version" to appVersion(),
                "Device" to "${Build.MANUFACTURER} ${Build.MODEL}",
                "Android" to "${Build.VERSION.RELEASE} (SDK ${Build.VERSION.SDK_INT})",
                "Build" to Build.DISPLAY,
                "Screen" to "${metrics.widthPixels}x${metrics.heightPixels} @ ${metrics.densityDpi}dpi",
                "Locale" to Locale.getDefault().toLanguageTag(),
            ),
        )
    }

    private fun appVersion(): String = try {
        val info = context.packageManager.getPackageInfo(context.packageName, 0)
        "${info.versionName} (${info.longVersionCode})"
    } catch (_: Exception) {
        "unknown"
    }
}
