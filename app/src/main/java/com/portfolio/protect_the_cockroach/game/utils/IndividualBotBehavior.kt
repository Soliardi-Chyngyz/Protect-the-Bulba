package com.portfolio.protect_the_cockroach.game.utils

import com.portfolio.protect_the_cockroach.game.dynamic.DynamicObject

object IndividualBotBehavior {

   fun botMoving(localObj: DynamicObject, randomValue: Int, isFire: Boolean): DynamicObject {
      when (randomValue) {
         0 -> {
            localObj.rotate(180F)
            localObj.pointStart.y += 15.0
         }
         1 -> {
            localObj.rotate(90F)
            localObj.pointStart.x += 15.0
         }
         2 -> {
            localObj.rotate(-90F)
            localObj.pointStart.x -= 15.0
         }
         3 -> {
            localObj.rotate(0F)
            localObj.pointStart.y -= 15.0
         }
         4 -> {
            localObj.rotate(180F)
            localObj.pointStart.y += 15.0
         }
      }
      return localObj
   }
}
