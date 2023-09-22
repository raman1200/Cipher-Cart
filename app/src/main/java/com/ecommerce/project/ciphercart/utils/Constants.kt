package com.ecommerce.project.ciphercart.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.widget.Button
import android.widget.Toolbar
import androidx.fragment.app.Fragment
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