package com.example.jetsnack.ui.components

import android.content.res.Resources
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import com.example.jetsnack.ui.theme.JetsnackTheme
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * A custom Scaffold for the Jetsnack application, providing consistent styling.
 */
@Composable
fun JetsnackScaffold(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    backgroundColor: Color = JetsnackTheme.colors.uiBackground,
    contentColor: Color = JetsnackTheme.colors.textSecondary,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = {
            snackbarHost(snackbarHostState)
        },
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = backgroundColor,
        contentColor = contentColor,
        content = content
    )
}

@Stable
class JetsnackScaffoldState(
    val snackbarHostState: SnackbarHostState,
    private val resources: Resources,
    val coroutineScope: CoroutineScope,
) {
    // Process snackbars coming from SnackbarManager
    init {
        coroutineScope.launch {

        }
    }
}

@Composable
fun rememberJetsnackScaffoldState(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): JetsnackScaffoldState = remember(
    snackbarHostState,
    resources,
    coroutineScope,
) {
    JetsnackScaffoldState(
        snackbarHostState,
        resources,
        coroutineScope,
    )
}

/*
* A composable function that returns the [Resources] associated with the current context. It
* will be recomposed when `Configuration` gets updated
* */
@Composable
@ReadOnlyComposable
private fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}
