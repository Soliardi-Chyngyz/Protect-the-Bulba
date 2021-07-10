package com.portfolio.protect_the_cockroach.game.collision

import com.portfolio.protect_the_cockroach.game.manager.ImmovableRenderingManager
import com.portfolio.protect_the_cockroach.game.model.GameObject
import com.portfolio.protect_the_cockroach.game.model.ObjectCoordinate

class Collision {

   private val listOfStaticObj = ImmovableRenderingManager.list
//   private val listOfDynamicObj = arrayListOf<ObjectCoordinate>()

   fun isStumbled(objC: GameObject): Boolean {
      val upLeftHeroX = objC.upLeftX + 1
      val upRightHeroX = objC.upRightX - 1
      val upLeftHeroY = objC.upLeftY + 1
      val downLeftHeroY = objC.downLeftY - 2
      
      listOfStaticObj.add(objC)
      listOfStaticObj.distinct().forEach { next ->
         if (objC != next) {
            if (upRightHeroX in next.upLeftX..next.upRightX && next.upLeftY in upLeftHeroY..downLeftHeroY)
               return true
            else if (upRightHeroX in next.upLeftX..next.upRightX && upLeftHeroY in next.upLeftY..next.downLeftY)
               return true
            else if (next.upRightX in upLeftHeroX..upRightHeroX && next.upLeftY in upLeftHeroY..downLeftHeroY)
               return true
            else if (upLeftHeroX in next.upLeftX..next.upRightX && upLeftHeroY in next.upLeftY..next.downLeftY)
               return true
         }
      }
      return false
   }
}