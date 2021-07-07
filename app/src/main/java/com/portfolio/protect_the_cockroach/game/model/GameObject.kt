package com.portfolio.protect_the_cockroach.game.model

abstract class GameObject(
    coordinate: GameCoordinate?,
    widthCell: Double,
    heightCell: Double,
) {


    var pointCenter: GamePoint = GamePoint()

    var upLeftX: Double
    var upRightX: Double
    var upLeftY: Double
    var downLeftY: Double

    var pointStart: GamePoint = GamePoint()
    var pointEnd: GamePoint = GamePoint()

    var widthCell: Double = widthCell
    var heightCell: Double = heightCell

    init {
        pointCenter = GamePoint((widthCell * coordinate!!.x) - (widthCell / 2), (heightCell * coordinate.y) - (heightCell / 2))

        upLeftX = widthCell * coordinate.x - widthCell
        upRightX = widthCell * coordinate.x
        upLeftY = heightCell * coordinate.y - heightCell
        downLeftY = heightCell * coordinate.y

        pointStart = GamePoint((widthCell * coordinate.x) - (widthCell), (heightCell * coordinate.y) - (heightCell))
        pointEnd = GamePoint((widthCell * coordinate.x), (heightCell * coordinate.y))
    }
}

