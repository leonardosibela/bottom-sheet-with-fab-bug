package com.sibela.bottomnavigationfab

import android.os.Handler
import android.view.View

fun View.showWithAnimation() {
    showWithAnimation(SCALING_ANIMATION_DURATION)
}

fun View.showInstantaneously() {
    showWithAnimation(ZERO_ANIMATION_DURATION)
}

fun View.hideWithAnimation() {
    hideWithAnimation(SCALING_ANIMATION_DURATION)
}

fun View.hideInstantaneously() {
    hideWithAnimation(ZERO_ANIMATION_DURATION)
}

private fun View.hideWithAnimation(animationDuration: Long) {
    animate().scaleX(0F).scaleY(0F).setDuration(animationDuration).start()
    Handler().postDelayed({
        visibility = View.GONE
    }, animationDuration)
}

private fun View.showWithAnimation(animationDuration: Long) {
    visibility = View.VISIBLE
    animate().scaleX(1F).scaleY(1F).setDuration(animationDuration).start()
}