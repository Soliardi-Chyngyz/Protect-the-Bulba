package com.portfolio.protect_the_cockroach.game

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.portfolio.protect_the_cockroach.`interface`.*
import com.portfolio.protect_the_cockroach.ui.GameActivity

class GameField @JvmOverloads constructor(
   context: Context,
   attrs: AttributeSet? = null,
   defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

   private var gameDrawThread: GameDrawThread? = null
   private var activity: GameActivity? = null
   private var score = 0

   init {
      holder.addCallback(this)
   }

   override fun surfaceCreated(holder: SurfaceHolder) {

   }

   fun sendActivity(gameActivity: GameActivity) {
      activity = gameActivity
   }

   fun switchOffGame() {
      gameDrawThread!!.removeAll()
      gameDrawThread!!.interrupt()
      gameDrawThread = null
   }

   fun pauseGame() {
      gameDrawThread?.pauseThread()
   }

   fun unPause() {
      gameDrawThread!!.resumeThread()
   }

   fun setArg(value: Int) {
      score = value
   }

   fun getLOnBonusTime(): OnBonusTimerListener? = gameDrawThread?.immovableRenderingManager

   fun getReTranslator(): OnClickL? = gameDrawThread?.dynamicRenderingManager

   fun getListener(): OnTouchLFirst? = gameDrawThread?.dynamicRenderingManager

   fun getLFour(): OnTouchLFour? = gameDrawThread?.dynamicRenderingManager

   fun getLThird(): OnTouchLThird? = gameDrawThread?.dynamicRenderingManager

   fun getLFive(): OnTouchLFive? = gameDrawThread?.dynamicRenderingManager

   fun getLSecond(): OnTouchLSecond? = gameDrawThread?.dynamicRenderingManager


   override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
      if (activity != null && score != 0) {
         gameDrawThread =
            GameDrawThread(holder, resources, width.toDouble(), height.toDouble(), context, activity!!, score)
         gameDrawThread?.runFlag = true
         gameDrawThread?.start()
      }
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