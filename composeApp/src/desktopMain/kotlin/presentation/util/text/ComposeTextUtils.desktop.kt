package presentation.util.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily

@Composable
actual fun createFontFamilyResolver(): FontFamily.Resolver {
    return androidx.compose.ui.text.font.createFontFamilyResolver()
}