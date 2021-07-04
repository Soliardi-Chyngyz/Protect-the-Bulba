package com.portfolio.protect_the_cockroach.game.immovable

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import com.portfolio.protect_the_cockroach.game.model.GameCoordinate
import com.portfolio.protect_the_cockroach.game.model.GameObject
import com.portfolio.protect_the_cockroach.game.utils.ImageRotationUtility

class WallObject(
   coordinate: GameCoordinate,
   widthCell: Double,
   heightCell: Double,
   resources: Resources,
   @DrawableRes idBitmap: Int
) : GameObject(
   coordinate,
   widthCell,
   heightCell,
) {

   var type: Type? = null

   val bitmap = BitmapFactory.decodeResource(resources, idBitmap)
   val resizeBitmap =
      Bitmap.createScaledBitmap(bitmap, widthCell.toInt(), heightCell.toInt(), false)!!

   enum class Type {
      Vulnerable,
      Invulnerable
   }
}