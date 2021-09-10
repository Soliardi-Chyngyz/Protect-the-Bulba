package com.portfolio.protect_the_cockroach.game.manager

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.os.CountDownTimer
import android.widget.TextView
import com.portfolio.protect_the_cockroach.R
import com.portfolio.protect_the_cockroach.`interface`.*
import com.portfolio.protect_the_cockroach.game.GameField
import com.portfolio.protect_the_cockroach.game.base.BaseManager
import com.portfolio.protect_the_cockroach.game.collision.Collision
import com.portfolio.protect_the_cockroach.game.dynamic.DynamicObject
import com.portfolio.protect_the_cockroach.game.model.GameCoordinate
import com.portfolio.protect_the_cockroach.game.model.GameObject
import com.portfolio.protect_the_cockroach.game.utils.AccuratePosition
import com.portfolio.protect_the_cockroach.ui.GameActivity
import kotlin.random.Random

class DynamicRenderingManager(
   widthScreen: Double,
   heightScreen: Double,
   var resources: Resources,
   val context: Context,
   private val activity: GameActivity,
   private val immovableRenderingManager: ImmovableRenderingManager,
   private val collision: Collision,
) : BaseManager(widthScreen, heightScreen), OnTouchLFirst, OnTouchLFour, OnTouchLSecond,
   OnTouchLThird, OnTouchLFive, OnClickL {

   // region Fields
   private val paint: Paint = Paint()
   private lateinit var canvas: Canvas
   private var typeMove: GameField.TypeMove? = null

   // Tanks
   private var locationHero: DynamicObject? = null
   private var locationPotato: DynamicObject? = null
   private var locationMap = mutableMapOf<String, DynamicObject>()

   // Bullets
   private var bullet1: DynamicObject? = null
   private var bullet2: DynamicObject? = null
   private var bullet3: DynamicObject? = null
   private var bullet4: DynamicObject? = null
   private var bullet5: DynamicObject? = null
   private var bulletHero: DynamicObject? = null

   private var sound: SoundPlayerManager? = null
   private var speedHeroBullet = 30
   private var speedBotsBullet = 30
   var tankSpeed = 10


   // Life
   private var life1: DynamicObject? = null
   private var life2: DynamicObject? = null
   private var life3: DynamicObject? = null

   private var countDown = 3
   private var score = 0

   // GameOver
   private var isRestart = false

   // endregion

   init {
      locationHero = DynamicObject(
         GameCoordinate(8, 9),
         widthCell,
         heightCell,
         resources,
         R.drawable.tank_hero,
      )
      locationMap["Tank4"] = DynamicObject(
         GameCoordinate(4, 1),
         widthCell,
         widthCell,
         resources,
         R.drawable.cocroach,
         true
      )
      locationMap["Tank4"]!!.rotate(180F)
      locationMap["Tank8"] = DynamicObject(
         GameCoordinate(8, 1),
         widthCell,
         widthCell,
         resources,
         R.drawable.tank_8,
         true
      )
      locationMap["Tank8"]!!.rotate(180F)
      locationMap["Tank12"] = DynamicObject(
         GameCoordinate(12, 1),
         widthCell,
         widthCell,
         resources,
         R.drawable.tank_12,
         true
      )
      locationMap["Tank12"]!!.rotate(180F)
      locationMap["Tank16"] = DynamicObject(
         GameCoordinate(16, 1),
         widthCell,
         widthCell,
         resources,
         R.drawable.tank_16,
         true
      )
      locationMap["Tank16"]!!.rotate(180F)

      locationMap["Tank20"] = DynamicObject(
         GameCoordinate(20, 1),
         widthCell,
         widthCell,
         resources,
         R.drawable.tank_20,
         true
      )
      locationMap["Tank20"]!!.rotate(180F)

      locationPotato = DynamicObject(
         GameCoordinate(10, 9),
         widthCell,
         heightCell,
         resources,
         R.drawable.potato,
      )

      bullet1 = DynamicObject(
         GameCoordinate(
            locationMap["Tank4"]!!.pointStart.x.toInt(),
            locationMap["Tank4"]!!.pointStart.y.toInt()
         ), widthCell / 6, heightCell / 9, resources, R.drawable.bullet, true
      )

      bullet2 = DynamicObject(
         GameCoordinate(
            locationMap["Tank8"]!!.pointStart.x.toInt(),
            locationMap["Tank8"]!!.pointStart.y.toInt()
         ), widthCell / 6, heightCell / 9, resources, R.drawable.bullet, true
      )

      bullet3 = DynamicObject(
         GameCoordinate(
            locationMap["Tank12"]!!.pointStart.x.toInt(),
            locationMap["Tank12"]!!.pointStart.y.toInt()
         ), widthCell / 6, heightCell / 9, resources, R.drawable.bullet, true
      )

      bullet4 = DynamicObject(
         GameCoordinate(
            locationMap["Tank16"]!!.pointStart.x.toInt(),
            locationMap["Tank16"]!!.pointStart.y.toInt()
         ), widthCell / 6, heightCell / 9, resources, R.drawable.bullet, true
      )

      bullet5 = DynamicObject(
         GameCoordinate(
            locationMap["Tank20"]!!.pointStart.x.toInt(),
            locationMap["Tank20"]!!.pointStart.y.toInt()
         ), widthCell / 6, heightCell / 9, resources, R.drawable.bullet, true
      )

      bulletHero = DynamicObject(
         GameCoordinate(
            locationHero!!.pointStart.x.toInt(),
            locationHero!!.pointStart.y.toInt(),
         ), widthCell / 6, heightCell / 9, resources, R.drawable.bullet
      )

      sound = SoundPlayerManager(context)

      life1 =
         DynamicObject(GameCoordinate(14, 9), widthCell, heightCell, resources, R.drawable.ic_life)
      life2 =
         DynamicObject(GameCoordinate(15, 9), widthCell, heightCell, resources, R.drawable.ic_life)
      life3 =
         DynamicObject(GameCoordinate(16, 9), widthCell, heightCell, resources, R.drawable.ic_life)
   }

   companion object {
      var invasiveColl = hashMapOf<String, String>()
   }

   fun draw(canvas: Canvas?, level: Int) {
      if (canvas != null) {
         this.canvas = canvas
      }

      speedLevel(level)

      heroBehavior(canvas)

      potato(canvas)

      botsBehavior(canvas, level)

      life()
   }

   fun musicStatus(value: Boolean) {
      sound = if (value)
         SoundPlayerManager(context)
      else
         null
   }

   private fun speedLevel(level: Int) {
      when (level) {
         in 4..5 -> speedBotsBullet = 35
         in 5..7 -> speedBotsBullet = 43
         in 8..9 -> speedBotsBullet = 50
         10 -> speedBotsBullet = 60
      }
   }

   fun restart() {
      isRestart = true
      setFalse()
      locationMap.forEach {
         it.value.isDestroyed = true
      }
      locationHero!!.isDestroyed = true
   }

   private fun setFalse() {
      locationHero?.isFired = false
      locationMap.forEach {
         it.value.isFired = false
      }
   }

   private fun life() {
      when (countDown) {
         3 -> {
            life1?.drawLife()
            life2?.drawLife()
            life3?.drawLife()
         }
         2 -> {
            life1?.drawLife()
            life2?.drawLife()
         }
         1 -> {
            life1?.drawLife()
         }
      }
   }

   private fun gameOver() {
      activity.onPauseLocal()
      activity.gameOver()
   }

   @SuppressLint("UseCompatLoadingForDrawables")
   private fun DynamicObject.drawLife() {
      canvas.drawBitmap(resizeBitmap, pointStart.x.toFloat(), pointStart.y.toFloat(), paint)
   }

   @SuppressLint("SetTextI18n")
   private fun bulletStartPosition(bullet: DynamicObject?, tank: DynamicObject?) {
      if (bullet != null) {
         if (!tank!!.frozen) {
            if (tank.isFired) {
               val speedBullet: Int = when (tank) {
                  locationHero -> {
                     speedHeroBullet
                  }
                  else -> speedBotsBullet
               }
               when (bullet.rotation) {
                  0F -> bullet.pointCenter.y -= speedBullet
                  90F -> bullet.pointCenter.x += speedBullet
                  -90F -> bullet.pointCenter.x -= speedBullet
                  180F -> bullet.pointCenter.y += speedBullet
               }
               bullet.rotate(bullet.rotation)

               val hit = collision.isHit(bullet)

               if (hit != null) {
                  if (!hit.invulnerable) {
                     if (tank.theirOwn && hit.theirOwn) {
                        sound?.playPick()
                     } else {
                        sound?.playMiniDamaged()
                        val mBitmap = BitmapFactory.decodeResource(resources, R.drawable.explosion)
                        val resizeBitmap =
                           Bitmap.createScaledBitmap(
                              mBitmap,
                              widthCell.toInt(),
                              heightCell.toInt(),
                              false
                           )
                        canvas.drawBitmap(
                           resizeBitmap,
                           hit.pointStart.x.toFloat(),
                           hit.pointStart.y.toFloat(),
                           paint
                        )
                        funOnWhen(hit)
                     }
                  }

                  tank.isFired = false

                  if (tank == locationHero) {
                     locationHero!!.isFired = false
                     if (hit.type == GameObject.Type.Dynamic) {
                        score += 100
                        val text = activity.findViewById<TextView>(R.id.score)
                        activity.runOnUiThread {
                           text.text = "Score: $score"
                        }
                        activity.setScore(score)
                     }
                  }
               } else {
                  bullet.drawBullet()
               }
            }
         }
      }
   }

   private fun funOnWhen(hit: GameObject) {
      when (hit) {
         locationHero -> {
            locationHero!!.isFired = true
            locationHero!!.isDestroyed = false
         }
         locationMap["Tank4"] -> {
            locationMap["Tank4"]!!.isFired = true
            locationMap["Tank4"]!!.isDestroyed = false
         }
         locationMap["Tank8"] -> {
            locationMap["Tank8"]!!.isFired = true
            locationMap["Tank8"]!!.isDestroyed = false
         }
         locationMap["Tank12"] -> {
            locationMap["Tank12"]!!.isFired = true
            locationMap["Tank12"]!!.isDestroyed = false
         }
         locationMap["Tank16"] -> {
            locationMap["Tank16"]!!.isFired = true
            locationMap["Tank16"]!!.isDestroyed = false
         }
         locationMap["Tank20"] -> {
            locationMap["Tank20"]!!.isFired = true
            locationMap["Tank20"]!!.isDestroyed = false
         }
         locationPotato -> {
            locationPotato!!.isDestroyed = false
            sound?.playVot()
         }
      }
   }

   private fun botsBehavior(canvas: Canvas?, level: Int) {
      randomGenerator(canvas, locationMap["Tank4"], "Tank4")
      randomGenerator(canvas, locationMap["Tank8"], "Tank8")
      randomGenerator(canvas, locationMap["Tank12"], "Tank12")
      bulletStartPosition(bullet1, locationMap["Tank4"])
      bulletStartPosition(bullet2, locationMap["Tank8"])
      bulletStartPosition(bullet3, locationMap["Tank12"])
      when {
         level in 4..6 -> {
            randomGenerator(canvas, locationMap["Tank16"], "Tank16")
            bulletStartPosition(bullet4, locationMap["Tank16"])
         }
         level >= 7 -> {
            randomGenerator(canvas, locationMap["Tank16"], "Tank16")
            randomGenerator(canvas, locationMap["Tank20"], "Tank20")
            bulletStartPosition(bullet4, locationMap["Tank16"])
            bulletStartPosition(bullet5, locationMap["Tank20"])
         }
      }
      bulletStartPosition(bulletHero, locationHero)
   }

   private fun potato(canvas: Canvas?){
      locationPotato?.drawAndCollision(
         canvas, "potato",
         locationPotato!!, locationPotato!!.pointStart.x, locationPotato!!.pointStart.y
      )
   }

   private fun heroBehavior(canvas: Canvas?) {
      locationHero?.let {
         val lastLocX = locationHero!!.pointStart.x
         val lastLocY = locationHero!!.pointStart.y

         typeMove?.let {
            when (typeMove) {
               GameField.TypeMove.UP -> {
                  locationHero!!.pointStart.y -= tankSpeed
                  locationHero?.rotate(0F)
               }
               GameField.TypeMove.DOWN -> {
                  locationHero!!.pointStart.y += tankSpeed
                  locationHero?.rotate(180F)
               }
               GameField.TypeMove.LEFT -> {
                  locationHero!!.pointStart.x -= tankSpeed
                  locationHero?.rotate(-90F)
               }
               GameField.TypeMove.RIGHT -> {
                  locationHero!!.pointStart.x += tankSpeed
                  locationHero?.rotate(90F)
               }
               else -> (throw IllegalStateException("Invalid rating param value"))
            }
         }
         locationHero?.drawAndCollision(
            canvas, "mainHero",
            locationHero!!, lastLocX, lastLocY
         )
      }
   }

   private fun randomGenerator(
      canvas: Canvas?,
      dynamicObject: DynamicObject?,
      string: String
   ) {
      val lastLocX = dynamicObject!!.pointStart.x
      val lastLocY = dynamicObject.pointStart.y

      if (!dynamicObject.frozen) {
         when (dynamicObject.randomValue) {
            0 -> {
               dynamicObject.rotate(180F)
               dynamicObject.pointStart.y += tankSpeed
               if (dynamicObject == locationMap["Tank4"]) sound?.playAccident()
            }
            1 -> {
               dynamicObject.rotate(90F)
               dynamicObject.pointStart.x += tankSpeed
            }
            2 -> {
               dynamicObject.rotate(-90F)
               dynamicObject.pointStart.x -= tankSpeed
            }
            3 -> {
               dynamicObject.rotate(0F)
               dynamicObject.pointStart.y -= tankSpeed
               if (dynamicObject == locationMap["Tank4"]) sound?.playEto()
            }
            4 -> {
               dynamicObject.rotate(180F)
               dynamicObject.pointStart.y += tankSpeed
            }
            5 -> {
               dynamicObject.rotate(0F)
               dynamicObject.pointStart.y -= tankSpeed
            }
            6 -> {
               dynamicObject.rotate(90F)
               dynamicObject.pointStart.x += tankSpeed
            }
            7 -> {
               dynamicObject.rotate(-90F)
               dynamicObject.pointStart.x -= tankSpeed
               if (dynamicObject == locationMap["Tank4"]) sound?.playVot()
            }
         }
      }

      dynamicObject.let {
         it.drawAndCollision(
            canvas,
            string,
            it,
            lastLocX, lastLocY
         )
      }
   }

   private fun DynamicObject.drawBullet() {
      rotatedBitmap?.let {
         canvas.drawBitmap(
            it,
            pointCenter.x.toFloat(),
            pointCenter.y.toFloat(),
            paint
         )
      }
   }

   private fun DynamicObject.drawAndCollision(
      canvas: Canvas?,
      s: String,
      dynamicObject: DynamicObject,
      lastLocX: Double,
      lastLocY: Double
   ) {
      if (dynamicObject.isDestroyed) {
         when (s) {
            "mainHero" -> {
               locationHero!!.isDestroyed = false
               if (!isRestart) locationHero!!.isFired = true
            }
            "Tank4" -> {
               locationMap["Tank4"]!!.isDestroyed = false
               if (!isRestart) locationMap["Tank4"]!!.isFired = true
            }
            "Tank8" -> {
               if (!isRestart) locationMap["Tank8"]!!.isFired = true
               locationMap["Tank8"]!!.isDestroyed = false
            }
            "Tank12" -> {
               locationMap["Tank12"]!!.isDestroyed = false
               if (!isRestart) locationMap["Tank12"]!!.isFired = true
            }
            "Tank16" -> {
               locationMap["Tank16"]!!.isDestroyed = false
               if (!isRestart) locationMap["Tank16"]!!.isFired = true
            }
            "Tank20" -> {
               locationMap["Tank20"]!!.isDestroyed = false
               if (!isRestart) locationMap["Tank20"]!!.isFired = true
            }
            "potato" -> {
               locationPotato!!.isDestroyed = false
            }
         }
         when (s) {
            "mainHero" -> {
               locationHero!!.pointStart.x = widthCell * 8 - widthCell
               locationHero!!.pointStart.y = heightCell * 9 - heightCell
            }
            "potato" -> {
               locationPotato!!.pointStart.x = widthCell * 10 - widthCell
               locationPotato!!.pointStart.y = heightCell * 9 - heightCell
            }
            else -> {
               sound?.playDamaged()
               val mBitmap = BitmapFactory.decodeResource(resources, R.drawable.explosion)
               val resizeBitmap =
                  Bitmap.createScaledBitmap(
                     mBitmap,
                     widthCell.toInt(),
                     heightCell.toInt(),
                     false
                  )
               canvas?.drawBitmap(
                  resizeBitmap,
                  dynamicObject.pointStart.x.toFloat(),
                  dynamicObject.pointStart.y.toFloat(),
                  paint
               )
               dynamicObject.pointStart.x =
                  kotlin.math.floor(Math.random() * (widthScreen - widthCell))
               dynamicObject.pointStart.y = 0.0
            }
         }
         invasiveColl.remove(s)
         collision.invasiveCollect.remove(s)
      }

      dynamicObject.leftX = dynamicObject.pointStart.x
      dynamicObject.rightX = dynamicObject.pointStart.x + widthCell
      dynamicObject.upY = dynamicObject.pointStart.y
      dynamicObject.downY = dynamicObject.pointStart.y + widthCell

      collision.listOfDynamicObj[s] = dynamicObject

      if (dynamicObject.pointStart.x < 0) dynamicObject.pointStart.x = 0.0
      if (dynamicObject.pointStart.y < 0) dynamicObject.pointStart.y = 0.0
      if (dynamicObject.pointStart.x > widthScreen - widthCell) dynamicObject.pointStart.x =
         widthScreen - widthCell
      if (dynamicObject.pointStart.y > heightScreen - widthCell) dynamicObject.pointStart.y =
         heightScreen - widthCell

      val isStopped = collision.isStumbled(dynamicObject, s)

      val stumbledWithBonus = collision.stumbledWithBonus(dynamicObject)
      stumbledWithBonus?.let {
         if (s == "mainHero") {
            sound?.playBonus()
            when (stumbledWithBonus) {
               "armour" -> {
                  var bitmap: Bitmap =
                     BitmapFactory.decodeResource(resources, R.drawable.tank_hero_armoured)
                  dynamicObject.resizeBitmap = Bitmap.createScaledBitmap(
                     bitmap,
                     widthCell.toInt(),
                     widthCell.toInt(),
                     false
                  )!!
                  dynamicObject.invulnerable = true
                  activity.runOnUiThread {
                     object : CountDownTimer(8000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {

                        }

                        override fun onFinish() {
                           dynamicObject.invulnerable = false
                           bitmap = BitmapFactory.decodeResource(resources, R.drawable.tank_hero)
                           dynamicObject.resizeBitmap = Bitmap.createScaledBitmap(
                              bitmap,
                              widthCell.toInt(),
                              widthCell.toInt(),
                              false
                           )!!
                        }
                     }.start()
                  }
               }
               "frozen" -> {
                  locationMap.forEach {
                     it.value.frozen = true
                  }
                  activity.runOnUiThread {
                     object : CountDownTimer(7000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {

                        }

                        override fun onFinish() {
                           locationMap.forEach {
                              it.value.frozen = false
                           }
                        }
                     }.start()
                  }
               }
               "he" -> {
                  locationMap.forEach {
                     it.value.isDestroyed = true
                  }
               }
               "life" -> {
                  if (countDown in 1..2)
                     countDown++
               }
               "protect" -> {
                  immovableRenderingManager.protectCockroach(true)
               }
               "star" -> {
                  speedHeroBullet = 50
               }
            }
         }
      }

      val bMap: Bitmap = rotatedBitmap ?: resizeBitmap

      if (isStopped) {
         val value = Random.nextInt(7)
         when (s) {
            "Tank8" -> {
               locationMap["Tank8"]!!.randomValue = value
            }
            "Tank12" -> {
               locationMap["Tank12"]!!.randomValue = value
            }
            "Tank4" -> {
               locationMap["Tank4"]!!.randomValue = value
            }
            "Tank16" -> {
               locationMap["Tank16"]!!.randomValue = value
            }
            "Tank20" -> {
               locationMap["Tank20"]!!.randomValue = value
            }
         }

         dynamicObject.pointStart.x = lastLocX
         dynamicObject.pointStart.y = lastLocY
      }

      val invasive = invasiveColl[s]
      if (s == invasive) {
         when (s) {
            "potato" -> {
               sound?.playLoose()
               locationPotato!!.isDestroyed = true
               setFalse()
               gameOver()
            }
            "mainHero" -> {
               locationHero!!.isFired = false
               locationHero!!.isDestroyed = true
               countDown--
               speedHeroBullet = 30
               if (countDown == 0) {
                  sound?.playLoose()
                  setFalse()
                  gameOver()
               }
            }
            "Tank4" -> {
               locationMap["Tank4"]!!.isFired = false
               locationMap["Tank4"]!!.isDestroyed = true
            }
            "Tank8" -> {
               locationMap["Tank8"]!!.isFired = false
               locationMap["Tank8"]!!.isDestroyed = true
            }
            "Tank12" -> {
               locationMap["Tank12"]!!.isFired = false
               locationMap["Tank12"]!!.isDestroyed = true
            }
            "Tank16" -> {
               locationMap["Tank16"]!!.isFired = false
               locationMap["Tank16"]!!.isDestroyed = true
            }
            "Tank20" -> {
               locationMap["Tank20"]!!.isFired = false
               locationMap["Tank20"]!!.isDestroyed = true
            }
         }
      } else {
         canvas?.drawBitmap(
            bMap,
            dynamicObject.pointStart.x.toFloat(),
            dynamicObject.pointStart.y.toFloat(),
            paint
         )
      }
   }

   //region ------------ ^_^ ----------- I
   override fun setOnTouchClickFirst(typeMove: GameField.TypeMove?) {
      this.typeMove = typeMove
   }

   override fun setOnTickTimerFirst(value: Int) {
      locationMap["Tank4"]!!.randomValue = value
   }

   override fun setOnFinishTimerFirst(b: Boolean) {
      locationMap["Tank4"]!!.isFired = b
      bullet1!!.rotation = locationMap["Tank4"]!!.rotation
      val accuratePosition =
         AccuratePosition.calculating(
            bullet1!!.rotation,
            locationMap["Tank4"],
            widthCell,
            heightCell
         )
      bullet1!!.pointCenter.x = accuratePosition.x
      bullet1!!.pointCenter.y = accuratePosition.y
      if (!locationMap["Tank4"]!!.isDestroyed && !locationMap["Tank4"]!!.frozen)
         sound?.playShot()
   }

   // ------------ ^_^ ----------- II
   override fun setOnTickTSec(value: Int) {
      locationMap["Tank8"]!!.randomValue = value
   }

   override fun setOnFinishTimerSec(b: Boolean) {
      locationMap["Tank8"]!!.isFired = b
      bullet2!!.rotation = locationMap["Tank8"]!!.rotation
      val accuratePosition =
         AccuratePosition.calculating(
            bullet2!!.rotation,
            locationMap["Tank8"],
            widthCell,
            heightCell
         )
      bullet2!!.pointCenter.x = accuratePosition.x
      bullet2!!.pointCenter.y = accuratePosition.y
      if (!locationMap["Tank8"]!!.isDestroyed && !locationMap["Tank8"]!!.frozen)
         sound?.playShot2()
   }

   // ------------ ^_^ ----------- III
   override fun setOnTickTimerThird(value: Int) {
      locationMap["Tank12"]!!.randomValue = value
   }

   override fun setOnFinishTimerThird(b: Boolean) {
      locationMap["Tank12"]!!.isFired = b
      bullet3!!.rotation = locationMap["Tank12"]!!.rotation
      val accuratePosition =
         AccuratePosition.calculating(
            bullet3!!.rotation,
            locationMap["Tank12"],
            widthCell,
            heightCell
         )
      bullet3!!.pointCenter.x = accuratePosition.x
      bullet3!!.pointCenter.y = accuratePosition.y
      if (!locationMap["Tank12"]!!.isDestroyed && !locationMap["Tank12"]!!.frozen)
         sound?.playShot3()
   }

   // ------------ ^_^ ----------- IV
   override fun setOnTickTimerFour(value: Int) {
      locationMap["Tank16"]!!.randomValue = value
   }

   override fun setOnFinishTimerFour(b: Boolean) {
      locationMap["Tank16"]!!.isFired = b
      bullet4!!.rotation = locationMap["Tank16"]!!.rotation
      val accuratePosition =
         AccuratePosition.calculating(
            bullet4!!.rotation,
            locationMap["Tank16"],
            widthCell,
            heightCell
         )
      bullet4!!.pointCenter.x = accuratePosition.x
      bullet4!!.pointCenter.y = accuratePosition.y
      if (!locationMap["Tank16"]!!.isDestroyed && !locationMap["Tank16"]!!.frozen)
         sound?.playShot4()
   }

   // ------------ ^_^ -----------  V
   override fun setOnTickTimerFive(value: Int) {
      locationMap["Tank20"]!!.randomValue = value
   }

   override fun setOnFinishTimerFive(b: Boolean) {
      locationMap["Tank20"]!!.isFired = b
      bullet5!!.rotation = locationMap["Tank20"]!!.rotation
      val accuratePosition =
         AccuratePosition.calculating(
            bullet5!!.rotation,
            locationMap["Tank20"],
            widthCell,
            heightCell
         )
      bullet5!!.pointCenter.x = accuratePosition.x
      bullet5!!.pointCenter.y = accuratePosition.y
      if (!locationMap["Tank20"]!!.isDestroyed && !locationMap["Tank20"]!!.frozen)
         sound?.playShot5()
   }

   // ------------ ^_^ -----------  MainHero
   override fun onClick(value: Boolean) {
      locationHero?.let {
         locationHero!!.isFired = value
         bulletHero!!.rotation = locationHero!!.rotation
         val accuratePosition =
            AccuratePosition.calculating(bulletHero!!.rotation, locationHero, widthCell, heightCell)
         bulletHero!!.pointCenter.x = accuratePosition.x
         bulletHero!!.pointCenter.y = accuratePosition.y
      }
   }
// endregion
}