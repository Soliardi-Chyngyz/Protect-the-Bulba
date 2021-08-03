package com.portfolio.protect_the_cockroach.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.protect_the_cockroach.R
import com.portfolio.protect_the_cockroach.game.constants.Constants
import com.portfolio.protect_the_cockroach.game.lists.CustomList
import com.portfolio.protect_the_cockroach.game.manager.SoundPlayerManager
import com.portfolio.protect_the_cockroach.ui.adapter.LevelAdapter
import com.portfolio.protect_the_cockroach.ui.dialogs.DialogAboutGame

class MainActivity : AppCompatActivity() {

   private val about: ImageView by lazy {
      return@lazy this.findViewById(R.id.btn_about)
   }

   private val recycler: RecyclerView by lazy {
      return@lazy this.findViewById(R.id.recycler)
   }

   private val adapter = LevelAdapter(this, this::onItemClick)

   private var sound: SoundPlayerManager? = null

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_main)

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
         window.insetsController?.hide(WindowInsets.Type.statusBars())
      } else {
         window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
         )
      }

      listeners()

      initUI()
   }

   private fun initUI() {
      sound = SoundPlayerManager(this)
      sound?.playTron()
      val pref = getSharedPreferences(resources.getString(R.string.pref), Context.MODE_PRIVATE)
      val score = pref.getInt(Constants.PREFS_LEVEL, 0)
      if (score != 0) {
         adapter.setLevel(score)
      }
      adapter.setList(CustomList.getData())
      recycler.adapter = adapter
   }

   private fun listeners() {
      about.setOnClickListener {
         DialogAboutGame().show(supportFragmentManager, "DialogFr")
      }
   }

   private fun onItemClick(value: Int) {
      if (value != 0) {
         sound = null
         val intent = Intent(this, GameActivity::class.java)
         intent.putExtra(Constants.KEY_LEVEL, value)
         startActivity(intent)
      }
   }

   override fun onResume() {
      super.onResume()
      sound?.playTron()
   }

   override fun onPause() {
      super.onPause()
      sound?.pauseTron()
   }
}


