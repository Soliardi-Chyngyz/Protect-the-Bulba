package com.portfolio.protect_the_cockroach.game.manager

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import com.portfolio.protect_the_cockroach.GameActivity
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

   init {
      GameActivity().setListener(this)
      gamePoint = GamePoint(widthScreen / 2, heightScreen / 2)
      paint = Paint()
   }

   fun draw(canvas: Canvas?) {
      DynamicObject(GameCoordinate(5, 5), widthCell, widthCell, resources, R.drawable.tank_hero).drawTank(canvas)
   }

   private fun DynamicObject.drawTank(canvas: Canvas?) {
      canvas?.drawBitmap(rotatedBitmap, pointStart.x.toFloat(), pointStart.y.toFloat(), paint)
   }

   override fun setOnTouchClick(typeMove: GameField.TypeMove?) {
      typeMove?.let {
         when (typeMove) {
            GameField.TypeMove.UP -> {
               gamePoint!!.y -= 10
            }
            GameField.TypeMove.DOWN -> {
               gamePoint!!.y += 10
            }
            GameField.TypeMove.LEFT -> {
               gamePoint!!.x -= 10
            }
            GameField.TypeMove.RIGHT -> {
               gamePoint!!.x += 10
            }
         }
      }
   }
}