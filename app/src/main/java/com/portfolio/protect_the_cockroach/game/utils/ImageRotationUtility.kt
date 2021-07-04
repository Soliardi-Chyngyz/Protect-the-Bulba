package com.portfolio.protect_the_cockroach.game.utils

import android.graphics.Bitmap
import android.graphics.Matrix

object ImageRotationUtility {
   fun rotateBitmap(source: Bitmap, angle: Float): Bitmap {
      val matrix = Matrix()
      matrix.postRotate(angle)
      return Bitmap.createBitmap(
         source, 0, 0, source.width, source.height, matrix, true
      )
   }
}