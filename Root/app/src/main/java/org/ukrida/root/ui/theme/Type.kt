package org.ukrida.root.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.ukrida.root.R
// Set of Material typography styles to start with
val Inter = FontFamily(
    Font(
        R.font.inter_regular,
        FontWeight.Normal
    ),
    Font(
        R.font.inter_medium,
        FontWeight.SemiBold
    ),
    Font(
        R.font.inter_bold,
        FontWeight.Bold
    ))
val Typography = Typography(
    // BODY
    bodyLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),

    // TITLE
    titleLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    ),

    // H1
    headlineLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    )
)
