package com.portfolio.protect_the_cockroach.game.utils

import com.portfolio.protect_the_cockroach.game.dynamic.DynamicObject
import com.portfolio.protect_the_cockroach.game.model.GamePoint

object AccuratePosition {
   fun calculating(
      rotation: Float,
      dynamicObject: DynamicObject?,
      widthCell: Double,
      heightCell: Double
   ): GamePoint {
      val value = GamePoint()
      when (rotation) {
         0F -> {
            value.x = dynamicObject!!.pointStart.x + widthCell / 2 - (widthCell / 12)
            value.y = dynamicObject.pointStart.y - (heightCell / 9)
         }
         90F -> {
            value.x = dynamicObject!!.pointStart.x + widthCell
            value.y = dynamicObject.pointStart.y + widthCell / 2 - (heightCell / 18)
         }
         180F -> {
            value.x = dynamicObject!!.pointStart.x + widthCell / 2 - (widthCell / 12)
            value.y = dynamicObject.pointStart.y + widthCell - (heightCell / 9)
         }
         -90F -> {
            value.x = dynamicObject!!.pointStart.x - widthCell / 4
            value.y = dynamicObject.pointStart.y + widthCell / 2 - (heightCell / 18)
         }
      }
      return value
   }
}