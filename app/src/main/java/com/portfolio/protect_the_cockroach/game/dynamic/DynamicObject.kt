package com.portfolio.protect_the_cockroach.game.dynamic

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import com.portfolio.protect_the_cockroach.game.model.GameCoordinate
import com.portfolio.protect_the_cockroach.game.model.GameObject
import com.portfolio.protect_the_cockroach.game.utils.ImageRotationUtility

class DynamicObject(
   coordinate: GameCoordinate,
   widthCell: Double,
   heightCell: Double,
   resources: Resources,
   @DrawableRes idBitmap: Int,
   rotation: Float
) : GameObject(
   coordinate,
   widthCell,
   heightCell,
) {

   var type: Type? = null

   val bitmap = BitmapFactory.decodeResource(resources, idBitmap)
   val resizeBitmap =
      Bitmap.createScaledBitmap(bitmap, widthCell.toInt(), widthCell.toInt(), false)!!
   val rotatedBitmap = ImageRotationUtility.rotateBitmap(resizeBitmap, rotation)

   enum class Type {
      Vulnerable,
      Invulnerable
   }
}