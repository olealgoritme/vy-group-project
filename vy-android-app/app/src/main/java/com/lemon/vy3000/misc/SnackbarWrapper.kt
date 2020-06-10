package com.lemon.vy3000.misc

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.view.ContextThemeWrapper
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.lemon.vy3000.R

class SnackbarWrapper private constructor(private val context: Context, private val text: CharSequence, @BaseTransientBottomBar.Duration private val duration: Int) {
    private val windowManager: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var externalCallback: Snackbar.Callback? = null
    private var action: Action? = null

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun show() {
        val layoutParams = createDefaultLayoutParams(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, null)
        windowManager.addView(object : FrameLayout(context) {
            override fun onAttachedToWindow() {
                super.onAttachedToWindow()
                onRootViewAvailable(this)
            }
        }, layoutParams)
    }

    private fun onRootViewAvailable(rootView: FrameLayout) {
        val snackbarContainer: CoordinatorLayout = object : CoordinatorLayout(ContextThemeWrapper(context, R.style.VySnackbarWrapper)) {
            override fun onAttachedToWindow() {
                super.onAttachedToWindow()
                onSnackbarContainerAttached(rootView, this)
            }
        }
        windowManager.addView(snackbarContainer, createDefaultLayoutParams(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL, rootView.windowToken))
    }

    private fun onSnackbarContainerAttached(rootView: View, snackbarContainer: CoordinatorLayout) {
        val snackbar = Snackbar.make(snackbarContainer, text, duration)
        snackbar.setCallback(object : Snackbar.Callback() {
            override fun onDismissed(snackbar: Snackbar, event: Int) {
                super.onDismissed(snackbar, event)
                // Clean up (NOTE! This callback can be called multiple times)
                if (snackbarContainer.parent != null && rootView.parent != null) {
                    windowManager.removeView(snackbarContainer)
                    windowManager.removeView(rootView)
                }
                if (externalCallback != null) {
                    externalCallback!!.onDismissed(snackbar, event)
                }
            }

            override fun onShown(snackbar: Snackbar) {
                super.onShown(snackbar)
                if (externalCallback != null) {
                    externalCallback!!.onShown(snackbar)
                }
            }
        })
        if (action != null) {
            snackbar.setAction(action!!.text, action!!.listener)
        }
        snackbar.show()
    }

    private fun createDefaultLayoutParams(type: Int, windowToken: IBinder?): WindowManager.LayoutParams {
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.format = PixelFormat.TRANSLUCENT
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.gravity = GravityCompat.getAbsoluteGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM, ViewCompat.LAYOUT_DIRECTION_LTR)
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        layoutParams.type = type
        layoutParams.token = windowToken
        return layoutParams
    }

    fun setCallback(callback: Snackbar.Callback?): SnackbarWrapper {
        externalCallback = callback
        return this
    }

    fun setAction(text: CharSequence?, listener: View.OnClickListener?): SnackbarWrapper {
        action = Action(text, listener)
        return this
    }

    private class Action(val text: CharSequence?, val listener: View.OnClickListener?)

    companion object {
        fun make(applicationContext: Context, text: CharSequence, @BaseTransientBottomBar.Duration duration: Int): SnackbarWrapper {
            return SnackbarWrapper(applicationContext, text, duration)
        }
    }

}