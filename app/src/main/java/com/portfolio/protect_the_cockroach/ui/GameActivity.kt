package com.portfolio.protect_the_cockroach.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.*
import android.view.MotionEvent
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.portfolio.protect_the_cockroach.R
import com.portfolio.protect_the_cockroach.game.GameField
import com.portfolio.protect_the_cockroach.game.constants.Constants
import com.portfolio.protect_the_cockroach.game.manager.SoundPlayerManager
import com.portfolio.protect_the_cockroach.game.model.OnClickStatus
import com.portfolio.protect_the_cockroach.ui.dialogs.LooseDialog
import com.portfolio.protect_the_cockroach.ui.dialogs.MenuDialog
import com.portfolio.protect_the_cockroach.ui.dialogs.VictoryDialog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

@Suppress("DEPRECATION")
class GameActivity : AppCompatActivity() {

   // region fields
   val gameField: GameField by lazy {
      return@lazy this.findViewById<GameField>(R.id.game_field)
   }
   private val timerView: TextView by lazy {
      return@lazy this.findViewById(R.id.timer)
   }
   private val fire: ImageView by lazy {
      return@lazy this.findViewById<ImageView>(R.id.fire)
   }
   private val menu: ImageView by lazy {
      return@lazy this.findViewById(R.id.menu)
   }
   private var sound: SoundPlayerManager? = null
   private var isStarted = false

   private var timerClock: CountDownTimer? = null
   private var timer1: CountDownTimer? = null
   private var timer2: CountDownTimer? = null
   private var timer3: CountDownTimer? = null
   private var timer4: CountDownTimer? = null
   private var timer5: CountDownTimer? = null
   private var timerHeroBullet: CountDownTimer? = null

   private var milliLeft: Long = 120000
   private var timer: Long = 0
   private var score = 0
   private var bonusTime = 0L

