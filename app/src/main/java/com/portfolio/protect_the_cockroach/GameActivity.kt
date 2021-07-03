package com.portfolio.protect_the_cockroach

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageView
import com.portfolio.protect_the_cockroach.game.GameField
import kotlin.reflect.KFunction1

@Suppress("DEPRECATION")
class GameActivity(val onTouchClick: (GameField.TypeMove?) -> Unit) : AppCompatActivity() {

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
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    onTouchClick(typeMove)
                }
                MotionEvent.ACTION_UP -> {
                    onTouchClick(null)
                }
            }
            true
        }
    }
}