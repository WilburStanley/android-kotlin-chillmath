package com.techvalentine.chillmathgame

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatButton

object AppUtility {
    fun goToNewPageWithAnimation(context: Context, intent: Intent) {
        val options = ActivityOptions.makeCustomAnimation(context, R.anim.zoom_in, R.anim.zoom_out)
        context.startActivity(intent, options.toBundle())
    }
    @SuppressLint("ClickableViewAccessibility")
    fun setButtonTouchEffect(view: View) {
        view.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.alpha = 0.5f // Lower the opacity
                    false // Do not consume this event to allow OnClickListener to work
                }
                MotionEvent.ACTION_UP -> {
                    v.alpha = 1.0f // Restore to original opacity
                    false // Do not consume this event, so it will go to the OnClickListener
                }
                MotionEvent.ACTION_CANCEL -> {
                    v.alpha = 1.0f // Restore in case of cancellation
                    true // Indicate that we handled this event
                }
                else -> false // For all other events, do not consume
            }
        }
    }

}
