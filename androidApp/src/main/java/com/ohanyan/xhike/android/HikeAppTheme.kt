package com.ohanyan.xhike.android

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFF006b59),
            secondary = Color(0xFFFFC107),
            tertiary = Color(0xFF3700B3),
            background = Color(0xD7D5E6DF),

        )
    } else {
        lightColorScheme(
            primary = Color(0xFF003733),
            secondary = Color(0xFFFFC107),
            tertiary = Color(0xFF3700B3),
            background = Color(0xD7D5E6DF)
        )
    }

    val appFontFamily = FontFamily(
        fonts = listOf(
            Font(
                resId = R.font.josefin,
                weight = FontWeight.W400,
                style = FontStyle.Normal
            )
        )
    )

    val typography = Typography(
        titleLarge = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 64.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
    )

    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
