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

   private var isFire1 = false
   private var isFire2 = false
   private var isFire3 = false
   private var isFire4 = false
   private var isFire5 = false
   private var isFireHero = false
   private var randomValue1 = 10
   private var randomValue2 = 10
   private var randomValue3 = 10
   private var randomValue4 = 10
   private var randomValue5 = 10

   // Tanks
   private var locationHero: DynamicObject? = null
   private var locationCockroach: DynamicObject? = null
   private var locationMap = mutableMapOf<String, DynamicObject>()

   // Bullets rotation
   private var rotation1 = 0F
   private var rotation2 = 0F
   private var rotation3 = 0F
   private var rotation4 = 0F
   private var rotation5 = 0F
   private var rotationHero = 0F

   // Bullets
   private var bullet: DynamicObject? = null
   private var bullet2: DynamicObject? = null
   private var bullet3: DynamicObject? = null
   private var bullet4: DynamicObject? = null
   private var bullet5: DynamicObject? = null
   private var bulletHero: DynamicObject? = null

   private var sound: SoundPlayerManager? = null
   private var speedHeroBullet = 30

   // Life
   private var life1: DynamicObject? = null
   private var life2: DynamicObject? = null
   private var life3: DynamicObject? = null

   private var coundDown = 3
   private var score = 0
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
         R.drawable.tank_4,
      )
      locationMap["Tank4"]!!.rotate(180F)
      locationMap["Tank8"] = DynamicObject(
         GameCoordinate(8, 1),
         widthCell,
         widthCell,
         resources,
         R.drawable.tank_8,
      )
      locationMap["Tank8"]!!.rotate(180F)
      locationMap["Tank12"] = DynamicObject(
         GameCoordinate(12, 1),
         widthCell,
         widthCell,
         resources,
         R.drawable.tank_12,
      )
      locationMap["Tank12"]!!.rotate(180F)
      locationMap["Tank16"] = DynamicObject(
         GameCoordinate(16, 1),
         widthCell,
         widthCell,
         resources,
         R.drawable.tank_16,
      )
      locationMap["Tank16"]!!.rotate(180F)
      locationMap["Tank20"] = DynamicObject(
         GameCoordinate(20, 1),
         widthCell,
         widthCell,
         resources,
         R.drawable.tank_20,
      )
      locationMap["Tank20"]!!.rotate(180F)
      bullet = DynamicObject(
         GameCoordinate(
            locationMap["Tank4"]!!.pointStart.x.toInt(),
            locationMap["Tank4"]!!.pointStart.y.toInt()
         ), widthCell / 6, heightCell / 9, resources, R.drawable.bullet
      )

      bullet2 = DynamicObject(
         GameCoordinate(
            locationMap["Tank8"]!!.pointStart.x.toInt(),
            locationMap["Tank8"]!!.pointStart.y.toInt()
         ), widthCell / 6, heightCell / 9, resources, R.drawable.bullet
      )

      bullet3 = DynamicObject(
         GameCoordinate(
            locationMap["Tank12"]!!.pointStart.x.toInt(),
            locationMap["Tank12"]!!.pointStart.y.toInt()
         ), widthCell / 6, heightCell / 9, resources, R.drawable.bullet
      )

      bullet4 = DynamicObject(
         GameCoordinate(
            locationMap["Tank16"]!!.pointStart.x.toInt(),
            locationMap["Tank16"]!!.pointStart.y.toInt()
         ), widthCell / 6, heightCell / 9, resources, R.drawable.bullet
      )

      bullet5 = DynamicObject(
         GameCoordinate(
            locationMap["Tank20"]!!.pointStart.x.toInt(),
            locationMap["Tank20"]!!.pointStart.y.toInt()
         ), widthCell / 6, heightCell / 9, resources, R.drawable.bullet
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

      heroBehavior(canvas)

      cockroachBehavior(canvas)

      botsBehavior(canvas, level)

      life()
   }

   private fun life() {
      when (coundDown) {
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
         0 -> {
            11
         }
      }
   }

   @SuppressLint("UseCompatLoadingForDrawables")
   private fun DynamicObject.drawLife() {
      canvas.drawBitmap(resizeBitmap, pointStart.x.toFloat(), pointStart.y.toFloat(), paint)
   }

   @SuppressLint("SetTextI18n")
   private fun bulletStartPosition(
      bullet: DynamicObject?,
      tank: DynamicObject?,
      rotation: Float,
      isFire: Boolean
   ) {
      if (!tank!!.frozen) {
         if (isFire) {
            var speedBullet = 30
            when (tank) {
               locationHero -> {
                  speedBullet = speedHeroBullet
               }
            }
            when (rotation) {
               0F -> bullet!!.pointCenter.y -= speedBullet
               90F -> bullet!!.pointCenter.x += speedBullet
               -90F -> bullet!!.pointCenter.x -= speedBullet
               180F -> bullet!!.pointCenter.y += speedBullet
            }
            bullet!!.rotate(rotation)

            val hit = collision.isHit(bullet)

            if (hit != null) {
               if (!hit.invulnerable) {
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
               }

               funOnWhen(hit)

               when (tank) {
                  locationMap["Tank4"] -> {
                     isFire1 = false
                  }
                  locationMap["Tank8"] -> {
                     isFire2 = false
                  }
                  locationMap["Tank12"] -> isFire3 = false
                  locationMap["Tank16"] -> isFire4 = false
                  locationMap["Tank20"] -> isFire5 = false
                  locationHero -> {
                     isFireHero = false
                     if (hit.type == GameObject.Type.Dynamic) {
                        score += 100
                        val text = activity.findViewById<TextView>(R.id.score)
                        activity.runOnUiThread {
                           text.text = "Score: $score"
                        }
                        activity.setScore(score)
                     }
                  }
               }
            } else {
               bullet.drawBullet()
            }
         }
      }
   }

   private fun funOnWhen(hit: GameObject) {
      when (hit) {
         locationHero -> {
            isFireHero = true
            locationHero!!.isDestroyed = false
         }
         locationMap["Tank4"] -> {
            isFire1 = true
            locationMap["Tank4"]!!.isDestroyed = false
         }
         locationMap["Tank8"] -> {
            isFire2 = true
            locationMap["Tank8"]!!.isDestroyed = false
         }
         locationMap["Tank12"] -> {
            isFire3 = true
            locationMap["Tank12"]!!.isDestroyed = false
         }
         locationMap["Tank16"] -> {
            isFire4 = true
            locationMap["Tank16"]!!.isDestroyed = false
         }
         locationMap["Tank20"] -> {
            isFire5 = true
            locationMap["Tank20"]!!.isDestroyed = false
         }
         locationCockroach -> {
            sound?.playVot()
         }
      }
   }

   private fun botsBehavior(canvas: Canvas?, level: Int) {
      randomGenerator(canvas, locationMap["Tank4"], randomValue1, isFire1, "Tank4")
      randomGenerator(canvas, locationMap["Tank8"], randomValue2, isFire2, "Tank8")
      randomGenerator(canvas, locationMap["Tank12"], randomValue3, isFire3, "Tank12")
      bulletStartPosition(bullet, locationMap["Tank4"], rotation1, isFire1)
      bulletStartPosition(bullet2, locationMap["Tank8"], rotation2, isFire2)
      bulletStartPosition(bullet3, locationMap["Tank12"], rotation3, isFire3)
      when {
         level in 4..6 -> {
            randomGenerator(canvas, locationMap["Tank16"], randomValue4, isFire4, "Tank16")
            bulletStartPosition(bullet4, locationMap["Tank16"], rotation4, isFire4)
         }
         level >= 7 -> {
            randomGenerator(canvas, locationMap["Tank16"], randomValue4, isFire4, "Tank16")
            randomGenerator(canvas, locationMap["Tank20"], randomValue5, isFire5, "Tank20")
            bulletStartPosition(bullet4, locationMap["Tank16"], rotation4, isFire4)
            bulletStartPosition(bullet5, locationMap["Tank20"], rotation5, isFire5)
         }
      }
      bulletStartPosition(bulletHero, locationHero, rotationHero, isFireHero)
   }

   private fun cockroachBehavior(canvas: Canvas?) {
      locationCockroach = DynamicObject(
         GameCoordinate(10, 9),
         widthCell,
         heightCell,
         resources,
         R.drawable.cocroach,
      )
      val lastLocX = locationCockroach!!.pointStart.x
      val lastLocY = locationCockroach!!.pointStart.y

      when (randomValue3) {
         5 -> {
            locationCockroach!!.rotate(180F)
            locationCockroach!!.pointStart.y += 15.0
         }
         4 -> {
            locationCockroach!!.rotate(90F)
            locationCockroach!!.pointStart.x += 15.0
         }
         3 -> {
            locationCockroach!!.rotate(-90F)
            locationCockroach!!.pointStart.x -= 15.0
         }
         2 -> {
            locationCockroach!!.rotate(0F)
            locationCockroach!!.pointStart.y -= 15.0
            sound?.playEto()
         }
         1 -> {
            locationCockroach!!.rotate(180F)
            locationCockroach!!.pointStart.y += 15.0
         }
         0 -> {
            locationCockroach!!.rotate(0F)
            locationCockroach!!.pointStart.y -= 15.0
         }
         6 -> {
            locationCockroach!!.rotate(-90F)
            locationCockroach!!.pointStart.x -= 15.0
            sound!!.playKatastrofa()
         }
         7 -> {
            locationCockroach!!.rotate(90F)
            locationCockroach!!.pointStart.x += 15.0
         }
      }

      locationCockroach!!.drawAndCollision(
         canvas, "cockroach",
         locationCockroach!!, lastLocX, lastLocY, true
      )
   }

   private fun heroBehavior(canvas: Canvas?) {
      val lastLocX = locationHero!!.pointStart.x
      val lastLocY = locationHero!!.pointStart.y

      typeMove?.let {
         when (typeMove) {
            GameField.TypeMove.UP -> {
               locationHero!!.pointStart.y -= 15
               locationHero?.rotate(0F)
            }
            GameField.TypeMove.DOWN -> {
               locationHero!!.pointStart.y += 15
               locationHero?.rotate(180F)
            }
            GameField.TypeMove.LEFT -> {
               locationHero!!.pointStart.x -= 15
               locationHero?.rotate(-90F)
            }
            GameField.TypeMove.RIGHT -> {
               locationHero!!.pointStart.x += 15
               locationHero?.rotate(90F)
            }
            else -> (throw IllegalStateException("Invalid rating param value"))
         }
      }
      locationHero?.drawAndCollision(
         canvas,
         "mainHero",
         locationHero!!,
         lastLocX,
         lastLocY,
         isFireHero
      )
   }

   private fun randomGenerator(
      canvas: Canvas?,
      dynamicObject: DynamicObject?,
      randomValue: Int,
      isFire: Boolean,
      string: String
   ) {
      val lastLocX = dynamicObject!!.pointStart.x
      val lastLocY = dynamicObject.pointStart.y

      if (!dynamicObject.frozen) {
         when (randomValue) {
            0 -> {
               dynamicObject.rotate(180F)
               dynamicObject.pointStart.y += 15.0
            }
            1 -> {
               dynamicObject.rotate(90F)
               dynamicObject.pointStart.x += 15.0
            }
            2 -> {
               dynamicObject.rotate(-90F)
               dynamicObject.pointStart.x -= 15.0
            }
            3 -> {
               dynamicObject.rotate(0F)
               dynamicObject.pointStart.y -= 15.0
            }
            4 -> {
               dynamicObject.rotate(180F)
               dynamicObject.pointStart.y += 15.0
            }
            5 -> {
               dynamicObject.rotate(0F)
               dynamicObject.pointStart.y -= 15.0
            }
            6 -> {
               dynamicObject.rotate(90F)
               dynamicObject.pointStart.x += 15.0
            }
            7 -> {
               dynamicObject.rotate(-90F)
               dynamicObject.pointStart.x -= 15.0
            }
         }
      }

      dynamicObject.let {
         it.drawAndCollision(
            canvas,
            string,
            it,
            lastLocX, lastLocY,
            isFire
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
      lastLocY: Double,
      isFire: Boolean
   ) {
      if (dynamicObject.isDestroyed) {
         when (s) {
            "mainHero" -> {
               isFireHero = true
               locationHero!!.isDestroyed = false
            }
            "Tank4" -> {
               isFire1 = true
               locationMap["Tank4"]!!.isDestroyed = false
            }
            "Tank8" -> {
               isFire2 = true
               locationMap["Tank8"]!!.isDestroyed = false
            }
            "Tank12" -> {
               isFire3 = true
               locationMap["Tank12"]!!.isDestroyed = false
            }
            "Tank16" -> {
               isFire4 = true
               locationMap["Tank16"]!!.isDestroyed = false
            }
            "Tank20" -> {
               isFire5 = true
               locationMap["Tank20"]!!.isDestroyed = false
            }
         }
         if (s == "mainHero") {
            locationHero!!.pointStart.x = widthCell * 8 - widthCell
            locationHero!!.pointStart.y = heightCell * 9 - heightCell
         } else {
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
                  if(coundDown in 1..2)
                     coundDown ++
               }
               "lopata" -> {
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
         dynamicObject.pointStart.x = lastLocX
         dynamicObject.pointStart.y = lastLocY
      }

      val invasive = invasiveColl[s]
      if (s == invasive) {
         when (s) {
            "cockroach" -> {

            }
            "mainHero" -> {
               isFireHero = false
               locationHero!!.isDestroyed = true
               coundDown--
               speedHeroBullet = 30
            }
            "Tank4" -> {
               isFire1 = false
               locationMap["Tank4"]!!.isDestroyed = true
            }
            "Tank8" -> {
               isFire2 = false
               locationMap["Tank8"]!!.isDestroyed = true
            }
            "Tank12" -> {
               isFire3 = false
               locationMap["Tank12"]!!.isDestroyed = true
            }
            "Tank16" -> {
               isFire4 = false
               locationMap["Tank16"]!!.isDestroyed = true
            }
            "Tank20" -> {
               isFire5 = false
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
      this.randomValue1 = value
   }

   override fun setOnFinishTimerFirst(b: Boolean) {
      isFire1 = b
      rotation1 = locationMap["Tank4"]!!.rotation
      val accuratePosition =
         AccuratePosition.calculating(rotation1, locationMap["Tank4"], widthCell, heightCell)
      bullet!!.pointCenter.x = accuratePosition.x
      bullet!!.pointCenter.y = accuratePosition.y
      if (!locationMap["Tank4"]!!.isDestroyed && !locationMap["Tank4"]!!.frozen)
         sound!!.playShot()
   }

   // ------------ ^_^ ----------- II
   override fun setOnTickTSec(value: Int) {
      this.randomValue2 = value
   }

   override fun setOnFinishTimerSec(b: Boolean) {
      isFire2 = b
      rotation2 = locationMap["Tank8"]!!.rotation
      val accuratePosition =
         AccuratePosition.calculating(rotation2, locationMap["Tank8"], widthCell, heightCell)
      bullet2!!.pointCenter.x = accuratePosition.x
      bullet2!!.pointCenter.y = accuratePosition.y
      if (!locationMap["Tank8"]!!.isDestroyed && !locationMap["Tank8"]!!.frozen)
         sound!!.playShot2()
   }

   // ------------ ^_^ ----------- III
   override fun setOnTickTimerThird(value: Int) {
      randomValue3 = value
   }

   override fun setOnFinishTimerThird(b: Boolean) {
      isFire3 = b
      rotation3 = locationMap["Tank12"]!!.rotation
      val accuratePosition =
         AccuratePosition.calculating(rotation3, locationMap["Tank12"], widthCell, heightCell)
      bullet3!!.pointCenter.x = accuratePosition.x
      bullet3!!.pointCenter.y = accuratePosition.y
      if (!locationMap["Tank12"]!!.isDestroyed && !locationMap["Tank12"]!!.frozen)
         sound!!.playShot3()
   }

   // ------------ ^_^ ----------- IV
   override fun setOnTickTimerFour(value: Int) {
      this.randomValue4 = value
   }

   override fun setOnFinishTimerFour(b: Boolean) {
      isFire4 = b
      rotation4 = locationMap["Tank16"]!!.rotation
      val accuratePosition =
         AccuratePosition.calculating(rotation4, locationMap["Tank16"], widthCell, heightCell)
      bullet4!!.pointCenter.x = accuratePosition.x
      bullet4!!.pointCenter.y = accuratePosition.y
      if (!locationMap["Tank16"]!!.isDestroyed && !locationMap["Tank16"]!!.frozen)
         sound!!.playShot4()
   }

   // ------------ ^_^ -----------  V
   override fun setOnTickTimerFive(value: Int) {
      randomValue5 = value
   }

   override fun setOnFinishTimerFive(b: Boolean) {
      isFire5 = b
      rotation5 = locationMap["Tank20"]!!.rotation
      val accuratePosition =
         AccuratePosition.calculating(rotation5, locationMap["Tank20"], widthCell, heightCell)
      bullet5!!.pointCenter.x = accuratePosition.x
      bullet5!!.pointCenter.y = accuratePosition.y
      if (!locationMap["Tank20"]!!.isDestroyed && !locationMap["Tank20"]!!.frozen)
         sound!!.playShot5()
   }

   // ------------ ^_^ -----------  MainHero
   override fun onClick(value: Boolean) {
      isFireHero = value
      rotationHero = locationHero!!.rotation
      val accuratePosition =
         AccuratePosition.calculating(rotationHero, locationHero, widthCell, heightCell)
      bulletHero!!.pointCenter.x = accuratePosition.x
      bulletHero!!.pointCenter.y = accuratePosition.y
   }
   // endregion
}