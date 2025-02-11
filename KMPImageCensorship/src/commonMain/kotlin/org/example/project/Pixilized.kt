import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.min

// -- Extension Function: Convert a Painter to an ImageBitmap --

fun Painter.toImageBitmap(
    size: Size,
    density: androidx.compose.ui.unit.Density,
    layoutDirection: LayoutDirection,
): ImageBitmap {
    // Create a mutable ImageBitmap with ARGB_8888 configuration.
    val bitmap = ImageBitmap(size.width.toInt(), size.height.toInt(), ImageBitmapConfig.Argb8888)
    val canvas = Canvas(bitmap)
    // Use a CanvasDrawScope to draw the painter's content onto our bitmap.
    CanvasDrawScope().draw(density, layoutDirection, canvas, size) {
        draw(size)
    }
    return bitmap
}

// -- Function: Compute the Pixelated Version of an ImageBitmap --

fun computePixelatedImage(original: ImageBitmap, blockSize: Int): ImageBitmap {
    val width = original.width
    val height = original.height

    // Obtain a PixelMap to access individual pixel colors.
    val pixelMap = original.toPixelMap()

    // Create a new mutable ImageBitmap to hold the pixelated result.
    val pixelated = ImageBitmap(width, height, ImageBitmapConfig.Argb8888)
    val canvas = Canvas(pixelated)

    // Process the image block by block.
    for (y in 0 until height step blockSize) {
        for (x in 0 until width step blockSize) {
            // Determine actual block dimensions (edge blocks may be smaller).
            val blockWidth = min(blockSize, width - x)
            val blockHeight = min(blockSize, height - y)

            // Sum up the color components within the current block.
            var sumRed = 0f
            var sumGreen = 0f
            var sumBlue = 0f
            var sumAlpha = 0f

            for (dy in 0 until blockHeight) {
                for (dx in 0 until blockWidth) {
                    val color = pixelMap[x + dx, y + dy]
                    sumRed += color.red
                    sumGreen += color.green
                    sumBlue += color.blue
                    sumAlpha += color.alpha
                }
            }
            val count = blockWidth * blockHeight

            // Compute the average color of the block.
            val avgColor = Color(
                red = sumRed / count,
                green = sumGreen / count,
                blue = sumBlue / count,
                alpha = sumAlpha / count
            )

            // Create a Paint with the average color.
            val paint = Paint().apply { color = avgColor }

            // Draw a filled rectangle for this block using explicit coordinates.
            canvas.drawRect(
                left = x.toFloat(),
                top = y.toFloat(),
                right = (x + blockWidth).toFloat(),
                bottom = (y + blockHeight).toFloat(),
                paint = paint
            )
        }
    }
    return pixelated
}

// -- Composable: Toggle between the original and pixelated image --

@Composable
fun PixelatedImageToggle(
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
