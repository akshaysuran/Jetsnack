package com.example.jetsnack.ui.home.cart

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import com.example.jetsnack.ui.components.SnackCollection
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetsnack.R
import com.example.jetsnack.model.OrderLine
import com.example.jetsnack.model.SnackRepo
import com.example.jetsnack.ui.components.JetsnackButton
import com.example.jetsnack.ui.components.JetsnackDivider
import com.example.jetsnack.ui.components.JetsnackSurface
import com.example.jetsnack.ui.components.QuantitySelector
import com.example.jetsnack.ui.components.SnackImage
import com.example.jetsnack.ui.home.DestinationBar
import com.example.jetsnack.ui.theme.AlphaNearOpaque
import com.example.jetsnack.ui.theme.JetsnackTheme
import com.example.jetsnack.ui.utils.formatPrice
import kotlin.math.roundToInt
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.unit.IntOffset
import com.example.jetsnack.model.SnackCollection
import com.example.jetsnack.ui.snackdetail.nonSpatialExpressiveSpring
import com.example.jetsnack.ui.snackdetail.spatialExpressiveSpring

@Composable
fun Cart(
    onSnackClick: (Long, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = viewModel(
        factory = CartViewModel.provideFactory(
        )
    )
) {
    val orderLines by viewModel.orderLine.collectAsStateWithLifecycle()
    val inspireByCart = remember { SnackRepo.getInspiredByCart() }
    Cart(
        orderLine = orderLines,
        removeSnack = viewModel::removeSnack,
        increaseItemCount = viewModel::increaseSnackCount,
        decreaseItemCount = viewModel::decreaseSnackCount,
        onSnackClick = onSnackClick,
        modifier = modifier,
        inspiredByCart = inspireByCart
    )
}

@Composable
fun Cart(
    orderLine: List<OrderLine>,
    removeSnack: (Long) -> Unit,
    increaseItemCount: (Long) -> Unit,
    decreaseItemCount: (Long) -> Unit,
    inspiredByCart: SnackCollection,
    onSnackClick: (Long, String) -> Unit,
    modifier: Modifier
) {
    JetsnackSurface(
        modifier = modifier.fillMaxSize()
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            CartContent(
                orderLines = orderLine,
                removeSnack = removeSnack,
                increaseItemCount = increaseItemCount,
                decreaseItemCount = decreaseItemCount,
                inspiredByCart = inspiredByCart,
                onSnackClick = onSnackClick,
                modifier = modifier
            )
            DestinationBar(modifier = Modifier.align(Alignment.TopCenter))
            CheckoutBar(modifier = Modifier.align(Alignment.BottomCenter))
        }
    }
}

