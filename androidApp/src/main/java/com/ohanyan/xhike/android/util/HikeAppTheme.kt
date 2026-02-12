package com.ohanyan.xhike.android.util

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
import com.ohanyan.xhike.android.R

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFF175366),
            secondary = Color(0xFFFFC107),
            tertiary = Color(0xFFFFFFFF),
            background = Color(0xD7D6F1F7),
            onSecondary = Color(0xFF8BC34A),
            error = Color(0xFFC50909)


        )
    } else {
        lightColorScheme(
            primary = Color(0xFF175366),
            secondary = Color(0xFFFFC107),
            tertiary = Color(0xFFFFFFFF),
            background = Color(0xD7D6F1F7),
            onSecondary = Color(0xFF8BC34A),
            error = Color(0xFFC50909)
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
        titleMedium = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 34.sp
        ),
        titleLarge = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 64.sp
        ),
        titleSmall = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        ),
        bodySmall = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 8.sp,
            color = MaterialTheme.colorScheme.tertiary
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
