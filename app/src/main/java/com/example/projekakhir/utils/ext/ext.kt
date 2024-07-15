package com.example.projekakhir.utils.ext

import android.os.Build
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import java.util.Base64

fun String.decodeJwtPayload(): String {
    val parts = this.split('.')
    if (parts.size != 3) {
        throw IllegalArgumentException("Invalid JWT format")
    }

    val encodedPayload = parts[1]
    val decodedPayload = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Base64.getUrlDecoder().decode(encodedPayload)
    } else {
        android.util.Base64.decode(encodedPayload, android.util.Base64.URL_SAFE)
    }
    return String(decodedPayload, Charsets.UTF_8)
}


fun NavController.safeNavigate(direction: Int) {
    currentDestination?.getAction(direction)?.run { navigate(direction) }
}
