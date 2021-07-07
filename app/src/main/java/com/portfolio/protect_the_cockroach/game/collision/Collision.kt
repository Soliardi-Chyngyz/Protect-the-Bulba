package com.portfolio.protect_the_cockroach.game.collision

import com.portfolio.protect_the_cockroach.game.manager.ImmovableRenderingManager
import com.portfolio.protect_the_cockroach.game.model.GamePoint

class Collision {

   private val listOfStaticObj = ImmovableRenderingManager.list
   private var gamePointHero = GamePoint()
   var size: Double = 0.0

   fun setHeroValues(gamePoint: GamePoint, size: Double) {
      gamePointHero = gamePoint
      this.size = size
   }

   fun isStumbled() : Boolean {
      val upLeftHeroX = gamePointHero.x
      val upRightHeroX = gamePointHero.x + size
      val upLeftHeroY = gamePointHero.y
      val downLeftHeroY = gamePointHero.y + size

      listOfStaticObj.forEach { next ->
         if (upRightHeroX in next.upLeftX..next.upRightX && next.upLeftY in upLeftHeroY..downLeftHeroY)
            return true
         else if (upRightHeroX in next.upLeftX..next.upRightX && upLeftHeroY in next.upLeftY..next.downLeftY)
            return true
         else if (next.upRightX in upLeftHeroX..upRightHeroX && next.upLeftY in upLeftHeroY..downLeftHeroY)
            return true
         else if (upLeftHeroX in next.upLeftX..next.upRightX && upLeftHeroY in next.upLeftY..next.downLeftY)
            return true
      }
      return false
   }
}