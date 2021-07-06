package com.portfolio.protect_the_cockroach.game

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.portfolio.protect_the_cockroach.`interface`.OnTouchListener

class GameField @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    private var gameDrawThread: GameDrawThread? = null
    private var typeMove: TypeMove? = null

    init {
        holder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {

    }

    fun getListener(): com.portfolio.protect_the_cockroach.`interface`.OnTouchListener? {
        return gameDrawThread?.dynamicRenderingManager
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        gameDrawThread = GameDrawThread(holder, resources, width.toDouble(), height.toDouble())
        gameDrawThread?.runFlag = true
        gameDrawThread?.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        gameDrawThread?.join()
    }

    enum class TypeMove {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
}