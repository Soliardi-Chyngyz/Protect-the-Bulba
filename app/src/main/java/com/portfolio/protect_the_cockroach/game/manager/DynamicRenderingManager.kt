package com.portfolio.protect_the_cockroach.game.manager

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
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

   companion object {
      val list = arrayListOf<ObjectCoordinate>()
   }


   init {
      gamePoint = GamePoint((widthCell * 8) - widthCell, (heightCell * 9) - heightCell)
      gamePointRed = GamePoint((widthCell * 1) - widthCell, (heightCell * 1) - heightCell)
      paint = Paint()
      canvas = Canvas()
      lastPointHero = GamePoint()
      collision = Collision()
      lastPointRed = GamePoint()
   }

   fun draw(canvas: Canvas?) {
      list.clear()
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
      ).behavior(canvas, "mainHero", gamePoint!!)
      DynamicObject(
         GameCoordinate(10, 9),
         widthCell,
         heightCell,
         resources,
         R.drawable.cocroach,
         0F
      ).behavior(canvas, "cockroach", gamePoint!!)

      randomGenerator(canvas)
   }

   private fun randomGenerator(canvas: Canvas?) {
      var rotation = 0F
      lastPointRed.x = gamePointRed.x
      lastPointRed.y = gamePointRed.y
      when (randomValueRed) {
         0 -> {
            rotation = 180F
            gamePointRed.y += 15.0
         }
         1 -> {
            rotation = 90F
            gamePointRed.x += 15.0
         }
         2 -> {
            rotation = -90F
            gamePointRed.x -= 15.0
         }
         3 -> {
            rotation = 0F
            gamePointRed.y -= 15.0
         }
         4 -> {
            rotation = 180F
            gamePointRed.y += 15.0
         }
      }
      DynamicObject(GameCoordinate(1,1), widthCell, widthCell, resources, R.drawable.tank_red, rotation).behavior(canvas, "bot", gamePointRed)
   }

   private fun DynamicObject.behavior(canvas: Canvas?, s: String, gamePoint: GamePoint) {
      val objCoordinate = ObjectCoordinate(gamePoint.x, gamePoint.x + widthCell, gamePoint.y, gamePoint.y + widthCell)
      list.add(objCoordinate)
      collision.setTankValues(objCoordinate, widthCell)

      if (gamePoint.x < 0) gamePoint.x = 0.0
      if (gamePoint.y < 0) gamePoint.y = 0.0
      if (gamePoint.x > widthScreen - widthCell) gamePoint.x = widthScreen - widthCell
      if (gamePoint.y > heightScreen - widthCell) gamePoint.y = heightScreen - widthCell

      when (s) {
         "mainHero" -> {
            if (collision.isStumbled()) {
               gamePoint.x = lastPointHero.x
               gamePoint.y = lastPointHero.y
            }
            canvas?.drawBitmap(rotatedBitmap, gamePoint.x.toFloat(), gamePoint.y.toFloat(), paint)
         }
         "bot" -> {
            if (collision.isStumbled()) {
               gamePoint.x = lastPointRed.x
               gamePoint.y = lastPointRed.y
            }
            canvas?.drawBitmap(rotatedBitmap, gamePoint.x.toFloat(), gamePoint.y.toFloat(), paint)
         }
         else -> {
            canvas?.drawBitmap(rotatedBitmap, pointStart.x.toFloat(), pointStart.y.toFloat(), paint)
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