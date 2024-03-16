package com.ecommerce.project.ciphercart.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.activities.MainActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

fun etHintTextChange(editText: TextInputEditText, textInputLayout:TextInputLayout, hint:String) {
    editText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        if (hasFocus) {
            textInputLayout.hint = "" // Set an empty string to remove the label
        } else {
            textInputLayout.hint = hint // Restore the label when not focused
        }
    }
}
fun hideKeyboard(activity:Activity?, view: View?) {
    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}
fun goToMainActivity(activity: Activity){
    activity.startActivity(Intent(activity, MainActivity::class.java))
}
fun setUpActionBar(toolbar: Toolbar, requireActivity:Activity) {
    (requireActivity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
    (requireActivity as AppCompatActivity?)!!.supportActionBar?.setDisplayShowTitleEnabled(false)
    (requireActivity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
fun getDialog(context: Context, layout:Int):Dialog {
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(true)
    dialog.setContentView(layout)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    return dialog
}

@SuppressLint("SimpleDateFormat")
fun generateOrderId(): String {
    val currentDateTime = Calendar.getInstance().time
    val formatter = SimpleDateFormat("ddMMyyyyHHmmssSSS")
    val formattedDateTime = formatter.format(currentDateTime)
    return "OD${formattedDateTime}"
}

fun getCurrentDateInLong():Long {
    return Date().time
}

@SuppressLint("SimpleDateFormat")
fun getLongToDate(date:Long) : String {
    val sdf = SimpleDateFormat("dd/mm/yyyy hh:mm a")
    return sdf.format(date)
}