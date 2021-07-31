package com.portfolio.protect_the_cockroach.game.manager

import android.content.res.Resources
import android.graphics.*
import android.os.CountDownTimer
import android.util.Log
import com.portfolio.protect_the_cockroach.R
import com.portfolio.protect_the_cockroach.`interface`.OnBonusTimerListener
import com.portfolio.protect_the_cockroach.game.base.BaseManager
import com.portfolio.protect_the_cockroach.game.collision.Collision
import com.portfolio.protect_the_cockroach.game.immovable.WallObject
import com.portfolio.protect_the_cockroach.game.lists.LocationLists
import com.portfolio.protect_the_cockroach.game.model.GameCoordinate
import com.portfolio.protect_the_cockroach.game.model.GameObject
import com.portfolio.protect_the_cockroach.game.model.LocationObject
import com.portfolio.protect_the_cockroach.ui.GameActivity
import kotlin.random.Random

class ImmovableRenderingManager(
   widthScreen: Double,
   heightScreen: Double,
   var resources: Resources,
   private val activity: GameActivity,
   val collision: Collision
) : BaseManager(widthScreen, heightScreen), OnBonusTimerListener {

   private val tableLinePaint: Paint = run {
      val paint = Paint()
      paint.color = Color.WHITE
      paint.strokeWidth = 1f
      return@run paint
   }

   private var isPermission = false
   private var bonusObj: WallObject? = null
   private var isProtectTheCockroach = false
   private var isProtectFinished = false

   private var p7: WallObject? = null
   private var p8: WallObject? = null
   private var p9: WallObject? = null
   private var p10: WallObject? = null
   private var p11: WallObject? = null

   companion object {
      val list = hashMapOf<String, GameObject>()
      var invasiveColl = hashMapOf<String, String>()
      val listOfBonus = hashMapOf<String, WallObject>()
   }

   init {
      p7 = WallObject(GameCoordinate(9, 9), widthCell, heightCell, resources, R.drawable.beton, true)
      p8 = WallObject(GameCoordinate(11, 9), widthCell, heightCell, resources, R.drawable.beton, true)
      p9 = WallObject(GameCoordinate(9, 8), widthCell, heightCell, resources, R.drawable.beton, true)
      p10 = WallObject(GameCoordinate(10, 8), widthCell, heightCell, resources, R.drawable.beton, true)
      p11 = WallObject(GameCoordinate(11, 8), widthCell, heightCell, resources, R.drawable.beton, true)
   }

   fun draw(canvas: Canvas?, level: Int) {
      drawTableLine(canvas)
      drawWalls(canvas, level)
      drawBonus(canvas)
   }

   fun clearAll(){
      list.clear()
      invasiveColl.clear()
      listOfBonus.clear()
   }

   private fun drawTableLine(canvas: Canvas?) {
      for (horizontalIndex in 0..horizontalCountCell.toInt()) {
         for (verticalIndex in 0..verticalCountCell.toInt()) {
            canvas?.drawLine(
               0F, (heightCell * verticalIndex).toFloat(),
               widthScreen.toFloat(), (heightCell * verticalIndex).toFloat(),
               tableLinePaint
            )
            canvas?.drawLine(
               (widthCell * horizontalIndex).toFloat(), 0f,
               (widthCell * horizontalIndex).toFloat(), heightScreen.toFloat(),
               tableLinePaint
            )
         }
      }
   }

   private fun drawWalls(canvas: Canvas?, level: Int) {

      var list = listOf<LocationObject>()

      when (level) {
         1 -> {
            list = LocationLists.getLevelPosition1()
         }
         2 -> {
            list = LocationLists.getLevelPosition2()
         }
         3 -> {
            list = LocationLists.getLevelPosition3()
         }
         4 -> {
            list = LocationLists.getLevelPosition4()
         }
         5 -> {
            list = LocationLists.getLevelPosition5()
         }
      }
      if (list.isNotEmpty()){
         WallObject(list[0].coordinate, widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas, list[0].name)
         WallObject(list[1].coordinate, widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas, list[1].name)
         WallObject(list[2].coordinate, widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas, list[2].name)
         WallObject(list[3].coordinate, widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas, list[3].name)
         WallObject(list[4].coordinate, widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas, list[4].name)
         WallObject(list[5].coordinate, widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas, list[5].name)

         WallObject(list[6].coordinate, widthCell, heightCell, resources, R.drawable.beton,true).drawWall(canvas,list[6].name)
         WallObject(list[7].coordinate, widthCell, heightCell, resources, R.drawable.beton,true).drawWall(canvas,list[7].name)
         WallObject(list[8].coordinate, widthCell, heightCell, resources, R.drawable.beton,true).drawWall(canvas, list[8].name)
         WallObject(list[9].coordinate, widthCell, heightCell, resources, R.drawable.beton,true).drawWall(canvas, list[9].name)
         WallObject(list[10].coordinate, widthCell, heightCell, resources, R.drawable.beton,true).drawWall(canvas, list[10].name)

         WallObject(list[11].coordinate, widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas, list[11].name)
         WallObject(list[12].coordinate, widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas, list[12].name)
         WallObject(list[13].coordinate, widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas, list[13].name)
         WallObject(list[14].coordinate, widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas, list[14].name)
      }

      if (isProtectTheCockroach) {
         p7 = WallObject(GameCoordinate(9, 9), widthCell, heightCell, resources, R.drawable.beton, true)
         p7?.drawConcrete(canvas, "p7")
         p8 = WallObject(GameCoordinate(11, 9), widthCell, heightCell, resources, R.drawable.beton, true)
         p8?.drawConcrete(canvas, "p8")
         p9 = WallObject(GameCoordinate(9, 8), widthCell, heightCell, resources, R.drawable.beton, true)
         p9?.drawConcrete(canvas, "p9")
         p10 = WallObject(GameCoordinate(10, 8), widthCell, heightCell, resources, R.drawable.beton, true)
         p10?.drawConcrete(canvas, "p10")
         p11 = WallObject(GameCoordinate(11, 8), widthCell, heightCell, resources, R.drawable.beton, true)
         p11?.drawConcrete(canvas, "p11")
      } else {
         if (isProtectFinished) {
            p7 = WallObject(GameCoordinate(9, 9), widthCell, heightCell, resources, R.drawable.kirpich, false)
            p7?.drawConcrete(canvas, "p7")
            p8 = WallObject(GameCoordinate(11, 9), widthCell, heightCell, resources, R.drawable.kirpich, false)
            p8?.drawConcrete(canvas, "p8")
            p9 = WallObject(GameCoordinate(9, 8), widthCell, heightCell, resources, R.drawable.kirpich, false)
            p9?.drawConcrete(canvas, "p9")
            p10 = WallObject(GameCoordinate(10, 8), widthCell, heightCell, resources, R.drawable.kirpich, false)
            p10?.drawConcrete(canvas, "p10")
            p11 = WallObject(GameCoordinate(11, 8), widthCell, heightCell, resources, R.drawable.kirpich, false)
            p11?.drawConcrete(canvas, "p11")
         }

         WallObject(GameCoordinate(9, 9), widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas, "k7")
         WallObject(GameCoordinate(11, 9), widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas, "k8")
         WallObject(GameCoordinate(9, 8), widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas, "k9")
         WallObject(GameCoordinate(10, 8), widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas, "k10")
         WallObject(GameCoordinate(11, 8), widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas, "k11")
      }
   }

   private fun WallObject.drawWall(canvas: Canvas?, s: String) {
      val invasive = invasiveColl[s]
      if (s == invasive) {
         list.remove(s)
      } else {
         list[s] = this
         canvas?.drawBitmap(resizeBitmap, pointStart.x.toFloat(), pointStart.y.toFloat(), Paint())
      }
   }

   private fun WallObject.drawConcrete(canvas: Canvas?, s: String){
      val invasive = invasiveColl[s]
      if (s == invasive || isProtectFinished) {
         list.remove(s)
         collision.collect.remove(s)
         invasiveColl.remove(s)
      } else {
         list[s] = this
         canvas?.drawBitmap(resizeBitmap, pointStart.x.toFloat(), pointStart.y.toFloat(), Paint())
      }
   }

   private fun drawBonus(canvas: Canvas?) {
      bonusObj?.let {
         val invasive = invasiveColl["bonus"]
         if (invasive == "bonus") {
            listOfBonus.clear()
            invasiveColl.remove("bonus")
            bonusObj = null
         } else {
            canvas?.drawBitmap(it.resizeBitmap, it.pointStart.x.toFloat(), it.pointStart.y.toFloat(), Paint())
         }
      }
   }

   fun protectCockroach(value: Boolean) {
      isProtectFinished = false
      isProtectTheCockroach = value
      activity.runOnUiThread {
         object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
               isProtectTheCockroach = false
               isProtectFinished = true
            }
         }.start()
      }
   }

   // ------------ ^_^ -----------  BonusTimer
   override fun onBonusTimer(value: Int) {
      when (value) {
         0 -> {
            bonusObj = null
            listOfBonus.clear()
         }
         1 -> {
            var drawable = 0
            var bonusName = "lol"
            when (Random.nextInt(6)) {
               0 -> {
                  drawable = R.drawable.armour
                  bonusName = "armour"
               }
               1 -> {
                  drawable = R.drawable.freezn
                  bonusName = "frozen"
               }
               2 -> {
                  drawable = R.drawable.he
                  bonusName = "he"
               }
               3 -> {
                  drawable = R.drawable.life
                  bonusName = "life"
               }
               4 -> {
                  drawable = R.drawable.lopata
                  bonusName = "lopata"
               }
               5 -> {
                  drawable = R.drawable.star
                  bonusName = "star"
               }
            }
            val gameCoordinate = GameCoordinate(Random.nextInt(1, 20), Random.nextInt(1, 9))
            bonusObj = WallObject(gameCoordinate, widthCell, heightCell, resources, drawable, false, bonusName)
            listOfBonus["bonus"] = bonusObj!!
         }
      }
   }

   override fun onBonusTick(bonusTime: Long) {
      isPermission = bonusTime % 2 == 0L
   }
}