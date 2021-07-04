package com.portfolio.protect_the_cockroach.game.manager

import android.content.res.Resources
import android.graphics.*
import com.portfolio.protect_the_cockroach.R
import com.portfolio.protect_the_cockroach.game.base.BaseManager
import com.portfolio.protect_the_cockroach.game.immovable.WallObject
import com.portfolio.protect_the_cockroach.game.model.GameCoordinate

class ImmovableRenderingManager(widthScreen: Double, heightScreen: Double, var resources: Resources): BaseManager(widthScreen, heightScreen) {

    private val tableLinePaint: Paint = run {
        val paint = Paint()
        paint.color = Color.WHITE
        paint.strokeWidth = 1f
        return@run paint
    }

    fun draw(canvas: Canvas?) {
        drawTableLine(canvas)
        drawWalls(canvas)
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

    private fun drawWalls(canvas: Canvas?) {
        WallObject(GameCoordinate(2, 2), widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas)
        WallObject(GameCoordinate(2, 3), widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas)
        WallObject(GameCoordinate(3, 2), widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas)
        WallObject(GameCoordinate(16, 2), widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas)
        WallObject(GameCoordinate(17, 2), widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas)
        WallObject(GameCoordinate(17, 3), widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas)

        WallObject(GameCoordinate(8, 4), widthCell, heightCell, resources, R.drawable.beton).drawWall(canvas)
        WallObject(GameCoordinate(9, 4), widthCell, heightCell, resources, R.drawable.beton).drawWall(canvas)
        WallObject(GameCoordinate(10, 4), widthCell, heightCell, resources, R.drawable.beton).drawWall(canvas)
        WallObject(GameCoordinate(11, 4), widthCell, heightCell, resources, R.drawable.beton).drawWall(canvas)
        WallObject(GameCoordinate(12, 4), widthCell, heightCell, resources, R.drawable.beton).drawWall(canvas)

        WallObject(GameCoordinate(9, 9), widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas)
        WallObject(GameCoordinate(11, 9), widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas)
        WallObject(GameCoordinate(9, 8), widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas)
        WallObject(GameCoordinate(10, 8), widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas)
        WallObject(GameCoordinate(11, 8), widthCell, heightCell, resources, R.drawable.kirpich).drawWall(canvas)
    }

    private fun WallObject.drawWall(canvas: Canvas?) {
        canvas?.drawBitmap(resizeBitmap, pointStart.x.toFloat(), pointStart.y.toFloat(), Paint())
    }
}