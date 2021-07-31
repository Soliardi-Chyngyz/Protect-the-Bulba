package com.portfolio.protect_the_cockroach.game

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.view.SurfaceHolder
import com.portfolio.protect_the_cockroach.game.collision.Collision
import com.portfolio.protect_the_cockroach.game.manager.DynamicRenderingManager
import com.portfolio.protect_the_cockroach.game.manager.ImmovableRenderingManager
import com.portfolio.protect_the_cockroach.game.manager.UIEventsManager
import com.portfolio.protect_the_cockroach.ui.GameActivity

class GameDrawThread(
   surfaceHolder: SurfaceHolder,
   resources: Resources,
   widthScreen: Double,
   heightScreen: Double,
   context: Context,
   activity: GameActivity,
   val score: Int,
) : Thread() {

   private var collision = Collision()
   var runFlag = false
   private var canvas: Canvas? = null
   var immovableRenderingManager: ImmovableRenderingManager? =
      ImmovableRenderingManager(widthScreen, heightScreen, resources, activity, collision)
   var dynamicRenderingManager: DynamicRenderingManager? =
      DynamicRenderingManager(widthScreen, heightScreen, resources, context, activity, immovableRenderingManager!!, collision)

   private var uiEventsManager: UIEventsManager = UIEventsManager(widthScreen, heightScreen)
   private var isRestart = false

   private var surfaceHolder: SurfaceHolder? = surfaceHolder
   private var prevTime: Long = 0

   private val lock = Object()
   private var pause = false

   init {
      prevTime = System.currentTimeMillis()
   }

   fun restart() {
      dynamicRenderingManager?.restart()
      clearAll()
   }

   fun clearAll() {
      immovableRenderingManager!!.clearAll()
      collision.listOfStaticObj.clear()
      collision.listOfDynamicObj.clear()
      collision.collect.clear()
      collision.invasiveCollect.clear()
   }

   fun pauseThread() {
      pause = true
   }

   fun resumeThread(){
      synchronized(lock){
         pause = false
         lock.notifyAll()
      }
   }

   override fun run() {
      while (runFlag) {
         while(pause){
            onPause()
         }
         canvas = null
         val now = System.currentTimeMillis()
         val elapsedTime = now - prevTime
         if (elapsedTime > 2) {
            prevTime = now
            try {
               canvas = surfaceHolder?.lockCanvas(null)
               surfaceHolder?.let {
                  synchronized(it) {
                     canvas?.drawColor(Color.GRAY)
                     immovableRenderingManager?.draw(canvas, score)
                     dynamicRenderingManager?.draw(canvas, score)
                  }
               }
            } finally {
               if (canvas != null) {
                  surfaceHolder!!.unlockCanvasAndPost(canvas)
               }
            }
         }
      }
   }

   private fun onPause(){
      synchronized(lock) {
         try {
            synchronized(lock) {
               lock.wait()
            }
         } catch (e: InterruptedException){
            e.printStackTrace()
         }
      }
   }
}