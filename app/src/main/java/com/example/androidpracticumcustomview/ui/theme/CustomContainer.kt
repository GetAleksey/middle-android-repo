package com.example.androidpracticumcustomview.ui.theme

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver.OnPreDrawListener
import android.widget.FrameLayout

private const val MAX_CHILD_COUNT = 2
private const val DEFAULT_FADE_IN_DURATION_MS = 2000L
private const val DEFAULT_TRANSLATION_DURATION_MS = 5000L

class CustomContainer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val firstChild: View? get() = getChildAt(0)
    private val secondChild: View? get() = getChildAt(1)

    init {
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        val childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST)
        val childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST)

        firstChild?.measure(childWidthMeasureSpec, childHeightMeasureSpec)
        secondChild?.measure(childWidthMeasureSpec, childHeightMeasureSpec)

        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val parentCenterX = (right - left) / 2
        val parentHeight = bottom - top

        firstChild?.apply {
            layout(
                parentCenterX - measuredWidth / 2,
                0,
                parentCenterX + measuredWidth / 2,
                measuredHeight
            )
        }

        secondChild?.apply {
            layout(
                parentCenterX - measuredWidth / 2,
                parentHeight - measuredHeight,
                parentCenterX + measuredWidth / 2,
                parentHeight
            )
        }
    }

    override fun addView(child: View) {
        ensureValidChildCount()
        scheduleAppearanceAnimation(child)
        super.addView(child)
    }

    private fun ensureValidChildCount() {
        if (childCount >= MAX_CHILD_COUNT) {
            error("Cannot add more than $MAX_CHILD_COUNT children")
        }
    }

    private fun scheduleAppearanceAnimation(child: View) {
        child.viewTreeObserver.addOnPreDrawListener(
            object : OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    child.viewTreeObserver.removeOnPreDrawListener(this)

                    val initialTranslation = when (child) {
                        firstChild -> height / 2 - child.measuredHeight / 2
                        secondChild -> child.measuredHeight / 2 - height / 2
                        else -> 0
                    }

                    animateAppearance(child, initialTranslation.toFloat())

                    return false
                }
            }
        )
    }

    private fun animateAppearance(child: View, initialTranslation: Float) {
        val fadeInAnimator = ObjectAnimator
            .ofFloat(child, View.ALPHA, 0f, 1f)
            .setDuration(DEFAULT_FADE_IN_DURATION_MS)

        val transitionAnimator = ObjectAnimator
            .ofFloat(child, View.TRANSLATION_Y, initialTranslation, 0f)
            .setDuration(DEFAULT_TRANSLATION_DURATION_MS)

        AnimatorSet().apply {
            playTogether(fadeInAnimator, transitionAnimator)
            start()
        }
    }
}