@Composable
private fun CartContent(
    orderLines: List<OrderLine>,
    removeSnack: (Long) -> Unit,
    decreaseItemCount: (Long) -> Unit,
    increaseItemCount: (Long) -> Unit,
    inspiredByCart: SnackCollection,
    onSnackClick: (Long, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val resource = LocalResources.current
    val snackCountFormattedString = remember(orderLines.size, resource) {
        resource.getQuantityString(R.plurals.cart_order_count, orderLines.size, orderLines.size)
    }

    val itemAnimationSpecFade = nonSpatialExpressiveSpring<Float>()
    val itemPlacementSpec = spatialExpressiveSpring<IntOffset>()

    LazyColumn(modifier) {
        item(key = "title") {
            Spacer(
                Modifier.windowInsetsTopHeight(
                    WindowInsets.statusBars.add(WindowInsets(top = 56.dp))
                )
            )

            Text(
                text = stringResource(R.string.cart_order_header, snackCountFormattedString),
                style = MaterialTheme.typography.titleLarge,
                color = JetsnackTheme.colors.brand,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.heightIn(min = 56.dp)
                    .padding(horizontal = 24.dp, vertical = 4.dp)
                    .wrapContentHeight()
            )
        }
        items(items = orderLines, key = { it.snack.id} ) { orderLine ->
            SwipeDismissItem(
                modifier = Modifier.animateItem(
                    fadeInSpec = itemAnimationSpecFade,
                    fadeOutSpec = itemAnimationSpecFade,
                    placementSpec = itemPlacementSpec,
                ),
                background = { progress ->
                    SwipeDismissItemBackground(progress)
                },
            ) {
                CartItem(
                    orderLine = orderLine,
                    removeSnack = removeSnack,
                    increaseItemCount = increaseItemCount,
                    decreaseItemCount = decreaseItemCount,
                    onSnackClick = onSnackClick,
                )
            }
        }
        item(key = "summery") {
            SummaryItem(
                modifier = Modifier.animateItem(
                    fadeInSpec = itemAnimationSpecFade,
                    fadeOutSpec = itemAnimationSpecFade,
                    placementSpec = itemPlacementSpec,
                ),
                subTotal = orderLines.sumOf { it.snack.price * it.count },
                shippingCost = 1000
            )
        }
        item(key = "inspiredByCart") {
            SnackCollection(
                modifier = Modifier.animateItem(
                    fadeInSpec = itemAnimationSpecFade,
                    fadeOutSpec = itemAnimationSpecFade,
                    placementSpec = itemPlacementSpec,
                ),
                snackCollection = inspiredByCart,
                onSnackClicked = onSnackClick,
                highlight = false,
            )
            Spacer(Modifier.height(56.dp))
        }
    }
}

@Composable
private fun SwipeDismissItemBackground(progress: Float) {
    Column(
        modifier = Modifier
            .background(JetsnackTheme.colors.uiBackground)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center
    ) {
        val padding: Dp by animateDpAsState(
            if (progress < 0.5f) 4.dp else 0.dp, label = "padding"
        )

        BoxWithConstraints(
            modifier =  Modifier.fillMaxWidth()
        ) {
            Surface(
                modifier = Modifier.padding(padding)
                    .fillMaxWidth()
                    .height(maxWidth)
                    .align(Alignment.Center),
                shape = RoundedCornerShape(percent = ((1 - progress)*100).roundToInt()),
                color = JetsnackTheme.colors.error
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    if(progress in 0.125f..0.475f) {
                        // Icon alpha decreases as it is about to disappear
                        val iconAlpha: Float by animateFloatAsState(
                            if (progress > 0.4f) 0.5f else 1f, label = "icon alpha",
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete_forever),
                            modifier = Modifier
                                .size(32.dp)
                                .graphicsLayer(alpha = iconAlpha),
                            tint = JetsnackTheme.colors.uiBackground,
                            contentDescription = null,
                        )
                    }

                    /*Text opacity increases as the text is supposed to appear in
                                    the screen*/
                    val textAlpha by animateFloatAsState(
                        if (progress > 0.5f) 1f else 0.5f, label = "text alpha",
                    )
                    if (progress > 0.5f) {
                        Text(
                            text = stringResource(id = R.string.remove_item),
                            style = MaterialTheme.typography.titleMedium,
                            color = JetsnackTheme.colors.uiBackground,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .graphicsLayer(
                                    alpha = textAlpha,
                                ),
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun CartItem(
    orderLine: OrderLine,
    removeSnack: (Long) -> Unit,
    increaseItemCount: (Long) -> Unit,
    decreaseItemCount: (Long) -> Unit,
    onSnackClick: (Long, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val snack = orderLine.snack
    Column(modifier = modifier
        .fillMaxWidth()
        .clickable { onSnackClick(snack.id, "cart")}
        .background(JetsnackTheme.colors.uiBackground)
        .padding(horizontal = 24.dp)) {
        Row(modifier = modifier) {
            SnackImage(
                imageRes = snack.imageRes,
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .size(100.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1F)
                    .padding(top = 16.dp, end = 16.dp)
            ) {
                Row(modifier = modifier.fillMaxWidth()) {
                    Text(
                        text = snack.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = JetsnackTheme.colors.textSecondary,
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 16.dp, end = 16.dp)
                    )
                    IconButton(
                        onClick = { removeSnack(snack.id) },
                        modifier = Modifier.padding(top = 12.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_close),
                            contentDescription = stringResource(R.string.label_remove),
                            tint = JetsnackTheme.colors.iconSecondary
                        )
                    }
                }
                Text(
                    text = snack.tagline,
                    style = MaterialTheme.typography.bodyLarge,
                    color = JetsnackTheme.colors.textHelp,
                    modifier = Modifier.padding(end = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = formatPrice(snack.price),
                        style = MaterialTheme.typography.titleMedium,
                        color = JetsnackTheme.colors.textPrimary,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 16.dp)
                            .alignBy(LastBaseline),
                    )
                    QuantitySelector(
                        count = orderLine.count,
                        decreaseItemCount = { decreaseItemCount(snack.id) },
                        increaseItemCount = { increaseItemCount(snack.id) },
                        modifier = Modifier.alignBy(LastBaseline),
                    )
                }
            }
        }
    }
}

@Composable
fun SummaryItem(subTotal: Long, shippingCost: Long, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.cart_summary_header),
            style = MaterialTheme.typography.titleLarge,
            color = JetsnackTheme.colors.brand,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 24.dp)
                .heightIn(min = 56.dp)
                .wrapContentHeight()
        )

        Row(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
            Text(
                text = stringResource(R.string.cart_shipping_label),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1F)
                    .wrapContentWidth(Alignment.Start)
                    .alignBy(LastBaseline)
            )

            Text(
                text = formatPrice(shippingCost),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .alignBy(LastBaseline)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        JetsnackDivider()

        Row(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
            Text(
                text = stringResource(R.string.cart_total_label),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1F)
                    .padding(end = 16.dp)
                    .wrapContentWidth(Alignment.End)
                    .alignBy(LastBaseline)
            )
            Text(
                text = formatPrice(shippingCost + subTotal),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .alignBy(LastBaseline)
            )
        }
        JetsnackDivider()
    }
}

@Composable
private fun CheckoutBar(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(JetsnackTheme.colors.uiBackground.copy(alpha = AlphaNearOpaque))
    ) {
        JetsnackDivider()
        Row {
            Spacer(modifier = Modifier.weight(1F))
            JetsnackButton(
                onClick = { },
                shape = RectangleShape,
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .weight(1F)
            ) {
                Text(
                    text = stringResource(R.string.cart_checkout),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    maxLines = 1
                )
            }
        }
    }
}