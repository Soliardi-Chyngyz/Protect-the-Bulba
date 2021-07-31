package com.portfolio.protect_the_cockroach.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.portfolio.protect_the_cockroach.R
import com.portfolio.protect_the_cockroach.game.GameField
import com.portfolio.protect_the_cockroach.game.model.OnClickStatus
import com.portfolio.protect_the_cockroach.ui.GameActivity

class MenuDialog(
   val onClick : (OnClickStatus) -> Unit
) : DialogFragment() {

   private var resume: MaterialButton? = null
   private var exit: MaterialButton? = null
   private var restart: MaterialButton? = null

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      if (dialog != null && dialog!!.window != null) {
         dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
         dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE);
      }
      return inflater.inflate(R.layout.fragment_menu, container, false)
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      init(view)
      listeners()
   }

   private fun init(view: View) {
      resume = view.findViewById(R.id.resume)
      restart = view.findViewById(R.id.restart)
      exit = view.findViewById(R.id.exit)
   }

   private fun listeners() {
      resume?.setOnClickListener {
         onClick.invoke(OnClickStatus.RESUME)
         dialog?.dismiss()
      }
      restart?.setOnClickListener {
         onClick.invoke(OnClickStatus.RESTART)
      }
      exit?.setOnClickListener {
         onClick.invoke(OnClickStatus.EXIT)
         dialog?.dismiss()
      }
   }
}
