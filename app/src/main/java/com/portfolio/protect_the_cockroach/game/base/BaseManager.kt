package com.portfolio.protect_the_cockroach.game.base

abstract class BaseManager(var widthScreen: Double, var heightScreen: Double) {

    val horizontalCountCell: Double = 18.0
    val verticalCountCell: Double = 9.0

    var widthCell: Double = 0.0
    var heightCell: Double = 0.0

    init {
        widthCell = widthScreen / horizontalCountCell
        heightCell = heightScreen / verticalCountCell
    }
}