package com.example.jetsnack.ui.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetsnack.R
import com.example.jetsnack.ui.theme.JetsnackTheme

@Composable
fun JetsnackGradientTintedIconButton(
    @DrawableRes iconResourceId: Int,
    onClick: () -> Unit,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    colors: List<Color> = JetsnackTheme.colors.interactiveSecondary
) {
    val interactiveSource = remember { MutableInteractionSource() }

    val border = Modifier.fadeInDiagonalGradientBorder(
        showBorder = true,
        colors = colors,
        shape = CircleShape
    )
    val pressed by interactiveSource.collectIsPressedAsState()

    val background = if(pressed) {
        Modifier.offsetGradientBackground(colors, 200f, 0f)
    } else {
        Modifier.background(JetsnackTheme.colors.uiBackground)
    }

    val blendMode = if(JetsnackTheme.colors.isDark) BlendMode.Darken else BlendMode.Plus
    val modifierColor = if(pressed) {
        Modifier.diagonalGradientTint(
            colors = listOf(
                JetsnackTheme.colors.textSecondary,
                JetsnackTheme.colors.textSecondary
            ),
            blendMode = blendMode
        )
    } else {
        Modifier.diagonalGradientTint(
            colors = colors,
            blendMode = blendMode
        )
    }

    Surface(
        modifier = modifier
            .clickable(
                onClick = onClick,
                interactionSource = interactiveSource,
                indication = null
            )
            .clip(CircleShape)
            .then(border)
            .then(background),
        color = Color.Transparent
    ) {
        Icon(
            painter = painterResource(id = iconResourceId),
            contentDescription = contentDescription,
            modifier = modifierColor
        )
    }
}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GradientTintedIconButtonPreview() {
    JetsnackTheme {
        JetsnackGradientTintedIconButton(
            iconResourceId = R.drawable.ic_add,
            onClick = {},
            contentDescription = "Demo",
            modifier = Modifier.padding(4.dp),
        )
    }
}