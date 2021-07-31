package com.portfolio.protect_the_cockroach.`interface`

import com.portfolio.protect_the_cockroach.game.GameField

interface OnTouchLFirst {
   fun setOnTouchClickFirst(typeMove: GameField.TypeMove?)

   fun setOnTickTimerFirst(value: Int)

   fun setOnFinishTimerFirst(b: Boolean)
}