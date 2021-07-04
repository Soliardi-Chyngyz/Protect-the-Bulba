package com.portfolio.protect_the_cockroach.game.manager

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
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
   private var rotation = 0F

   init {
      GameActivity().setListener(this)
      gamePoint = GamePoint(widthScreen / 2, heightScreen / 2)
      paint = Paint()
   }

   fun draw(canvas: Canvas?) {
      DynamicObject(GameCoordinate(8, 9), widthCell, heightCell, resources, R.drawable.tank_hero, rotation).drawTank(canvas)
      DynamicObject(GameCoordinate(10, 9), widthCell, heightCell, resources, R.drawable.cocroach, 0F).drawTank(canvas)
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
               rotation = 180F
            }
            GameField.TypeMove.LEFT -> {
               gamePoint!!.x -= 10
               rotation = -90F
            }
            GameField.TypeMove.RIGHT -> {
               gamePoint!!.x += 10
               rotation = 90F
            }
         }
      }
   }
}