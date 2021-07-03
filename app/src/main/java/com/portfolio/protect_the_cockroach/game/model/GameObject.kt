package com.portfolio.protect_the_cockroach.game.model

abstract class GameObject(var coordinate: GameCoordinate, widthCell: Double, heightCell: Double) {

    var pointCenter: GamePoint = GamePoint()

    var pointLeft: GamePoint = GamePoint()
    var pointRight: GamePoint = GamePoint()
    var pointTop: GamePoint = GamePoint()
    var pointButton: GamePoint = GamePoint()

    var pointStart: GamePoint = GamePoint()
    var pointEnd: GamePoint = GamePoint()

    var widthCell: Double = widthCell
    var heightCell: Double = heightCell

    init {
        pointCenter = GamePoint((widthCell * coordinate.x) - (widthCell / 2), (heightCell * coordinate.y) - (heightCell / 2))

        pointLeft = GamePoint((widthCell * (coordinate.x - 1)), (heightCell * coordinate.y) - (heightCell / 2))
        pointRight = GamePoint((widthCell * coordinate.x), (heightCell * coordinate.y) - (heightCell / 2))
        pointTop = GamePoint((widthCell * coordinate.x) - (widthCell / 2), (heightCell * (coordinate.y - 1)))
        pointButton = GamePoint((widthCell * coordinate.x) - (widthCell / 2), (heightCell * coordinate.y))

        pointStart = GamePoint((widthCell * coordinate.x) - (widthCell), (heightCell * coordinate.y) - (heightCell))
        pointEnd = GamePoint((widthCell * coordinate.x), (heightCell * coordinate.y))
    }
}



//
//   o--------------
//   |             |
//   |             |
//   |             |
//   --------------o
//


