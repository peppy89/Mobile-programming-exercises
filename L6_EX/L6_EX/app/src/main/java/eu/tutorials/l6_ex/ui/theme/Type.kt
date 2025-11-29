package eu.tutorials.l6_ex.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import eu.tutorials.l6_ex.R

val GeoFontFamily: FontFamily = FontFamily(
    Font(R.font.geo_regular)
)

private val defaultTypography = Typography()

val AppTypography : Typography = Typography(
    bodyLarge = Typography().bodyLarge.copy(fontFamily = GeoFontFamily),
    bodyMedium = Typography().bodyMedium.copy(fontFamily = GeoFontFamily),
    titleMedium = Typography().titleMedium.copy(fontFamily = GeoFontFamily)
    // add more styles if you want, all using GeoFontFamily
)