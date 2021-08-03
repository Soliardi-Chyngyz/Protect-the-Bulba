package com.portfolio.protect_the_cockroach.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.CheckBox
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.portfolio.protect_the_cockroach.R
import com.portfolio.protect_the_cockroach.game.manager.SoundPlayerManager
import com.portfolio.protect_the_cockroach.game.model.OnClickStatus

class MenuDialog(
   private var sound: SoundPlayerManager?,
   private val onClick: (OnClickStatus) -> Unit
) : DialogFragment() {

   private var resume: MaterialButton? = null
   private var exit: MaterialButton? = null
   private var restart: MaterialButton? = null
   private var checkBox: CheckBox? = null

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      if (dialog != null && dialog!!.window != null) {
         dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
         dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
         dialog!!.setCancelable(false)
         dialog!!.setCanceledOnTouchOutside(false)
      }
      return inflater.inflate(R.layout.fragment_menu, container, false)
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      init(view)
      checkBoxStatus()
      listeners()
   }

   private fun checkBoxStatus() {
      checkBox?.isChecked = sound != null
   }

   private fun init(view: View) {
      resume = view.findViewById(R.id.resume)
      restart = view.findViewById(R.id.restart)
      exit = view.findViewById(R.id.exit)
      checkBox = view.findViewById(R.id.check_box)
   }

   private fun listeners() {
      resume?.setOnClickListener {
         onClick.invoke(OnClickStatus.RESUME)
         dismissFun()
      }
      restart?.setOnClickListener {
         onClick.invoke(OnClickStatus.RESTART)
         dismissFun()
      }
      exit?.setOnClickListener {
         onClick.invoke(OnClickStatus.EXIT)
         dismissFun()
      }
      checkBox?.setOnCheckedChangeListener { _, isChecked ->
         if (isChecked)
            onClick.invoke(OnClickStatus.MUSIC_ON)
         else
            onClick.invoke(OnClickStatus.MUSIC_OFF)
      }
   }

   private fun dismissFun() {
      dialog?.dismiss()
   }
}
