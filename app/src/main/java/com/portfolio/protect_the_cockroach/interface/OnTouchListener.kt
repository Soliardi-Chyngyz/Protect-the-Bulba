package com.portfolio.protect_the_cockroach.`interface`

import com.portfolio.protect_the_cockroach.game.GameField

interface OnTouchListener {
   fun setOnTouchClick(typeMove: GameField.TypeMove?)

   fun setOnTickTimer(value: Int)

   fun setOnFinishTimer(b: Boolean)
}