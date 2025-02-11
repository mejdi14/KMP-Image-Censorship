package org.example.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import computePixelatedImage
import toImageBitmap


@Composable
fun CensorshipComposable(
    painter: Painter,
    // Use a larger block size (e.g. 32) for bigger pixel squares.
    blockSize: Int = 32
) {
    // Get current density and layout direction.
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current

    // Determine the painter's intrinsic size.
    val size = painter.intrinsicSize

    // Convert the Painter into an ImageBitmap.
    val imageBitmap = remember(painter, density, layoutDirection) {
        painter.toImageBitmap(size, density, layoutDirection)
    }

    // State to toggle between original and pixelated image.
    var isPixelated by remember { mutableStateOf(false) }
    // Cache the computed pixelated image.
    var pixelatedImage by remember { mutableStateOf<ImageBitmap?>(null) }

    // When toggled and not yet computed, generate the pixelated image.
    LaunchedEffect(isPixelated) {
        if (isPixelated && pixelatedImage == null) {
            pixelatedImage = computePixelatedImage(imageBitmap, blockSize)
        }
    }

    // Toggle images on click.
    Box(modifier = Modifier.clickable { isPixelated = !isPixelated }) {
        if (isPixelated) {
            // Show pixelated version or a progress indicator if still processing.
            pixelatedImage?.let {
                Image(bitmap = it, contentDescription = "Pixelated image")
            } ?: CircularProgressIndicator()
        } else {
            Image(bitmap = imageBitmap, contentDescription = "Original image")
        }
    }
}
