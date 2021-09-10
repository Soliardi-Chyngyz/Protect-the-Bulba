package com.portfolio.protect_the_cockroach.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.portfolio.protect_the_cockroach.R
import com.portfolio.protect_the_cockroach.game.GameDrawThread
import com.portfolio.protect_the_cockroach.game.GameField
import com.portfolio.protect_the_cockroach.game.manager.DynamicRenderingManager
import com.portfolio.protect_the_cockroach.game.manager.ImmovableRenderingManager

class GameViewModel : ViewModel() {

   private var isStarted = MutableLiveData<Boolean>()
   private var timer1 = MutableLiveData<CountDownTimer>()
   private var timer2 = MutableLiveData<CountDownTimer>()
   private var timer3 = MutableLiveData<CountDownTimer>()
   private var timer4 = MutableLiveData<CountDownTimer>()
   private var timer5 = MutableLiveData<CountDownTimer>()
   private var timerClock = MutableLiveData<CountDownTimer>()

   private var milliLeft: Long = 120000
   private var timer: Long = 0
   private var score = 0
   private var bonusTime = 0L

   private var onResume = MutableLiveData(false)

   private var gameDrawThread = MutableLiveData<GameDrawThread>()
   var dynamicRenderingManager = MutableLiveData<DynamicRenderingManager>()
   var immovableRenderingManager = MutableLiveData<ImmovableRenderingManager>()

   fun saveDynamicManager(dynamicRenderingManager: DynamicRenderingManager) {
      this.dynamicRenderingManager.value = dynamicRenderingManager
   }

   fun saveImmovableManager(immovableRenderingManager: ImmovableRenderingManager) {
      this.immovableRenderingManager.value = immovableRenderingManager
   }

   fun getDrawThread(): LiveData<GameDrawThread> = gameDrawThread


}