package com.portfolio.protect_the_cockroach.game.manager

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.provider.SyncStateContract
import com.portfolio.protect_the_cockroach.R
import com.portfolio.protect_the_cockroach.`interface`.OnTouchListener
import com.portfolio.protect_the_cockroach.game.GameField
import com.portfolio.protect_the_cockroach.game.base.BaseManager
import com.portfolio.protect_the_cockroach.game.collision.Collision
import com.portfolio.protect_the_cockroach.game.dynamic.DynamicObject
import com.portfolio.protect_the_cockroach.game.model.GameCoordinate
import com.portfolio.protect_the_cockroach.game.model.GamePoint
import com.portfolio.protect_the_cockroach.game.model.ObjectCoordinate

class DynamicRenderingManager(
   widthScreen: Double,
   heightScreen: Double,
   var resources: Resources,
) : BaseManager(widthScreen, heightScreen), OnTouchListener {

   private var gamePoint: GamePoint? = null
   private val paint: Paint
   private var rotation = 0F
   private var canvas: Canvas? = null
   private var collision = Collision()
   private var typeMove: GameField.TypeMove? = null

   private var randomValueRed = 0
   private var lastPointHero: GamePoint
   private var gamePointRed: GamePoint
   private var lastPointRed: GamePoint

   private var pointsMap = mutableMapOf<String, GamePoint>()
   private var lastPointsMap = mutableMapOf<String, GamePoint>()


   companion object {
      val list = arrayListOf<ObjectCoordinate>()
   }

   init {
      gamePoint = GamePoint((widthCell * 8) - widthCell, (heightCell * 9) - heightCell)
      paint = Paint()
      canvas = Canvas()
      lastPointHero = GamePoint()
      collision = Collision()
      gamePointRed = GamePoint((widthCell * 1) - widthCell, (heightCell * 1) - heightCell)
      lastPointRed = GamePoint()

      pointsMap["Tank4"] = GamePoint((widthCell * 4) - widthCell, (heightCell * 1) - heightCell)
      pointsMap["Tank8"] = GamePoint((widthCell * 8) - widthCell, (heightCell * 1) - heightCell)
      pointsMap["Tank12"] = GamePoint((widthCell * 12) - widthCell, (heightCell * 1) - heightCell)
      pointsMap["Tank16"] = GamePoint((widthCell * 16) - widthCell, (heightCell * 1) - heightCell)
      pointsMap["Tank20"] = GamePoint((widthCell * 20) - widthCell, (heightCell * 1) - heightCell)
      lastPointsMap["Tank4"] = GamePoint()
      lastPointsMap["Tank8"] = GamePoint()
      lastPointsMap["Tank12"] = GamePoint()
      lastPointsMap["Tank16"] = GamePoint()
      lastPointsMap["Tank20"] = GamePoint()
   }

   fun draw(canvas: Canvas?) {
      list.clear()

      heroBehavior(canvas)

      cockroachBehavior(canvas)

      randomGenerator(canvas)
   }

   private fun cockroachBehavior(canvas: Canvas?) {
      DynamicObject(
         GameCoordinate(10, 9),
         widthCell,
         heightCell,
         resources,
         R.drawable.cocroach,
         0F
      ).behavior(canvas, "cockroach", gamePoint!!, lastPointHero)
   }

   private fun heroBehavior(canvas: Canvas?) {
      lastPointHero.x = gamePoint!!.x
      lastPointHero.y = gamePoint!!.y
      typeMove?.let {
         when (typeMove) {
            GameField.TypeMove.UP -> {
               gamePoint!!.y -= 15
               rotation = 0F
            }
            GameField.TypeMove.DOWN -> {
               gamePoint!!.y += 15
               rotation = 180F
            }
            GameField.TypeMove.LEFT -> {
               gamePoint!!.x -= 15
               rotation = -90F
            }
            GameField.TypeMove.RIGHT -> {
               gamePoint!!.x += 15
               rotation = 90F
            }
         }
      }
      DynamicObject(
         GameCoordinate(8, 9),
         widthCell,
         heightCell,
         resources,
         R.drawable.tank_hero,
         rotation
      ).behavior(canvas, "mainHero", gamePoint!!, lastPointHero)
   }

   private fun randomGenerator(canvas: Canvas?) {
      for (i in 20 downTo 1 step 4) {
         var rotation = 0F
         val local = pointsMap["Tank$i"]
         lastPointsMap["Tank$i"] = GamePoint(local!!.x, local.y)
         when (randomValueRed) {
            0 -> {
               rotation = 180F
               pointsMap["Tank$i"]!!.y += 15.0
            }
            1 -> {
               rotation = 90F
               pointsMap["Tank$i"]!!.x += 15.0
            }
            2 -> {
               rotation = -90F
               pointsMap["Tank$i"]!!.x -= 15.0
            }
            3 -> {
               rotation = 0F
               pointsMap["Tank$i"]!!.y -= 15.0
            }
            4 -> {
               rotation = 180F
               pointsMap["Tank$i"]!!.y += 15.0
            }
         }
         pointsMap["Tank$i"]?.let {
            DynamicObject(GameCoordinate(1,1), widthCell, widthCell, resources, R.drawable.tank_4, rotation).behavior(
               canvas,
               "bot",
               it,
               lastPointsMap["Tank$i"]!!
            )
         }
      }
   }

   private fun DynamicObject.behavior(
      canvas: Canvas?,
      s: String,
      gamePoint: GamePoint,
      lastPoint: GamePoint
   ) {
      val objCoordinate = ObjectCoordinate(gamePoint.x, gamePoint.x + widthCell, gamePoint.y, gamePoint.y + widthCell)
      list.add(objCoordinate)

      if (gamePoint.x < 0) gamePoint.x = 0.0
      if (gamePoint.y < 0) gamePoint.y = 0.0
      if (gamePoint.x > widthScreen - widthCell) gamePoint.x = widthScreen - widthCell
      if (gamePoint.y > heightScreen - widthCell) gamePoint.y = heightScreen - widthCell

      val isStopped = collision.isStumbled(objCoordinate)

      when (s) {
         "cockroach" -> {
            canvas?.drawBitmap(rotatedBitmap, pointStart.x.toFloat(), pointStart.y.toFloat(), paint)
         } else -> {
            if (isStopped) {
               gamePoint.x = lastPoint.x
               gamePoint.y = lastPoint.y
            }
            canvas?.drawBitmap(rotatedBitmap, gamePoint.x.toFloat(), gamePoint.y.toFloat(), paint)
         }
      }
   }

   override fun setOnTouchClick(typeMove: GameField.TypeMove?) {
      this.typeMove = typeMove
   }

   override fun setOnTickTimer(value: Int) {
      randomValueRed = value
   }

   override fun setOnFinishTimer(b: Boolean) {

   }
}