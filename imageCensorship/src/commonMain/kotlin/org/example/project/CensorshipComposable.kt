package org.example.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import org.example.project.helpers.computePixelatedImage
import org.example.project.helpers.toImageBitmap

/**
 * Composable for displaying an image with pixelated effect.
 */
@Composable
fun CensorshipComposable(
    painter: Painter,
    blockSize: Int = 32
) {
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current

    val size = painter.intrinsicSize

    val imageBitmap = remember(painter, density, layoutDirection) {
        painter.toImageBitmap(size, density, layoutDirection)
    }

    var isPixelated by remember { mutableStateOf(false) }
    var pixelatedImage by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(isPixelated) {
        if (isPixelated && pixelatedImage == null) {
            pixelatedImage = computePixelatedImage(imageBitmap, blockSize)
        }
    }

    Box(modifier = Modifier.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null
    ) { isPixelated = !isPixelated }) {
        if (isPixelated) {
            pixelatedImage?.let {
                Image(bitmap = it, contentDescription = "Pixelated image")
            } ?: CircularProgressIndicator()
        } else {
            Image(bitmap = imageBitmap, contentDescription = "Original image")
        }
    }
}
