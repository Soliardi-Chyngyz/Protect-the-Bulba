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

class DynamicRenderingManager(
   widthScreen: Double,
   heightScreen: Double,
   var resources: Resources,
) : BaseManager(widthScreen, heightScreen), OnTouchListener {

   private var gamePoint: GamePoint? = null
   private var lastPoint: GamePoint
   private val paint: Paint
   private var rotation = 0F
   private var canvas: Canvas? = null
   private var mainHero: DynamicObject? = null
   private var collision: Collision? = null

   init {
      gamePoint = GamePoint((widthCell * 8) - widthCell, (heightCell * 9) - heightCell)
      paint = Paint()
      canvas = Canvas()
      lastPoint = GamePoint()
      collision = Collision()
   }

   fun draw(canvas: Canvas?) {
      lastPoint.x = gamePoint!!.x
      lastPoint.y = gamePoint!!.y
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
         collision!!.setHeroValues(gamePoint!!, widthCell)
      }

      mainHero = DynamicObject(
         GameCoordinate(8, 9),
         widthCell,
         heightCell,
         resources,
         R.drawable.tank_hero,
         rotation
      )
      mainHero!!.drawTank(canvas, "mainHero")
      DynamicObject(
         GameCoordinate(10, 9),
         widthCell,
         heightCell,
         resources,
         R.drawable.cocroach,
         0F
      ).drawTank(canvas, "cockroach")
   }

   var typeMove: GameField.TypeMove? = null

   private fun DynamicObject.drawTank(canvas: Canvas?, s: String) {
      if (gamePoint!!.x < 0) gamePoint!!.x = 0.0
      if (gamePoint!!.y < 0) gamePoint!!.y = 0.0
      if (gamePoint!!.x > widthScreen - widthCell) gamePoint!!.x = widthScreen - widthCell
      if (gamePoint!!.y > heightScreen - widthCell) gamePoint!!.y = heightScreen - widthCell

      if (s == "mainHero") {
         if (collision!!.isStumbled()) {
            gamePoint!!.x = lastPoint.x
            gamePoint!!.y = lastPoint.y
         }
         canvas?.drawBitmap(rotatedBitmap, gamePoint!!.x.toFloat(), gamePoint!!.y.toFloat(), paint)
      } else {
         canvas?.drawBitmap(rotatedBitmap, pointStart.x.toFloat(), pointStart.y.toFloat(), paint)
      }
   }

   override fun setOnTouchClick(typeMove: GameField.TypeMove?) {
      this.typeMove = typeMove
   }
}