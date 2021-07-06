package com.portfolio.protect_the_cockroach.game.manager

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import com.portfolio.protect_the_cockroach.R
import com.portfolio.protect_the_cockroach.`interface`.OnTouchListener
import com.portfolio.protect_the_cockroach.game.GameField
import com.portfolio.protect_the_cockroach.game.base.BaseManager
import com.portfolio.protect_the_cockroach.game.dynamic.DynamicObject
import com.portfolio.protect_the_cockroach.game.model.GameCoordinate
import com.portfolio.protect_the_cockroach.game.model.GamePoint

class DynamicRenderingManager(
   widthScreen: Double,
   heightScreen: Double,
   var resources: Resources,
) : BaseManager(widthScreen, heightScreen), OnTouchListener {

   private var gamePoint: GamePoint? = null
   private val paint: Paint
   private var rotation = 0F
   private var canvas: Canvas? = null
   private var mainHero: DynamicObject? = null

   init {
      gamePoint = GamePoint(widthScreen / 2, heightScreen / 2)
      paint = Paint()
      canvas = Canvas()
   }

   fun draw(canvas: Canvas?) {
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

      mainHero = DynamicObject(GameCoordinate(8, 9), widthCell,heightCell,resources,R.drawable.tank_hero,rotation)
      mainHero!!.drawTank(canvas)
      DynamicObject(
         GameCoordinate(10, 9),
         widthCell,
         heightCell,
         resources,
         R.drawable.cocroach,
         0F
      ).drawTank(canvas)

      mainHero?.moveTank(canvas)
   }

   var typeMove: GameField.TypeMove? = null

   private fun DynamicObject.drawTank(canvas: Canvas?) {
      canvas?.drawBitmap(rotatedBitmap, pointStart.x.toFloat(), pointStart.y.toFloat(), paint)
   }

   private fun DynamicObject.moveTank(canvas: Canvas?) {
      canvas?.drawBitmap(rotatedBitmap, gamePoint!!.x.toFloat(), gamePoint!!.y.toFloat(), paint)
   }

   override fun setOnTouchClick(typeMove: GameField.TypeMove?) {
      this.typeMove = typeMove
   }
}