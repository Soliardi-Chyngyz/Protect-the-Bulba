package com.portfolio.protect_the_cockroach.game.collision

import android.util.Log
import com.portfolio.protect_the_cockroach.game.manager.DynamicRenderingManager
import com.portfolio.protect_the_cockroach.game.manager.ImmovableRenderingManager
import com.portfolio.protect_the_cockroach.game.model.ObjectCoordinate

class Collision {

   private val listOfStaticObj = ImmovableRenderingManager.list
   private val listOfDynamicObj = DynamicRenderingManager.list
   private var objC = ObjectCoordinate()
   var size: Double = 0.0

   fun setTankValues(objC: ObjectCoordinate, size: Double) {
      this.objC = objC
      this.size = size
   }

   fun isStumbled(): Boolean {
      /*val upLeftHeroX = gamePoint.x + 1
      val upRightHeroX = gamePoint.x + size - 1
      val upLeftHeroY = gamePoint.y + 1
      val downLeftHeroY = gamePoint.y + size - 2*/

      val upLeftHeroX = objC.upLeftX + 1
      val upRightHeroX = objC.upRightX - 1
      val upLeftHeroY = objC.upLeftY + 1
      val downLeftHeroY = objC.downLeftY - 2

      listOfStaticObj.plus(listOfDynamicObj).forEach { next ->
         if (objC != next) {
            if (upRightHeroX in next.upLeftX..next.upRightX && next.upLeftY in upLeftHeroY..downLeftHeroY)
               return true
            else if (upRightHeroX in next.upLeftX..next.upRightX && upLeftHeroY in next.upLeftY..next.downLeftY)
               return true
            else if (next.upRightX in upLeftHeroX..upRightHeroX && next.upLeftY in upLeftHeroY..downLeftHeroY)
               return true
            else if (upLeftHeroX in next.upLeftX..next.upRightX && upLeftHeroY in next.upLeftY..next.downLeftY)
               return true
         } else if (objC == next) {
            Log.i("equal", "isStumbled: $next")
         }
      }
      return false
   }
}