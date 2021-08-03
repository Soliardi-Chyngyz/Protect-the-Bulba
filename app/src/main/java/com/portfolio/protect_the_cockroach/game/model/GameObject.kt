package com.portfolio.protect_the_cockroach.game.model

abstract class GameObject(
    coordinate: GameCoordinate?,
    var widthCell: Double,
    var heightCell: Double,
    var invulnerable: Boolean,
    type: Type,
    var theirOwn: Boolean,
) {

    var pointCenter: GamePoint = GamePoint()

    var leftX: Double
    var rightX: Double
    var upY: Double
    var downY: Double

    var pointStart: GamePoint = GamePoint()
    private var pointEnd: GamePoint = GamePoint()

    var isDestroyed = false
    var type: Type? = type

    init {
        pointCenter = GamePoint(coordinate!!.x + widthCell / 2, coordinate.y - (heightCell / 2))

        leftX = widthCell * coordinate.x - widthCell
        rightX = widthCell * coordinate.x
        upY = heightCell * coordinate.y - heightCell
        downY = heightCell * coordinate.y

        pointStart = GamePoint((widthCell * coordinate.x) - (widthCell), (heightCell * coordinate.y) - (heightCell))
        pointEnd = GamePoint((widthCell * coordinate.x), (heightCell * coordinate.y))
    }

    enum class Type {
        Dynamic,
        Static
    }
}

