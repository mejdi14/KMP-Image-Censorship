package org.example.project

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kmp_image_censorship.composeapp.generated.resources.Res
import kmp_image_censorship.composeapp.generated.resources.books
import kmp_image_censorship.composeapp.generated.resources.bottle
import kmp_image_censorship.composeapp.generated.resources.computer
import kmp_image_censorship.composeapp.generated.resources.game_console
import kmp_image_censorship.composeapp.generated.resources.plant
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {

        val images = listOf(
            Res.drawable.books,
            Res.drawable.plant,
            Res.drawable.game_console,
            Res.drawable.bottle,

            )
        ResponsiveGrid(images = images)

        // val painter = painterResource(Res.drawable.books)
        // CensorshipComposable(painter)
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun ResponsiveGrid(
    images: List<DrawableResource>,
    modifier: Modifier = Modifier,
    minColumnWidth: Dp = 200.dp
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        // Capture the available width in a local variable:
        val containerWidth = this@BoxWithConstraints.maxWidth
        // Now 'containerWidth' can be used freely inside this scope.
        val columns = maxOf(1, (containerWidth / minColumnWidth).toInt())

        FlowRow(
        ) {
            images.forEach { resId ->
                // Use 'containerWidth' instead of 'maxWidth' here
                val cellWidth = (containerWidth / columns) - 16.dp

                Box(modifier = Modifier.width(cellWidth).height(cellWidth)) {
                    val painter = painterResource(resId)
                    CensorshipComposable(painter, modifier = modifier.align(Alignment.Center))
                }
            }
        }
    }
}
