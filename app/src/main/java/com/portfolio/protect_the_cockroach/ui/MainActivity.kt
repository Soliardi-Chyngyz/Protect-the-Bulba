package com.portfolio.protect_the_cockroach.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.protect_the_cockroach.R
import com.portfolio.protect_the_cockroach.game.lists.CustomList
import com.portfolio.protect_the_cockroach.game.constants.Constants
import com.portfolio.protect_the_cockroach.ui.adapter.LevelAdapter

class MainActivity : AppCompatActivity() {
   /*private val start: MaterialButton by lazy {
      return@lazy this.findViewById(R.id.btn_start)
   }

   private val about: MaterialButton by lazy {
      return@lazy this.findViewById(R.id.btn_about)
   }*/
   private val recycler: RecyclerView by lazy {
      return@lazy this.findViewById(R.id.recycler)
   }

   private val adapter = LevelAdapter(this::onItemClick)

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_main)

      listeners()

      initUI()
   }

   private fun initUI() {
      adapter.setList(CustomList.getData())
      recycler.adapter = adapter
   }

   private fun listeners() {
      /*start.setOnClickListener {
         startActivity(Intent(this, GameActivity::class.java))
      }
      about.setOnClickListener {
         DialogAboutGame().show(supportFragmentManager, "DialogFr")
      }*/
   }

   private fun onItemClick(value: Int) {
      val intent = Intent(this, GameActivity::class.java)
      intent.putExtra(Constants.KEY_LEVEL, value)
      startActivity(intent)
   }
}


