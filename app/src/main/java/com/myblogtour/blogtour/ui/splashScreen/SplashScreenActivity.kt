package com.myblogtour.blogtour.ui.splashScreen

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.myblogtour.blogtour.databinding.ActivitySplashScreenBinding
import com.myblogtour.blogtour.ui.main.MainActivity
import java.util.concurrent.Executors

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            showSplashScreen(installSplashScreen())
        } else {
            setContentView(binding.root)
            customSplashScreen()
        }
    }

    private fun customSplashScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()
        }, 2500)
    }

    private fun showSplashScreen(splashScreen: SplashScreen) {
        splashScreen.setKeepOnScreenCondition { true }
        Executors.newSingleThreadExecutor().execute {
            Thread.sleep(2000)
            splashScreen.setKeepOnScreenCondition {
                false
            }
        }
        splashScreen.setOnExitAnimationListener { splash ->
            ObjectAnimator.ofFloat(
                splash.view,
                View.TRANSLATION_Y,
                0f,
                -splash.view.height.toFloat()
            ).apply {
                duration = 1000
                interpolator = AnticipateInterpolator()
                doOnEnd {
                    splash.remove()
                    Handler(Looper.getMainLooper()).post {
                        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                        finish()
                    }
                }
            }.start()
        }
    }
}