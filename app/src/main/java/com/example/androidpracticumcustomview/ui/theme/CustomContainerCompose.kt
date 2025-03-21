package com.example.androidpracticumcustomview.ui.theme

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onSizeChanged
import kotlinx.coroutines.launch

private const val DEFAULT_TRANSLATION_DURATION_MS = 5000
private const val DEFAULT_ALPHA_DURATION_MS = 2000

@Composable
fun CustomContainerCompose(
    modifier: Modifier = Modifier,
    translationDuration: Int = DEFAULT_TRANSLATION_DURATION_MS,
    alphaDuration: Int = DEFAULT_ALPHA_DURATION_MS,
    firstChild: @Composable (() -> Unit)? = null,
    secondChild: @Composable (() -> Unit)? = null
) {
    val alphaAnimation = remember { Animatable(0f) }
    val offsetAnimation = remember { Animatable(1f) }
    var containerHeight by remember { mutableIntStateOf(0) }
    var firstChildHeight by remember { mutableIntStateOf(0) }
    var secondChildHeight by remember { mutableIntStateOf(0) }

    val firstChildTranslationY by remember {
        derivedStateOf {
            calculateTranslationY(containerHeight, firstChildHeight, offsetAnimation.value)
        }
    }

    val secondChildTranslationY by remember {
        derivedStateOf {
            -1 * calculateTranslationY(containerHeight, secondChildHeight, offsetAnimation.value)
        }
    }

    LaunchedEffect(Unit) {
        launch {
            offsetAnimation.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = translationDuration)
            )
        }

        launch {
            alphaAnimation.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = alphaDuration)
            )
        }
    }

    Layout(
        content = {
            firstChild?.also { item ->
                Box(
                    modifier = Modifier
                        .onSizeChanged { size -> firstChildHeight = size.height }
                        .graphicsLayer {
                            translationY = firstChildTranslationY
                            alpha = alphaAnimation.value
                        }
                ) {
                    item()
                }
            }

            secondChild?.also { item ->
                Box(
                    modifier = Modifier
                        .onSizeChanged { size -> secondChildHeight = size.height }
                        .graphicsLayer {
                            translationY = secondChildTranslationY
                            alpha = alphaAnimation.value
                        }
                ) {
                    item()
                }
            }
        },
        modifier = modifier.onSizeChanged { size -> containerHeight = size.height }
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        val width = constraints.maxWidth
        val height = constraints.maxHeight

        layout(width, height) {
            placeables.forEachIndexed { index, placeable ->
                placeable.place(
                    x = (width - placeable.width) / 2,
                    y = when (index) {
                        0 -> 0
                        else -> height - placeable.height
                    }
                )
            }
        }
    }
}

private fun calculateTranslationY(parentHeight: Int, childHeight: Int, fraction: Float) =
    (parentHeight - childHeight) / 2 * fraction
