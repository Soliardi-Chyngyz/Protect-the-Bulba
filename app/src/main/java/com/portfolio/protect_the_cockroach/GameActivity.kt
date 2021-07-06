package com.portfolio.protect_the_cockroach

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.portfolio.protect_the_cockroach.game.GameField
import com.portfolio.protect_the_cockroach.game.manager.DynamicRenderingManager

@Suppress("DEPRECATION")
class GameActivity : AppCompatActivity() {

   private val gameField: GameField by lazy {
      return@lazy this.findViewById<GameField>(R.id.game_field)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_game)

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
         window.insetsController?.hide(WindowInsets.Type.statusBars())
      } else {
         window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
         )
      }
      window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

      setupUI()
   }

   private fun setupUI() {
      findViewById<ImageView>(R.id.up).setClickListener(GameField.TypeMove.UP)
      findViewById<ImageView>(R.id.right).setClickListener(GameField.TypeMove.RIGHT)
      findViewById<ImageView>(R.id.down).setClickListener(GameField.TypeMove.DOWN)
      findViewById<ImageView>(R.id.left).setClickListener(GameField.TypeMove.LEFT)
   }

   @SuppressLint("ClickableViewAccessibility")
   private fun ImageView.setClickListener(typeMove: GameField.TypeMove) {
      this.setOnTouchListener { _, event ->
         var type: GameField.TypeMove? = null
         when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
               type = typeMove
               gameField.getListener()?.setOnTouchClick(type)
            }
            MotionEvent.ACTION_UP -> {
               type = null
               gameField.getListener()?.setOnTouchClick(type)
            }
         }
         true
      }
   }
}