package gov.lbl.crucible.scanner.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Blue theme
private val BlueDarkColorScheme = darkColorScheme(
    primary = Color(0xFF90CAF9),
    secondary = Color(0xFFCE93D8),
    tertiary = Color(0xFFA5D6A7)
)

private val BlueLightColorScheme = lightColorScheme(
    primary = Color(0xFF1976D2),
    secondary = Color(0xFF9C27B0),
    tertiary = Color(0xFF388E3C)
)

// Purple theme
private val PurpleDarkColorScheme = darkColorScheme(
    primary = Color(0xFFCE93D8),
    secondary = Color(0xFF90CAF9),
    tertiary = Color(0xFFA5D6A7)
)

private val PurpleLightColorScheme = lightColorScheme(
    primary = Color(0xFF9C27B0),
    secondary = Color(0xFF1976D2),
    tertiary = Color(0xFF388E3C)
)

// Green theme
private val GreenDarkColorScheme = darkColorScheme(
    primary = Color(0xFFA5D6A7),
    secondary = Color(0xFF90CAF9),
    tertiary = Color(0xFFCE93D8)
)

private val GreenLightColorScheme = lightColorScheme(
    primary = Color(0xFF388E3C),
    secondary = Color(0xFF1976D2),
    tertiary = Color(0xFF9C27B0)
)

// Orange theme
private val OrangeDarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFB74D),
    secondary = Color(0xFF90CAF9),
    tertiary = Color(0xFFA5D6A7)
)

private val OrangeLightColorScheme = lightColorScheme(
    primary = Color(0xFFF57C00),
    secondary = Color(0xFF1976D2),
    tertiary = Color(0xFF388E3C)
)

// Red theme
private val RedDarkColorScheme = darkColorScheme(
    primary = Color(0xFFEF5350),
    secondary = Color(0xFF90CAF9),
    tertiary = Color(0xFFA5D6A7)
)

private val RedLightColorScheme = lightColorScheme(
    primary = Color(0xFFD32F2F),
    secondary = Color(0xFF1976D2),
    tertiary = Color(0xFF388E3C)
)

@Composable
fun CrucibleScannerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    accentColor: String = "blue",
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> {
            when (accentColor.lowercase()) {
                "purple" -> if (darkTheme) PurpleDarkColorScheme else PurpleLightColorScheme
                "green" -> if (darkTheme) GreenDarkColorScheme else GreenLightColorScheme
                "orange" -> if (darkTheme) OrangeDarkColorScheme else OrangeLightColorScheme
                "red" -> if (darkTheme) RedDarkColorScheme else RedLightColorScheme
                else -> if (darkTheme) BlueDarkColorScheme else BlueLightColorScheme
            }
        }
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
