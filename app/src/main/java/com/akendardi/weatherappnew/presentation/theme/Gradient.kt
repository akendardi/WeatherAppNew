package com.akendardi.weatherappnew.presentation.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

data class Gradient(
    val primary: Brush,
    val secondary: Brush,
    val shadowColor: Color
) {
    constructor(
        firstColor: Color,
        secondColor: Color,
        thirdColor: Color,
        fourthColor: Color,
    ) : this(
        primary = Brush.linearGradient(listOf(firstColor, secondColor)),
        secondary = Brush.linearGradient(listOf(thirdColor, fourthColor)),
        shadowColor = firstColor
    )

    object CardGradients {
        val gradients = listOf(
            Gradient(
                firstColor = Color(0xFFFFDF37),
                secondColor = Color(0xFFFF5621),
                thirdColor = Color(0xFFFFCE21),
                fourthColor = Color(0xFFFF7F57),
            ),
            Gradient(
                firstColor = Color(0xFFFB37FF),
                secondColor = Color(0xFF3531FF),
                thirdColor = Color(0x245000FF),
                fourthColor = Color(0x8A8433FF),
            ),
            Gradient(
                firstColor = Color(0xFF379FFF),
                secondColor = Color(0xFF4021FF),
                thirdColor = Color(0x633B67FF),
                fourthColor = Color(0x523895FF),
            ),
            Gradient(
                firstColor = Color(0xFFFF7AA2),
                secondColor = Color(0xFF9021FF),
                thirdColor = Color(0xFFB33DE2),
                fourthColor = Color(0x00F270AD),
            ),
            Gradient(
                firstColor = Color(0xFFFF8A00),
                secondColor = Color(0xFFD60093),
                thirdColor = Color(0xFF61045F),
                fourthColor = Color(0xFFAA076B),
            ),
            Gradient(
                firstColor = Color(0xFF00C9FF),
                secondColor = Color(0xFF92FE9D),
                thirdColor = Color(0xFF00B4DB),
                fourthColor = Color(0xFF0083B0),
            ),
            Gradient(
                firstColor = Color(0xFFFC466B),
                secondColor = Color(0xFF3F5EFB),
                thirdColor = Color(0xFF06BEB6),
                fourthColor = Color(0xFF48B1BF),
            ),
            Gradient(
                firstColor = Color(0xFFFF6B6B),
                secondColor = Color(0xFFFFC371),
                thirdColor = Color(0xFFFFA69E),
                fourthColor = Color(0xFFFF6868),
            ),
            Gradient(
                firstColor = Color(0xFF56CCF2),
                secondColor = Color(0xFF2F80ED),
                thirdColor = Color(0xFF1CB5E0),
                fourthColor = Color(0xFF000851),
            )
        )
    }

}
