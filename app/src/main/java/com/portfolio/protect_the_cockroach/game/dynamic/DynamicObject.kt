package com.portfolio.protect_the_cockroach.game.dynamic

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import com.portfolio.protect_the_cockroach.game.model.GameCoordinate
import com.portfolio.protect_the_cockroach.game.model.GameObject
import com.portfolio.protect_the_cockroach.game.utils.ImageRotationUtility

open class DynamicObject(
   coordinate: GameCoordinate? = null,
   widthCell: Double = 0.0,
   heightCell: Double = 0.0,
   resources: Resources? = null,
   @DrawableRes idBitmap: Int = 0,
   theirOwn: Boolean = false,
   frozen: Boolean = false,
) : GameObject(
   coordinate,
   widthCell,
   heightCell,
   false,
   Type.Dynamic,
   theirOwn
) {
   var frozen = frozen
   var img = idBitmap
   var rotatedBitmap: Bitmap? = null
   var rotation: Float = 0f

   private val bitmap: Bitmap = BitmapFactory.decodeResource(resources, img)
   var resizeBitmap =
      Bitmap.createScaledBitmap(bitmap, widthCell.toInt(), widthCell.toInt(), false)!!

   fun rotate(rot: Float) {
      rotatedBitmap = ImageRotationUtility.rotateBitmap(resizeBitmap, rot)
      rotation = rot
   }
}