package com.portfolio.protect_the_cockroach.ui.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.portfolio.protect_the_cockroach.R
import com.portfolio.protect_the_cockroach.game.manager.SoundPlayerManager
import com.portfolio.protect_the_cockroach.ui.MainActivity

class MineSplashActivity : AppCompatActivity() {

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_mine_splash)

      SoundPlayerManager(this).playIntro1()

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
         window.insetsController?.hide(WindowInsets.Type.statusBars())
      } else {
         window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
         )
      }

      Handler().postDelayed({
         startActivity(Intent(this, SplashActivity::class.java))
         finish()
         overridePendingTransition(R.anim.rotate, R.anim.alpha)
      }, 2000)
   }
}