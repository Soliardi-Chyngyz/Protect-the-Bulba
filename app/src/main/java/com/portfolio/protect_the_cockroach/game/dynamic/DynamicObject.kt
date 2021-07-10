package com.portfolio.protect_the_cockroach.game.dynamic

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import com.portfolio.protect_the_cockroach.game.model.GameCoordinate
import com.portfolio.protect_the_cockroach.game.model.GameObject
import com.portfolio.protect_the_cockroach.game.utils.ImageRotationUtility

class DynamicObject(
   coordinate: GameCoordinate? = null,
   widthCell: Double = 0.0,
   heightCell: Double = 0.0,
   resources: Resources? = null,
   @DrawableRes idBitmap: Int = 0,
   rotation: Float = 0f
) : GameObject(
   coordinate,
   widthCell,
   heightCell,
) {

   var type: Type? = null
   var rot: Float = 0F

   val bitmap = BitmapFactory.decodeResource(resources, idBitmap)
   val resizeBitmap =
      Bitmap.createScaledBitmap(bitmap, widthCell.toInt(), widthCell.toInt(), false)!!
   val rotatedBitmap = ImageRotationUtility.rotateBitmap(resizeBitmap, rot)

   enum class Type {
      Vulnerable,
      Invulnerable
   }
}