package com.portfolio.protect_the_cockroach.game.model

class GameCoordinate {

    var x: Int
    var y: Int

    constructor(x: Int, y: Int) {
        this.x = x
        this.y = y
    }

    constructor() {
        this.x = Double.NaN.toInt()
        this.y = Double.NaN.toInt()
    }

    fun updateData(point: GameCoordinate) {
        this.x = point.x
        this.y = point.y
    }

    fun updateData(x: Int, y: Int) {
        this.x = x
        this.y = y
    }
}