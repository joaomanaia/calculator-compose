package presentation.util.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily

@Composable
expect fun createFontFamilyResolver(): FontFamily.Resolver
