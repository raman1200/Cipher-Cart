package com.ecommerce.project.ciphercart.utils

import android.view.View
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
