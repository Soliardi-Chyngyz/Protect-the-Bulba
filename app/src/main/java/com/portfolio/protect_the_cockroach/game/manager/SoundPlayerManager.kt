package com.portfolio.protect_the_cockroach.game.manager

import android.content.Context
import android.media.MediaPlayer
import com.portfolio.protect_the_cockroach.R

class SoundPlayerManager(context: Context) {

   private var moving: MediaPlayer? = null
   private var motor: MediaPlayer? = null
   private var shot: MediaPlayer? = null
   private var damaged: MediaPlayer? = null
   private var middleShot2: MediaPlayer? = null
   private var middleShot3: MediaPlayer? = null
   private var middleShot4: MediaPlayer? = null
   private var middleShot5: MediaPlayer? = null
   private var miniDamaged: MediaPlayer? = null
   private var battleStart: MediaPlayer? = null

   private var bonus: MediaPlayer? = null
   private var pick: MediaPlayer? = null

   private var vot_i_vse: MediaPlayer? = null
   private var eto: MediaPlayer? = null
   private var catastrophe: MediaPlayer? = null
   private var loose: MediaPlayer? = null

   private var tron: MediaPlayer? = null
   private var intro1: MediaPlayer? = null
   private var intro2: MediaPlayer? = null

   init {
      moving = MediaPlayer.create(context, R.raw.ezdamini)
      motor = MediaPlayer.create(context, R.raw.motormini)
      damaged = MediaPlayer.create(context, R.raw.podryv)
      shot = MediaPlayer.create(context, R.raw.vystrelmini)
      middleShot2 = MediaPlayer.create(context, R.raw.vystrelmini)
      middleShot3 = MediaPlayer.create(context, R.raw.vystrelmini)
      middleShot4 = MediaPlayer.create(context, R.raw.vystrelmini)
      middleShot5 = MediaPlayer.create(context, R.raw.vystrelmini)
      eto = MediaPlayer.create(context, R.raw.jeto)
      catastrophe = MediaPlayer.create(context, R.raw.katastrofa)
      vot_i_vse = MediaPlayer.create(context, R.raw.votivse)
      miniDamaged = MediaPlayer.create(context, R.raw.podryvmini)
      bonus = MediaPlayer.create(context, R.raw.bonusmini)
      battleStart = MediaPlayer.create(context, R.raw.battle_start)
      pick = MediaPlayer.create(context, R.raw.pick)
      loose = MediaPlayer.create(context, R.raw.lose)
      tron = MediaPlayer.create(context, R.raw.tron)
      intro1 = MediaPlayer.create(context, R.raw.intro)
      intro2 = MediaPlayer.create(context, R.raw.intro2)
   }

   fun playIntro1() {
      intro1?.start()
   }

   fun playIntro2() {
      intro2?.start()
   }

   fun playTron(){
      tron?.start()
   }

   fun pauseTron(){
      tron?.pause()
   }

   fun playLoose(){
      loose?.start()
   }

   fun playPick() {
      pick?.start()
   }

   fun playBattleStart() {
      battleStart?.start()
   }

   fun playVot() {
      vot_i_vse!!.start()
   }

   fun playMotor() {
      motor!!.start()
   }

   fun pauseMotor() {
      motor!!.pause()
   }

   fun playEto() {
      eto!!.start()
   }

   fun playAccident() {
      catastrophe!!.start()
   }

   fun playMoving(){
      moving!!.start()
   }

   fun playHugeShot(){
      shot!!.start()
   }

   fun pauseMoving() {
      moving!!.pause()
   }

   fun playMiniDamaged() {
      miniDamaged!!.start()
   }

   fun playDamaged() {
      damaged!!.start()
   }

   fun playShot() {
      shot?.start()
   }

   fun playShot2() {
      middleShot2?.start()
   }
   fun playShot3() {
      middleShot3?.start()
   }
   fun playShot4() {
      middleShot4?.start()
   }
   fun playShot5() {
      middleShot5?.start()
   }

   fun playBonus() {
      bonus!!.start()
   }
}