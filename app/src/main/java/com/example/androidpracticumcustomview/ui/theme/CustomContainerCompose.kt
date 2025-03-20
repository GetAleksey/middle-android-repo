package com.example.androidpracticumcustomview.ui.theme

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.Layout
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
    val offsetAnimation = remember { Animatable(1f) }
    val alphaAnimation = remember { Animatable(0f) }

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
                Box(modifier = Modifier.alpha(alphaAnimation.value)) {
                    item()
                }
            }

            secondChild?.also { item ->
                Box(modifier = Modifier.alpha(alphaAnimation.value)) {
                    item()
                }
            }
        },
        modifier = modifier
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        val width = constraints.maxWidth
        val height = constraints.maxHeight

        layout(width, height) {
            placeables.forEachIndexed { index, placeable ->
                val offset = calculateOffset(height, placeable.height, offsetAnimation.value)

                placeable.place(
                    x = (width - placeable.width) / 2,
                    y = when (index) {
                        0 -> 0 + offset
                        else -> height - placeable.height - offset
                    }
                )
            }
        }
    }
}

private fun calculateOffset(parentHeight: Int, childHeight: Int, fraction: Float) =
    ((parentHeight - childHeight) / 2 * fraction).toInt()
