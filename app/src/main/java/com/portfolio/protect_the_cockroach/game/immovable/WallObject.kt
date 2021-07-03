package com.portfolio.protect_the_cockroach.game.immovable

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import com.portfolio.protect_the_cockroach.game.model.GameCoordinate
import com.portfolio.protect_the_cockroach.game.model.GameObject

class WallObject(coordinate: GameCoordinate, widthCell: Double, heightCell: Double, resources: Resources, @DrawableRes idBitmap: Int) : GameObject(
   coordinate,
   widthCell,
   heightCell,
) {

    var type: Type? = null
    var bitmap: Bitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(resources, idBitmap), 0, 0, widthCell.toInt(), heightCell.toInt())

    enum class Type {
        Vulnerable,
        Invulnerable
    }
}