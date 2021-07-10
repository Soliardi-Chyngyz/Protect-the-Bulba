package com.portfolio.protect_the_cockroach.game.manager

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import com.portfolio.protect_the_cockroach.R

class SoundPlayerManager(context: Context) {

   private var audioAtr: AudioAttributes? = null
   private var soundPool: SoundPool? = null
   private var hitSound = 0
   private val overSound = 0

   init {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
         audioAtr = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
         soundPool = SoundPool.Builder()
            .setAudioAttributes(audioAtr)
            .setMaxStreams(2)
            .build()
      } else {
         soundPool = SoundPool(2, AudioManager.STREAM_MUSIC, 0)
      }

      hitSound = soundPool!!.load(context, R.raw.hit, 1)
   }

   fun playSound() {
      soundPool!!.play(hitSound, 1.0F, 1.0F, 1, 0, 1.0F)

   }
}