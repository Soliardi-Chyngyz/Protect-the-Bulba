package com.portfolio.protect_the_cockroach.game.model

abstract class GameObject(
    coordinate: GameCoordinate?,
    widthCell: Double,
    heightCell: Double,
    invulnerable: Boolean,
    type: Type,
    theirOwn: Boolean,
) {
    var theirOwn: Boolean = theirOwn

    var pointCenter: GamePoint = GamePoint()

    var leftX: Double
    var rightX: Double
    var upY: Double
    var downY: Double

    var pointStart: GamePoint = GamePoint()
    var pointEnd: GamePoint = GamePoint()

    var widthCell: Double = widthCell
    var heightCell: Double = heightCell

    var isDestroyed = false
    var invulnerable = invulnerable
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

