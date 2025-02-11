package org.example.project

import PixelatedImageToggle
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import kmp_image_censorship.composeapp.generated.resources.Res
import kmp_image_censorship.composeapp.generated.resources.compose_multiplatform
import kmp_image_censorship.composeapp.generated.resources.computer
import toImageBitmap

@Composable
@Preview
fun App() {
    MaterialTheme {
        val myPainter = painterResource(Res.drawable.computer)

        PixelatedImageToggle(myPainter)
    }
}