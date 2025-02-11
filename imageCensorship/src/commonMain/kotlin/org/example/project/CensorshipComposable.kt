package org.example.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.project.helpers.computePixelatedImage
import org.example.project.helpers.toImageBitmap


@Composable
fun CensorshipComposable(
    painter: Painter,
    pixelsSize: Int = 152,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current

    val size = painter.intrinsicSize

    val imageBitmap = remember(painter, density, layoutDirection) {
        painter.toImageBitmap(size, density, layoutDirection)
    }

    val pixelatedImage by produceState<ImageBitmap?>(initialValue = null, imageBitmap) {
        value = withContext(Dispatchers.Default) {
            computePixelatedImage(imageBitmap, pixelsSize)
        }
    }

    var isPixelated by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .size(200.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                isPixelated = !isPixelated
            }
    ) {
        if (isPixelated) {
            if (pixelatedImage != null) {
                Image(
                    bitmap = pixelatedImage!!,
                    contentDescription = "Pixelated image",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        } else {
            Image(
                bitmap = imageBitmap,
                contentDescription = "Original image",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

