package com.portfolio.protect_the_cockroach.game.lists

import com.portfolio.protect_the_cockroach.R
import com.portfolio.protect_the_cockroach.game.model.LevelItem

class CustomList {

   companion object {
      fun getData(): List<LevelItem> {
         val list = arrayListOf<LevelItem>()

         val item1 = LevelItem(1, R.drawable.kosmos)
         val item2 = LevelItem(2, R.drawable.kosmos)
         val item3 = LevelItem(3, R.drawable.kosmos)
         val item4 = LevelItem(4, R.drawable.kosmos)
         val item5 = LevelItem(5, R.drawable.kosmos)
         val item6 = LevelItem(6, R.drawable.kosmos)
         val item7 = LevelItem(7, R.drawable.kosmos)
         val item8 = LevelItem(8, R.drawable.kosmos)
         val item9 = LevelItem(9, R.drawable.kosmos)
         val item10 = LevelItem(10, R.drawable.kosmos)

         list.add(item1)
         list.add(item2)
         list.add(item3)
         list.add(item4)
         list.add(item5)
         list.add(item6)
         list.add(item7)
         list.add(item8)
         list.add(item9)
         list.add(item10)
         return list
      }
   }
}