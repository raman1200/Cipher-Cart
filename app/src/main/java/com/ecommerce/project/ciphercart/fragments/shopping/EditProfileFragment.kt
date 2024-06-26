package com.ecommerce.project.ciphercart.fragments.shopping

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.getSystemService
import com.bumptech.glide.Glide
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.FragmentEditProfileBinding
import com.ecommerce.project.ciphercart.model.UserData
import com.ecommerce.project.ciphercart.utils.UserDataManager
import com.ecommerce.project.ciphercart.utils.etHintTextChange
import com.ecommerce.project.ciphercart.utils.hideKeyboard
import com.ecommerce.project.ciphercart.utils.setUpActionBar
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.whynotimagecarousel.utils.setImage
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    lateinit var binding:FragmentEditProfileBinding

    @Inject
    lateinit var userDataManager: UserDataManager

    private var getContent: ActivityResultLauncher<Intent>? = null
    private var selectedYear = 0
    private var selectedMonth = 0
    private var selectedDay = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(layoutInflater, container, false)

        setUpActionBar(binding.toolbar, requireActivity())
        initialize()
        focusListeners()
        clickListeners()

        return binding.root
    }
    private fun initialize() {
        setData()
       getContent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val selectedImageUri = result.data?.data
                    binding.profileImg.setImageURI(selectedImageUri)
                }
            }

    }
    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        getContent?.launch(galleryIntent)
    }
    private fun setData() {
        val name = userDataManager.getUsername()
        val email = userDataManager.getEmail()
        val mobile = userDataManager.getMobile()
        val dob = userDataManager.getDob()
        val image = userDataManager.getProfileImg()
        if(!name.isNullOrBlank())
            binding.nameEditText.setText(name)
        if(!email.isNullOrBlank())
            binding.emailEditText.setText(email)
        if(!mobile.isNullOrBlank())
            binding.numberEditText.setText(mobile)
        if(dob.isNullOrBlank())
            binding.DOBEditText.setText(dob)
        if(!image.isNullOrBlank()){
             Glide.with(requireContext()).load(image).placeholder(R.drawable.person_profile).into(binding.profileImg)
        }
    }

    private fun clickListeners() {
        binding.apply {
            ll.setOnClickListener {
                nameEditText.clearFocus()
                emailEditText.clearFocus()
                numberEditText.clearFocus()
                DOBEditText.clearFocus()
                hideKeyboard(activity, view)
            }
            editImg.setOnClickListener {
                openGallery()
            }
        }
    }


    private fun focusListeners() {
        binding.apply {
            etHintTextChange(nameEditText, nameTextInputLayout, "Name")
            etHintTextChange(emailEditText, emailTextInputLayout, "Email")
            etHintTextChange(numberEditText, numberTextInputLayout, "Number")

            DOBEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    DateofbirthTextInputLayout.hint = "" // Set an empty string to remove the label
                    showDatePicker()
                } else {
                    DateofbirthTextInputLayout.hint = "Dob" // Restore the label when not focused
                }
            }
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                selectedYear = year
                selectedMonth = month
                selectedDay = day

                // Update the button text with the selected date
                binding.DOBEditText.setText(getString(R.string.date_format, selectedDay, selectedMonth + 1, selectedYear))
            },
            currentYear,
            currentMonth,
            currentDay
        )
        // Set a minimum date to disable dates below 16 years ago
        val maxDate = Calendar.getInstance()
        maxDate.add(Calendar.YEAR, -16)
        datePickerDialog.datePicker.maxDate = maxDate.timeInMillis
        // Show the date picker dialog
        datePickerDialog.show()
    }


}