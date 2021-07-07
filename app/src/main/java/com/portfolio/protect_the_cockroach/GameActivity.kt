package com.portfolio.protect_the_cockroach

import android.annotation.SuppressLint
import android.os.*
import android.view.MotionEvent
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.portfolio.protect_the_cockroach.game.GameField
import kotlin.random.Random

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

      CDTimers()

      setupUI()
   }

   private fun CDTimers() {
      val value = Random.nextInt(5)
      object: CountDownTimer(Random.nextLong(2000, 4000), Random.nextLong(600, 1000)) {
         override fun onTick(millisUntilFinished: Long) {
            gameField.getListener()?.setOnTickTimer(value)
         }
         override fun onFinish() {
            gameField.getListener()?.setOnFinishTimer(true)
            CDTimers()
         }
      }.start()
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
         val type: GameField.TypeMove?
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