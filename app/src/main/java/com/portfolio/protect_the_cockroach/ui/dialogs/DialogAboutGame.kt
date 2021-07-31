package com.portfolio.protect_the_cockroach.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.portfolio.protect_the_cockroach.R

class DialogAboutGame : DialogFragment() {

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      if (dialog != null && dialog!!.window != null) {
         dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
         dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE);
      }
      return inflater.inflate(R.layout.fragment_about_game, container, false)
   }
}