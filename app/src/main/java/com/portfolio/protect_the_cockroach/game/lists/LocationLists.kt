package com.portfolio.protect_the_cockroach.game.lists

import com.portfolio.protect_the_cockroach.game.model.GameCoordinate
import com.portfolio.protect_the_cockroach.game.model.LocationObject

class LocationLists {
   companion object {
      val list = arrayListOf<LocationObject>()

      private fun LocationObject.addToList() {
         list.add(this)
      }

      fun getLevelPosition1(): List<LocationObject> {
         list.clear()
         LocationObject(GameCoordinate(2, 2), "1k12").addToList()
         LocationObject(GameCoordinate(2, 3), "1k13").addToList()
         LocationObject(GameCoordinate(3, 2), "1k14").addToList()
         LocationObject(GameCoordinate(19, 2), "1k15").addToList()
         LocationObject(GameCoordinate(18, 2), "1k16").addToList()
         LocationObject(GameCoordinate(19, 3), "1k17").addToList()

         LocationObject(GameCoordinate(8, 6), "1b1").addToList()
         LocationObject(GameCoordinate(9, 6), "1b2").addToList()
         LocationObject(GameCoordinate(10, 6), "1b3").addToList()
         LocationObject(GameCoordinate(11, 6), "1b4").addToList()
         LocationObject(GameCoordinate(12, 6), "1b5").addToList()

         LocationObject(GameCoordinate(2, 8), "1k1").addToList()
         LocationObject(GameCoordinate(2, 9), "1k2").addToList()
         LocationObject(GameCoordinate(19, 8), "1k3").addToList()
         LocationObject(GameCoordinate(19, 9), "1k4").addToList()

         return list
      }

      fun getLevelPosition2(): List<LocationObject> {
         list.clear()
         LocationObject(GameCoordinate(2, 2), "2k12").addToList()
         LocationObject(GameCoordinate(3, 3), "2k13").addToList()
         LocationObject(GameCoordinate(4, 4), "2k14").addToList()
         LocationObject(GameCoordinate(19, 2), "2k15").addToList()
         LocationObject(GameCoordinate(18, 3), "2k16").addToList()
         LocationObject(GameCoordinate(17, 4), "2k17").addToList()

         LocationObject(GameCoordinate(9, 6), "2b1").addToList()
         LocationObject(GameCoordinate(10, 6), "2b2").addToList()
         LocationObject(GameCoordinate(11, 6), "2b3").addToList()
         LocationObject(GameCoordinate(2, 9), "2b4").addToList()
         LocationObject(GameCoordinate(19, 9), "2b5").addToList()

         LocationObject(GameCoordinate(2, 7), "2k1").addToList()
         LocationObject(GameCoordinate(3, 6), "2k2").addToList()
         LocationObject(GameCoordinate(18, 6), "2k3").addToList()
         LocationObject(GameCoordinate(19, 7), "2k4").addToList()

         return list
      }

      fun getLevelPosition3(): List<LocationObject> {
         list.clear()
         LocationObject(GameCoordinate(9, 2), "3k12").addToList()
         LocationObject(GameCoordinate(9, 4), "3k13").addToList()
         LocationObject(GameCoordinate(10, 3), "3k14").addToList()
         LocationObject(GameCoordinate(11, 2), "3k15").addToList()
         LocationObject(GameCoordinate(11, 4), "3k16").addToList()
         LocationObject(GameCoordinate(10, 5), "3k17").addToList()

         LocationObject(GameCoordinate(6, 6), "3b1").addToList()
         LocationObject(GameCoordinate(8, 6), "3b2").addToList()
         LocationObject(GameCoordinate(10, 6), "3b3").addToList()
         LocationObject(GameCoordinate(12, 6), "3b4").addToList()
         LocationObject(GameCoordinate(14, 6), "3b5").addToList()

         LocationObject(GameCoordinate(2, 7), "3k1").addToList()
         LocationObject(GameCoordinate(3, 8), "3k2").addToList()
         LocationObject(GameCoordinate(18, 8), "3k3").addToList()
         LocationObject(GameCoordinate(19, 7), "3k4").addToList()

         return list
      }

      fun getLevelPosition4(): List<LocationObject> {
         list.clear()
         LocationObject(GameCoordinate(2, 8), "4k12").addToList()
         LocationObject(GameCoordinate(3, 7), "4k13").addToList()
         LocationObject(GameCoordinate(4, 6), "4k14").addToList()
         LocationObject(GameCoordinate(5, 4), "4k15").addToList()
         LocationObject(GameCoordinate(6, 3), "4k16").addToList()
         LocationObject(GameCoordinate(15, 4), "4k17").addToList()

         LocationObject(GameCoordinate(8, 4), "4b1").addToList()
         LocationObject(GameCoordinate(9, 3), "4b2").addToList()
         LocationObject(GameCoordinate(10, 2), "4b3").addToList()
         LocationObject(GameCoordinate(11, 3), "4b4").addToList()
         LocationObject(GameCoordinate(12, 4), "4b5").addToList()

         LocationObject(GameCoordinate(16, 5), "4k1").addToList()
         LocationObject(GameCoordinate(17, 6), "4k2").addToList()
         LocationObject(GameCoordinate(18, 7), "4k3").addToList()
         LocationObject(GameCoordinate(19, 8), "4k4").addToList()

         return list
      }

      fun getLevelPosition5(): List<LocationObject> {
         list.clear()
         LocationObject(GameCoordinate(2, 8), "5k12").addToList()
         LocationObject(GameCoordinate(3, 7), "5k13").addToList()
         LocationObject(GameCoordinate(4, 6), "5k14").addToList()
         LocationObject(GameCoordinate(5, 4), "5k15").addToList()
         LocationObject(GameCoordinate(6, 3), "5k16").addToList()
         LocationObject(GameCoordinate(15, 4), "5k17").addToList()

         LocationObject(GameCoordinate(8, 4), "5b1").addToList()
         LocationObject(GameCoordinate(9, 3), "5b2").addToList()
         LocationObject(GameCoordinate(10, 2), "5b3").addToList()
         LocationObject(GameCoordinate(11, 3), "5b4").addToList()
         LocationObject(GameCoordinate(12, 4), "5b5").addToList()

         LocationObject(GameCoordinate(16, 5), "5k1").addToList()
         LocationObject(GameCoordinate(17, 6), "5k2").addToList()
         LocationObject(GameCoordinate(18, 7), "5k3").addToList()
         LocationObject(GameCoordinate(19, 8), "5k4").addToList()

         return list
      }

   }
}