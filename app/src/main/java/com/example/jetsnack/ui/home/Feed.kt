package com.example.jetsnack.ui.home

import android.content.res.Configuration
import android.view.Window
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetsnack.model.Filter
import com.example.jetsnack.model.SnackCollection
import com.example.jetsnack.model.SnackRepo
import com.example.jetsnack.ui.components.FilterBar
import com.example.jetsnack.ui.components.JetsnackDivider
import com.example.jetsnack.ui.components.JetsnackSurface
import com.example.jetsnack.ui.theme.JetsnackTheme

@Composable
fun Feed(onSnackClicked: (Long, String) -> Unit, modifier: Modifier = Modifier) {
    val snackCollections = remember { SnackRepo.getSnacks() }
    val filter = remember {
        SnackRepo.getFilters()
    }
    Feed(
        snackCollections, filter, onSnackClicked, modifier
    )
}

@Composable
private fun Feed(
    snackCollections: List<SnackCollection>,
    filter: List<Filter>,
    onSnackClicked: (Long, String) -> Unit,
    modifier: Modifier = Modifier
) {
    JetsnackSurface(modifier = modifier.fillMaxSize()) {
        var filterVisible by remember { mutableStateOf(false) }
        SharedTransitionLayout {
            Box {
                SnackCollectionList(
                    snackCollections = snackCollections,
                    filter = filter,
                    filterVisible = filterVisible,
                    onFilterSelected = {
                        filterVisible = true
                    },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    onSnackClicked = onSnackClicked
                )
                DestinationBar()
                AnimatedVisibility(filterVisible, enter = fadeIn(), exit = fadeOut()) {
                    FilterScreen {
                        filterVisible = false
                    }
                }
            }
        }
    }
}

@Composable
private fun SnackCollectionList(
    snackCollections: List<SnackCollection>,
    filter: List<Filter>,
    filterVisible: Boolean,
    onFilterSelected: () -> Unit,
    onSnackClicked: (Long, String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item {
            Spacer(
                modifier = Modifier.windowInsetsTopHeight(
                    WindowInsets.statusBars.add(WindowInsets(top = 56.dp))
                )
            )
            FilterBar(
                filters = filter,
                onShowFilters = onFilterSelected,
                filterScreenVisible = filterVisible,
                sharedTransitionScope = sharedTransitionScope
            )
        }
        itemsIndexed(snackCollections) { index, snackCollection ->
            if(index > 0) {
                JetsnackDivider(thickness = 2.dp)
            }

            com.example.jetsnack.ui.components.SnackCollection(
                snackCollection = snackCollection,
                onSnackClicked = onSnackClicked,
                index = index
            )
        }
    }
}


@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
fun HomePreview() {
    JetsnackTheme {
        Feed(onSnackClicked = { _, _ -> })
    }
}