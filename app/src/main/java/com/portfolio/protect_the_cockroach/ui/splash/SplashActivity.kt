package com.portfolio.protect_the_cockroach.ui.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.portfolio.protect_the_cockroach.R
import com.portfolio.protect_the_cockroach.game.manager.SoundPlayerManager
import com.portfolio.protect_the_cockroach.ui.MainActivity

class SplashActivity : AppCompatActivity()  {
   private var sound: SoundPlayerManager? = null

   private val img: ImageView by lazy {
      return@lazy this.findViewById(R.id.splash_img)
   }

   private val splash = 4000L

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_splash)


      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
         window.insetsController?.hide(WindowInsets.Type.statusBars())
      } else {
         window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
         )
      }

      init()

      Handler().postDelayed({
         startActivity(Intent(this, MainActivity::class.java))
         finish()
         overridePendingTransition(R.anim.rotate, R.anim.alpha)
      }, splash)
   }

   private fun init() {
      Glide.with(this)
         .load(R.raw.cda)
         .into(img)
      SoundPlayerManager(this).playIntro2()
   }
}