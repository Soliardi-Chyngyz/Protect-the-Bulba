package com.portfolio.protect_the_cockroach.game

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.os.Handler
import android.view.SurfaceHolder
import com.portfolio.protect_the_cockroach.game.manager.DynamicRenderingManager
import com.portfolio.protect_the_cockroach.game.manager.ImmovableRenderingManager
import com.portfolio.protect_the_cockroach.game.manager.UIEventsManager
import java.util.*

class GameDrawThread(
   surfaceHolder: SurfaceHolder,
   resources: Resources,
   widthScreen: Double,
   heightScreen: Double,
   context: Context,
): Thread()  {

   var runFlag = false

   private var timer: Timer
   private var handler: Handler
   private var immovableRenderingManager: ImmovableRenderingManager =
      ImmovableRenderingManager(widthScreen, heightScreen, resources)
   var dynamicRenderingManager: DynamicRenderingManager =
      DynamicRenderingManager(widthScreen, heightScreen, resources, context)
   private var uiEventsManager: UIEventsManager = UIEventsManager(widthScreen, heightScreen)

   private var surfaceHolder: SurfaceHolder? = surfaceHolder
   private var prevTime: Long = 0

   init {
      prevTime = System.currentTimeMillis()
      timer = Timer()
      handler = Handler()
   }

   override fun run() {
      /*var canvas: Canvas?
      timer.schedule(object : TimerTask() {
         override fun run() {
            handler.post {
               while (runFlag) {
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
                              immovableRenderingManager.draw(canvas)
                              dynamicRenderingManager.draw(canvas)
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
         }
      }, 0, 20)*/

      var canvas: Canvas?

      while (runFlag) {
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
                     immovableRenderingManager.draw(canvas)
                     dynamicRenderingManager.draw(canvas)
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
}