package com.burixer.mynotesapp.core.utilities

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

@Composable
fun getTranslatedAchievement(key: String): String {
    if (key.isBlank()) return ""

    val context = LocalContext.current

    val resourceId = context.resources.getIdentifier(key.trim(), "string", context.packageName)

    return if (resourceId != 0) {
        stringResource(resourceId)
    } else {
        key
    }
}

