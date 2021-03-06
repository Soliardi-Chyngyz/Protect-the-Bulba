package com.portfolio.protect_the_cockroach.game.immovable

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import com.portfolio.protect_the_cockroach.game.model.GameCoordinate
import com.portfolio.protect_the_cockroach.game.model.GameObject

class WallObject(
   coordinate: GameCoordinate,
   widthCell: Double,
   heightCell: Double,
   resources: Resources,
   @DrawableRes idBitmap: Int,
   invulnerable: Boolean = false,
   val bonusName: String = ""
) : GameObject(
   coordinate,
   widthCell,
   heightCell,
   invulnerable,
   Type.Static,
   false
) {

   private val bitmap: Bitmap = BitmapFactory.decodeResource(resources, idBitmap)
   val resizeBitmap =
      Bitmap.createScaledBitmap(bitmap, widthCell.toInt(), heightCell.toInt(), false)!!
}