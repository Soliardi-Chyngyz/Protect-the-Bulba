package com.portfolio.protect_the_cockroach.game.model

import kotlin.math.*

class GamePoint {

    var x: Double
    var y: Double

    constructor(x: Double, y: Double) {
        this.x = x
        this.y = y
    }

    constructor(point: GamePoint) {
        this.x = point.x
        this.y = point.y
    }

    constructor() {
        this.x = Double.NaN
        this.y = Double.NaN
    }

    fun updateData(point: GamePoint) {
        this.x = point.x
        this.y = point.y
    }

    fun updateData(x: Double, y: Double) {
        this.x = x
        this.y = y
    }

    fun distanceTo(point: GamePoint) : Double {
        return sqrt((x - point.x).pow(2) + (y - point.y).pow(2))
    }

    fun bearingTo(point: GamePoint) : Double {
        val rad2deg = 57.2957795130823209

        var theta = atan2(point.y - y, point.x - x)
        if (theta < 0.0) theta += PI * 2

        return rad2deg * theta
    }

    fun middleTo(point: GamePoint): GamePoint {
        return GamePoint((this.x + point.x) / 2, (this.y + point.y) / 2)
    }

    fun pointTo(bearing: Double, radius: Double) : GamePoint {
        val yT = radius * sin(Math.toRadians(bearing))
        val xT = radius * cos(Math.toRadians(bearing))

        return GamePoint(x + xT, y + yT)
    }

    fun rotate(angle: Double) {
        val rad: Double = angle * PI / 180

        val rx = x
        val ry = y

        x = rx * cos(rad) - ry * sin(rad)
        y = ry * cos(rad) + rx * sin(rad)
    }
}