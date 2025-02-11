package org.example.project.helpers

import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.toPixelMap
import kotlin.math.min

/**
 * Computes a pixelated version of the given [ImageBitmap].
 */
internal fun computePixelatedImage(original: ImageBitmap, blockSize: Int): ImageBitmap {
    val width = original.width
    val height = original.height

    val pixelMap = original.toPixelMap()

    val pixelated = ImageBitmap(width, height, ImageBitmapConfig.Argb8888)
    val canvas = Canvas(pixelated)

    for (y in 0 until height step blockSize) {
        for (x in 0 until width step blockSize) {
            // Determine actual block dimensions (edge blocks may be smaller).
            val blockWidth = min(blockSize, width - x)
            val blockHeight = min(blockSize, height - y)

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

            val avgColor = Color(
                red = sumRed / count,
                green = sumGreen / count,
                blue = sumBlue / count,
                alpha = sumAlpha / count
            )

            val paint = Paint().apply { color = avgColor }

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