   private var level = 0
   //endregion

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_game)

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
         window.insetsController?.hide(WindowInsets.Type.statusBars())
      } else {
         window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
         )
      }
      window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

      sound = SoundPlayerManager(this)
      gameField.sendActivity(this)

      startLevel()

      setupUI()

      listeners()
   }

   private fun startTimer() {
      if (!isStarted) {
         CDTimer()

         CDTimerSec()

         CDTimerThird()

         CDTimerFour()

         CDTimerFive()

         CDTimerBullet()
      }
   }

   fun setScore(v: Int) {
      score = v
   }

   private fun startLevel() {
      val intent = intent
      val value = intent.getIntExtra(Constants.KEY_LEVEL, 0)
      level = value
      milliLeft += level * 60000
      timer = milliLeft
      gameField.setArg(value)
   }

   private fun listeners() {
      fire.setOnClickListener {
         sound?.playHugeShot()
         gameField.getReTranslator()?.onClick(true)
         it.isEnabled = false
         startTimer()
         CDTimerGame(milliLeft)
      }
      menu.setOnClickListener {
         onPauseFun()
      }
   }

   fun onPauseLocal() {
      onPauseCDT()
      gameField.pauseGame()
      timerClock?.cancel()
   }

   fun gameOver() {
      LooseDialog { status ->
         when (status) {
            OnClickStatus.MENU -> {
               openMenu()
            }
            OnClickStatus.RESTART -> {
               restart()
            }
            else -> (throw IllegalStateException("Invalid rating param value"))
         }
      }.show(supportFragmentManager, "LoosingDialog")
   }

   private fun restart() {
      milliLeft = timer
      onResumeFun()
      score = 0
      gameField.restartGame()
      sound?.playBattleStart()
   }

   private fun openMenu() {
      gameField.switchOffGame()
      startActivity(Intent(this, MainActivity::class.java))
      this@GameActivity.finish()
   }

   private fun onPauseFun() {
      onPauseLocal()
      MenuDialog(sound) { status ->
         when (status) {
            OnClickStatus.RESUME -> {
               onResumeFun()
            }
            OnClickStatus.RESTART -> {
               restart()
            }
            OnClickStatus.EXIT -> {
               this.onBackPressed()
            }
            OnClickStatus.MUSIC_ON -> {
               gameField.musicStatus(true)
               sound = SoundPlayerManager(this)
            }
            OnClickStatus.MUSIC_OFF -> {
               gameField.musicStatus(false)
               sound = null
            }
            else -> (throw IllegalStateException("Invalid rating param value"))
         }
      }.show(supportFragmentManager, "MenuDialog")
   }

   private fun onResumeFun() {
      onResumeCDT()
      gameField.unPause()
      isStarted = false
      CDTimerGame(milliLeft)
   }

   private fun onResumeCDT() {
      timer1?.start()
      timer2?.start()
      timer3?.start()
      timer4?.start()
      timer5?.start()
      timerHeroBullet?.start()
   }

   private fun onPauseCDT() {
      timer1?.cancel()
      timer2?.cancel()
      timer3?.cancel()
      timer4?.cancel()
      timer5?.cancel()
      timerHeroBullet?.cancel()
   }

   override fun onBackPressed() {
      super.onBackPressed()
      gameField.switchOffGame()
      finish()
   }

   private fun CDTimerBullet() {
      timerHeroBullet = object : CountDownTimer(2000, 1000) {
         override fun onTick(millisUntilFinished: Long) {
         }

         override fun onFinish() {
            fire.isEnabled = true
            CDTimerBullet()
         }
      }.start()
   }

   private fun CDTimerFive() {
      val value = Random.nextInt(7)
      timer5 = object : CountDownTimer(Random.nextLong(2000, 4000), Random.nextLong(600, 1000)) {
         override fun onTick(millisUntilFinished: Long) {
            gameField.getLFive()?.setOnTickTimerFive(value)
         }

         override fun onFinish() {
            gameField.getLFive()?.setOnFinishTimerFive(true)
            CDTimerFive()
         }
      }.start()
   }

   private fun CDTimerFour() {
      val value = Random.nextInt(7)
      timer4 = object : CountDownTimer(Random.nextLong(2000, 4000), Random.nextLong(600, 1000)) {
         override fun onTick(millisUntilFinished: Long) {
            gameField.getLFour()?.setOnTickTimerFour(value)
         }

         override fun onFinish() {
            gameField.getLFour()?.setOnFinishTimerFour(true)
            CDTimerFour()
         }
      }.start()
   }

   private fun CDTimerThird() {
      val value = Random.nextInt(7)
      timer3 = object : CountDownTimer(Random.nextLong(2000, 4000), Random.nextLong(600, 1000)) {
         override fun onTick(millisUntilFinished: Long) {
            gameField.getLThird()?.setOnTickTimerThird(value)
         }

         override fun onFinish() {
            gameField.getLThird()?.setOnFinishTimerThird(true)
            CDTimerThird()
         }
      }.start()
   }

   private fun CDTimerSec() {
      val value = Random.nextInt(7)
      timer2 = object : CountDownTimer(Random.nextLong(2000, 4000), Random.nextLong(600, 1000)) {
         override fun onTick(millisUntilFinished: Long) {
            gameField.getLSecond()?.setOnTickTSec(value)
         }

         override fun onFinish() {
            gameField.getLSecond()?.setOnFinishTimerSec(true)
            CDTimerSec()
         }
      }.start()
   }

   private fun CDTimer() {
      val value = Random.nextInt(7)
      timer1 = object : CountDownTimer(Random.nextLong(2000, 4000), Random.nextLong(600, 1000)) {
         override fun onTick(millisUntilFinished: Long) {
            gameField.getListener()?.setOnTickTimerFirst(value)
         }

         override fun onFinish() {
            gameField.getListener()?.setOnFinishTimerFirst(true)
            CDTimer()
         }
      }.start()
   }

   private fun CDTimerGame(milliLeft: Long) {
      if (!isStarted) {
         isStarted = true
         timerClock = object : CountDownTimer(milliLeft, 1000) {
            @SuppressLint("SimpleDateFormat")
            override fun onTick(millisUntilFinished: Long) {
               this@GameActivity.milliLeft = millisUntilFinished
               val dateFormat = SimpleDateFormat("mm:ss")
               dateFormat.timeZone = TimeZone.getTimeZone("GMT")
               val date = Date(millisUntilFinished)
               timerView.text = dateFormat.format(date)
               bonusTime += 1000
               // Bonus part
               gameField.getLOnBonusTime()?.onBonusTick(bonusTime)
               if (bonusTime >= 10000) {
                  val value = Random.nextInt(2)
                  gameField.getLOnBonusTime()?.onBonusTimer(value)
                  bonusTime = 0
               }
            }

            override fun onFinish() {
               isStarted = false
               val pref = getSharedPreferences(resources.getString(R.string.pref), Context.MODE_PRIVATE)
               score = pref.getInt(Constants.PREFS_LEVEL, 0)
               var s = 0
                  if (level >= score) {
                     s = level + 1
                  }
               if (s != 0){
                  pref.edit().putInt(Constants.PREFS_LEVEL, s).apply()
               }
               gameField.pauseGame()
               VictoryDialog(score) { status ->
                  when (status) {
                     OnClickStatus.MENU -> {
                        openMenu()
                     }
                     OnClickStatus.NEXT -> {
                        if (level != 0) {
                           gameField.switchOffGame()
                           val intent = Intent(this@GameActivity, GameActivity::class.java)
                           intent.putExtra(Constants.KEY_LEVEL, level + 1)
                           startActivity(intent)
                           this@GameActivity.finish()
                        }
                     }
                     else -> (throw IllegalStateException("Invalid rating param value"))
                  }
               }.show(supportFragmentManager, "VictoryDialog")
            }
         }.start()
      }
   }

   private fun setupUI() {
      findViewById<ImageView>(R.id.up).setClickListener(GameField.TypeMove.UP)
      findViewById<ImageView>(R.id.right).setClickListener(GameField.TypeMove.RIGHT)
      findViewById<ImageView>(R.id.down).setClickListener(GameField.TypeMove.DOWN)
      findViewById<ImageView>(R.id.left).setClickListener(GameField.TypeMove.LEFT)
   }

   @SuppressLint("ClickableViewAccessibility")
   private fun ImageView.setClickListener(typeMove: GameField.TypeMove) {
      this.setOnTouchListener { _, event ->
         when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
               sound?.playMoving()
               gameField.getListener()?.setOnTouchClickFirst(typeMove)
            }
            MotionEvent.ACTION_UP -> {
               sound?.pauseMoving()
               gameField.getListener()?.setOnTouchClickFirst(null)
            }
         }
         true
      }
   }

   override fun onResume() {
      super.onResume()
      timer1?.let {
         onResumeCDT()
         gameField.unPause()
         isStarted = false
         CDTimerGame(milliLeft)
      }
   }

   override fun onPause() {
      super.onPause()
      onPauseCDT() // все count down timer cancel
      gameField.pauseGame()
      timerClock?.cancel()
   }
}