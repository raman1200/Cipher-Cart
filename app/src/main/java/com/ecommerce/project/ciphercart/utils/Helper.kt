package com.ecommerce.project.ciphercart.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.Toast
import android.widget.Toolbar
import com.ecommerce.project.ciphercart.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun etHintTextChange(editText: TextInputEditText, textInputLayout:TextInputLayout, hint:String) {
    editText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        if (hasFocus) {
            textInputLayout.hint = "" // Set an empty string to remove the label
        } else {
            textInputLayout.hint = hint // Restore the label when not focused
        }
    }
}
fun setUpActionBar(toolbar: Toolbar, requireActivity:Activity) {
    requireActivity.setActionBar(toolbar)
    requireActivity.actionBar!!.setDisplayHomeAsUpEnabled(true)
    requireActivity.actionBar!!.setDisplayShowTitleEnabled(false)
    toolbar.setNavigationIcon(R.drawable.back_arrow)
    toolbar.setNavigationOnClickListener {
        requireActivity.onBackPressed()

    }

}

fun disableButton(context: Context, btn:Button){
    btn.isEnabled = false
    btn.backgroundTintList = context.resources.getColorStateList(R.color.grey_600, null)
}

fun enableButton(context: Context, btn:Button){
    btn.isEnabled = true
    btn.backgroundTintList = context.resources.getColorStateList(R.color.black, null)
}

fun toast(context: Context, message:String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
fun showLoadingDialog(context: Context) {
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(true)
    dialog.setContentView(R.layout.pop_up)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.show()
}