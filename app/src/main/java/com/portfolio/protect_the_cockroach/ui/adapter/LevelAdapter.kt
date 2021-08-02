package com.portfolio.protect_the_cockroach.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.portfolio.protect_the_cockroach.R
import com.portfolio.protect_the_cockroach.game.model.LevelItem

class LevelAdapter(
   val context: Context,
   val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<LevelAdapter.VH>() {

   private var list = listOf<LevelItem>()
   private var level = 0

   fun setList(list: List<LevelItem>) {
      this.list = list
      notifyDataSetChanged()
   }

   fun setLevel(value: Int) {
      level = value
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

      private val img: ImageView by lazy {
         v.findViewById(R.id.level_img)
      }

      fun onBind(model: LevelItem) {

         if (model.number <= level && model.number <= 7 || model.number == 1) {
            txt.text = model.number.toString()
            layout.setBackgroundResource(model.photo)

            layout.setOnClickListener {
               onItemClick(model.number)
            }
         } else if (model.number in 7..level){
            txt.setTextColor(Color.WHITE)
            txt.alpha = 0.2F
            txt.text = model.number.toString()
            layout.setBackgroundColor(Color.BLACK)
            Glide.with(context)
               .load(model.photo)
               .into(img);
         } else {
            img.setImageResource(R.drawable.ic_locked)
         }
      }
   }
}