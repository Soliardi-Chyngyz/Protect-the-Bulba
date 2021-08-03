package com.portfolio.protect_the_cockroach.game.collision

import com.portfolio.protect_the_cockroach.game.dynamic.DynamicObject
import com.portfolio.protect_the_cockroach.game.manager.DynamicRenderingManager
import com.portfolio.protect_the_cockroach.game.manager.ImmovableRenderingManager
import com.portfolio.protect_the_cockroach.game.model.GameObject

class Collision {

   val listOfStaticObj = ImmovableRenderingManager.list
   val listOfDynamicObj = hashMapOf<String, GameObject>()
   val collect = hashMapOf<String, GameObject>()

   val invasiveCollect = hashMapOf<String, String>()

   fun isHit(bullet: DynamicObject): GameObject? {
      collect.forEach { target ->
         if (bullet.pointCenter.x in target.value.leftX..target.value.rightX &&
            bullet.pointCenter.y in target.value.upY..target.value.downY
         ) {
            return if (target.value.invulnerable) {
               target.value
            } else if (bullet.theirOwn && target.value.theirOwn) {
               target.value
            } else {
               invasiveCollect[target.key] = target.key
               ImmovableRenderingManager.invasiveColl[target.key] = target.key
               DynamicRenderingManager.invasiveColl[target.key] = target.key
               listOfDynamicObj.remove(target.key)
               listOfStaticObj.remove(target.key)
               collect.remove(target.key)
               target.value
            }
         }
      }
      return null
   }

   fun isStumbled(objC: GameObject, s: String): Boolean {
      val invasive = invasiveCollect[s]
      if (invasive.isNullOrEmpty())
         listOfDynamicObj[s] = objC

      val upLeftHeroX = objC.leftX + 4
      val upRightHeroX = objC.rightX - 4
      val upLeftHeroY = objC.upY + 4
      val downLeftHeroY = objC.downY - 4

      collect.putAll(listOfStaticObj)
      collect.putAll(listOfDynamicObj)
      collect.forEach { next ->
         if (s != next.key) {
            if (upRightHeroX in next.value.leftX..next.value.rightX && next.value.upY in upLeftHeroY..downLeftHeroY)
               return true
            else if (upRightHeroX in next.value.leftX..next.value.rightX && upLeftHeroY in next.value.upY..next.value.downY)
               return true
            else if (next.value.rightX in upLeftHeroX..upRightHeroX && next.value.upY in upLeftHeroY..downLeftHeroY)
               return true
            else if (upLeftHeroX in next.value.leftX..next.value.rightX && upLeftHeroY in next.value.upY..next.value.downY)
               return true
         }
      }
      return false
   }

   private val bonusObj = ImmovableRenderingManager.listOfBonus

   fun stumbledWithBonus(obj: GameObject): String? {
      if (!bonusObj.isNullOrEmpty()) {
         bonusObj.forEach {
            if (obj.type == GameObject.Type.Dynamic) {
               if (obj.rightX in it.value.leftX..it.value.rightX && it.value.upY in obj.upY..obj.downY) {
                  ImmovableRenderingManager.invasiveColl[it.key] = "bonus"
                  return it.value.bonusName
               } else if (obj.rightX in it.value.leftX..it.value.rightX && obj.upY in it.value.upY..it.value.downY) {
                  ImmovableRenderingManager.invasiveColl[it.key] = "bonus"
                  bonusObj.clear()
                  return it.value.bonusName
               } else if (it.value.rightX in obj.leftX..obj.rightX && it.value.upY in obj.upY..obj.downY) {
                  ImmovableRenderingManager.invasiveColl[it.key] = "bonus"
                  bonusObj.clear()
                  return it.value.bonusName
               } else if (obj.leftX in it.value.leftX..it.value.rightX && obj.upY in it.value.upY..it.value.downY) {
                  ImmovableRenderingManager.invasiveColl[it.key] = "bonus"
                  bonusObj.clear()
                  return it.value.bonusName
               }
            }
         }
      }
      return null
   }
}