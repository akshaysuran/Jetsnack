package com.example.jetsnack.ui.home

import android.content.res.Configuration
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetsnack.R
import com.example.jetsnack.ui.LocalNavAnimatedVisibilityScope
import com.example.jetsnack.ui.LocalSharedTransitionScope
import com.example.jetsnack.ui.components.JetsnackDivider
import com.example.jetsnack.ui.components.JetsnackPreviewWrapper
import com.example.jetsnack.ui.snackdetail.spatialExpressiveSpring
import com.example.jetsnack.ui.theme.AlphaNearOpaque
import com.example.jetsnack.ui.theme.JetsnackTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestinationBar(modifier: Modifier = Modifier) {
    val sharedElementScope = LocalSharedTransitionScope.current ?: throw IllegalStateException("No scope found")
    val sharedAnimationScope = LocalNavAnimatedVisibilityScope.current ?: throw IllegalStateException("No scope found")
    with(sharedElementScope) {
        with(sharedAnimationScope) {
            Column(
                modifier = modifier
                    .renderInSharedTransitionScopeOverlay()
                    .animateEnterExit(
                        enter = slideInVertically(spatialExpressiveSpring()) { -it*2},
                        exit = slideOutVertically(spatialExpressiveSpring()) { -it*2},
                    )
            ) {
                TopAppBar(
                    windowInsets = WindowInsets(0, 0, 0, 0),
                    title = {
                        Row {
                            Text(
                               text = "Delivery to 1600 Amphitheater Way",
                                style = MaterialTheme.typography.titleMedium,
                                color = JetsnackTheme.colors.textSecondary,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f)
                                    .align(Alignment.CenterVertically)
                            )
                            IconButton(
                                onClick = { /* todo */},
                                modifier = Modifier.align(Alignment.CenterVertically)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_expand_more),
                                    tint = JetsnackTheme.colors.brand,
                                    contentDescription = stringResource(R.string.label_select_delivery)
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors().copy(
                        containerColor = JetsnackTheme.colors.uiBackground
                            .copy(alpha = AlphaNearOpaque),
                        titleContentColor = JetsnackTheme.colors.textSecondary
                    )
                )
                JetsnackDivider()
            }
        }
    }
}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
fun PreviewDestinationBar() {
    JetsnackPreviewWrapper {
        DestinationBar()
    }
}