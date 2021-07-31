package com.portfolio.protect_the_cockroach.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.protect_the_cockroach.R
import com.portfolio.protect_the_cockroach.game.model.LevelItem

class LevelAdapter(val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<LevelAdapter.VH>() {

   private var list = listOf<LevelItem>()

   fun setList(list: List<LevelItem>) {
      this.list = list
      notifyDataSetChanged()
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.level_item, parent, false)
      return VH(view)
   }

   override fun getItemCount() = list.size

   override fun onBindViewHolder(holder: VH, position: Int) {
      holder.onBind(list[position])
   }

   inner class VH(v: View) : RecyclerView.ViewHolder(v) {
      private val txt: TextView by lazy {
         v.findViewById(R.id.level_txt)
      }

      private val layout: ConstraintLayout by lazy {
         v.findViewById(R.id.l_layout)
      }

      fun onBind(model: LevelItem) {
         txt.text = model.number.toString()
         layout.setBackgroundResource(model.photo)

         layout.setOnClickListener{
            onItemClick(model.number)
         }
      }
   }
}