package com.portfolio.protect_the_cockroach.ui.dialogs

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.portfolio.protect_the_cockroach.R
import com.portfolio.protect_the_cockroach.game.model.OnClickStatus

class VictoryDialog(
   private val score: Int?,
   private val onClick: (OnClickStatus) -> Unit
) : DialogFragment() {

   private val menu: MaterialButton by lazy {
      return@lazy this.requireView().findViewById(R.id.v_menu)
   }

   private val next: MaterialButton by lazy {
      return@lazy this.requireView().findViewById(R.id.next)
   }

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      if (dialog != null && dialog!!.window != null) {
         dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
         dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
         dialog!!.window!!.attributes.windowAnimations = R.style.MyCustomTheme
         dialog!!.setCancelable(false)
         dialog!!.setCanceledOnTouchOutside(false)
      }
      return inflater.inflate(R.layout.fragment_victory, container, false)
   }

   @SuppressLint("SetTextI18n")
   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      view.findViewById<TextView>(R.id.v_score).text = "You scored $score points per level"

      listeners()
   }

   private fun listeners() {
      menu.setOnClickListener {
         onClick.invoke(OnClickStatus.MENU)
      }

      next.setOnClickListener {
         onClick.invoke(OnClickStatus.NEXT)
      }
   }
}