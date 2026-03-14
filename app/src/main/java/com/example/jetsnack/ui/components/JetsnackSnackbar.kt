package com.example.jetsnack.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.example.jetsnack.ui.theme.JetsnackTheme

/**
 * A custom Snackbar for the Jetsnack application.
 *
 * @param snackbarData The data to be displayed in the snackbar.
 * @param modifier The [Modifier] to be applied to the snackbar.
 * @param actionOnNewLine Whether the action should be displayed on a new line.
 * @param shape The [Shape] of the snackbar.
 * @param backgroundColor The background color of the snackbar.
 * @param contentColor The color of the content (text) in the snackbar.
 * @param actionColor The color of the action button in the snackbar.
 */
@Composable
fun JetsnackSnackar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    actionOnNewLine: Boolean = false,
    shape: Shape = MaterialTheme.shapes.small,
    backgroundColor: Color = JetsnackTheme.colors.uiBackground,
    contentColor: Color = JetsnackTheme.colors.textSecondary,
    actionColor: Color = JetsnackTheme.colors.brand
) {
    Snackbar(
        snackbarData = snackbarData,
        modifier = modifier,
        actionOnNewLine = actionOnNewLine,
        shape = shape,
        containerColor = backgroundColor,
        contentColor = contentColor,
        actionColor = actionColor
    )
}
