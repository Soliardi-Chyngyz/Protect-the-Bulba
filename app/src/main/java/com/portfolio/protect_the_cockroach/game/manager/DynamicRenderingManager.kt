package com.portfolio.protect_the_cockroach.game.manager

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import com.portfolio.protect_the_cockroach.R
import com.portfolio.protect_the_cockroach.`interface`.OnTouchLRed
import com.portfolio.protect_the_cockroach.`interface`.OnTouchLSecond
import com.portfolio.protect_the_cockroach.`interface`.OnTouchListener
import com.portfolio.protect_the_cockroach.game.GameField
import com.portfolio.protect_the_cockroach.game.base.BaseManager
import com.portfolio.protect_the_cockroach.game.collision.Collision
import com.portfolio.protect_the_cockroach.game.dynamic.DynamicObject
import com.portfolio.protect_the_cockroach.game.model.GameCoordinate
import com.portfolio.protect_the_cockroach.game.model.GameObject

class DynamicRenderingManager(
   widthScreen: Double,
   heightScreen: Double,
   var resources: Resources,
   private val context: Context,
) : BaseManager(widthScreen, heightScreen), OnTouchListener, OnTouchLRed, OnTouchLSecond {

   private var gamePoint: DynamicObject? = null
   private lateinit var lastPointHero: DynamicObject
   private val paint: Paint
   private var rotation = 0F
   private var canvas: Canvas? = null
   private var collision = Collision()
   private var typeMove: GameField.TypeMove? = null

   private var randomValueRed = 0

   private var pointsMap = mutableMapOf<String, DynamicObject>()
   private var lastPointsMap = mutableMapOf<String, GameObject>()

   companion object {
   }

   init {
      paint = Paint()
      canvas = Canvas()
      collision = Collision()

      gamePoint = DynamicObject(
         GameCoordinate(8, 9),
         widthCell,
         widthCell,
         resources,
         R.drawable.tank_hero,
         rotation
      )

      pointsMap["Tank4"] = DynamicObject(
         GameCoordinate(4, 1),
         widthCell,
         widthCell,
         resources,
         R.drawable.tank_4,
         rotation
      )
      pointsMap["Tank8"] = DynamicObject(
         GameCoordinate(8, 1),
         widthCell,
         widthCell,
         resources,
         R.drawable.tank_8,
         rotation
      )
      pointsMap["Tank12"] = DynamicObject(
         GameCoordinate(12, 1),
         widthCell,
         widthCell,
         resources,
         R.drawable.tank_12,
         rotation
      )
      pointsMap["Tank16"] = DynamicObject(
         GameCoordinate(16, 1),
         widthCell,
         widthCell,
         resources,
         R.drawable.tank_16,
         rotation
      )
      pointsMap["Tank20"] = DynamicObject(
         GameCoordinate(20, 1),
         widthCell,
         widthCell,
         resources,
         R.drawable.tank_20,
         rotation
      )

      pointsMap["Tank20"]?.let { ImmovableRenderingManager.list.add(it) }
      pointsMap["Tank16"]?.let { ImmovableRenderingManager.list.add(it) }
      pointsMap["Tank12"]?.let { ImmovableRenderingManager.list.add(it) }
      pointsMap["Tank8"]?.let { ImmovableRenderingManager.list.add(it) }
      pointsMap["Tank4"]?.let { ImmovableRenderingManager.list.add(it) }
   }

   fun draw(canvas: Canvas?) {

      heroBehavior(canvas)

//      cockroachBehavior(canvas)

      randomGenerator(canvas)
   }

   /*private fun cockroachBehavior(canvas: Canvas?) {
      DynamicObject(
         GameCoordinate(10, 9),
         widthCell,
         heightCell,
         resources,
         R.drawable.cocroach,
         0F
      ).behavior(canvas, "cockroach", gamePoint!!, lastPointHero)
   }*/

   private fun heroBehavior(canvas: Canvas?) {
      lastPointHero = gamePoint!!
      lastPointHero.pointStart.x = gamePoint!!.pointStart.x
      lastPointHero.pointStart.y = gamePoint!!.pointStart.y
      typeMove?.let {
         when (typeMove) {
            GameField.TypeMove.UP -> {
               gamePoint!!.pointStart.y -= 15
               rotation = 0F
            }
            GameField.TypeMove.DOWN -> {
               gamePoint!!.pointStart.y += 15
               rotation = 180F
            }
            GameField.TypeMove.LEFT -> {
               gamePoint!!.pointStart.x -= 15
               rotation = -90F
            }
            GameField.TypeMove.RIGHT -> {
               gamePoint!!.pointStart.x += 15
               rotation = 90F
            }
         }
      }
      gamePoint?.rot = rotation
      gamePoint?.rotatedBitmap
      gamePoint?.behavior(canvas, "mainHero", gamePoint!!, lastPointHero)
   }

   private fun randomGenerator(canvas: Canvas?) {
      for (i in 20 downTo 1 step 4) {
         var rotation = 0F
         lastPointsMap["Tank$i"] = pointsMap["Tank$i"]!!
         lastPointsMap["Tank$i"]!!.pointStart.x = pointsMap["Tank$i"]!!.pointStart.x
         lastPointsMap["Tank$i"]!!.pointStart.y = pointsMap["Tank$i"]!!.pointStart.y
         when (randomValueRed) {
            0 -> {
               rotation = 180F
               pointsMap["Tank$i"]!!.pointStart.y += 15.0
            }
            1 -> {
               rotation = 90F
               pointsMap["Tank$i"]!!.pointStart.x += 15.0
            }
            2 -> {
               rotation = -90F
               pointsMap["Tank$i"]!!.pointStart.x -= 15.0
            }
            3 -> {
               rotation = 0F
               pointsMap["Tank$i"]!!.pointStart.y -= 15.0
            }
            4 -> {
               rotation = 180F
               pointsMap["Tank$i"]!!.pointStart.y += 15.0
            }
         }
         pointsMap["Tank$i"]?.rot = rotation
         pointsMap["Tank$i"]?.rotatedBitmap
         pointsMap["Tank$i"]?.let {
            pointsMap["Tank$i"]?.behavior(
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
      gamePoint: GameObject,
      lastPoint: GameObject
   ) {
      gamePoint.upLeftX = gamePoint.pointStart.x
      gamePoint.upRightX = gamePoint.pointStart.x + widthCell
      gamePoint.upLeftY = gamePoint.pointStart.y
      gamePoint.downLeftY = gamePoint.pointStart.y + widthCell
//      ImmovableRenderingManager.list.add(gamePoint)

      if (gamePoint.pointStart.x < 0) gamePoint.pointStart.x = 0.0
      if (gamePoint.pointStart.y < 0) gamePoint.pointStart.y = 0.0
      if (gamePoint.pointStart.x > widthScreen - widthCell) gamePoint.pointStart.x =
         widthScreen - widthCell
      if (gamePoint.pointStart.y > heightScreen - widthCell) gamePoint.pointStart.y =
         heightScreen - widthCell

      val isStopped = collision.isStumbled(gamePoint)

      when (s) {
         "cockroach" -> {
            canvas?.drawBitmap(rotatedBitmap, pointStart.x.toFloat(), pointStart.y.toFloat(), paint)
         }
         else -> {
            if (isStopped) {
               gamePoint.pointStart.x = lastPoint.pointStart.x
               gamePoint.pointStart.y = lastPoint.pointStart.y
            }
            canvas?.drawBitmap(
               rotatedBitmap,
               gamePoint.pointStart.x.toFloat(),
               gamePoint.pointStart.y.toFloat(),
               paint
            )
         }
      }
   }

   override fun setOnTouchClick(typeMove: GameField.TypeMove?) {
      this.typeMove = typeMove
   }

   override fun setOnTickTimer(value: Int) {
      this.randomValueRed = value
   }

   override fun setOnFinishTimer(b: Boolean) {
      SoundPlayerManager(context).playSound()
   }

   override fun setOnTickTimerRed(value: Int) {

   }

   override fun setOnFinishTimerRed(b: Boolean) {

   }

   override fun setOnTickTSec(value: Int) {

   }

   override fun setOnFinishTimerSec(b: Boolean) {

   }
}