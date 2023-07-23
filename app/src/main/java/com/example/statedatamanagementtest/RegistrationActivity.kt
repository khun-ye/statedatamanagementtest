package com.example.statedatamanagementtest

import CustomSpinnerAdapter
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import java.util.Calendar

class RegistrationActivity : AppCompatActivity() {

    val nationalityList = listOf("Singaporean")
    val countryList = listOf("Singapore")
    private val TAG = "RegistrationActivity"

    private lateinit var spinnerNationality: Spinner
    private lateinit var spinnerCountry: Spinner


    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var editTextDateOfBirth: EditText
    private lateinit var createAccountBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        spinnerNationality = findViewById(R.id.spinnerNationality)
        spinnerCountry = findViewById(R.id.spinnerCountry)
        setSupportActionBar(toolbar)
        firstNameEditText = findViewById(R.id.editTextFirstName)
        lastNameEditText = findViewById(R.id.editTextLastName)
        emailEditText = findViewById(R.id.editTextEmail)
        editTextDateOfBirth = findViewById(R.id.editTextDateOfBirth)
        createAccountBtn = findViewById(R.id.buttonSubmit)

        // Enable the back button in the Toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val nationalityAdapter =
            CustomSpinnerAdapter(this, R.layout.spinner_dropdown_item, nationalityList)
        val countryAdapter =
            CustomSpinnerAdapter(this, R.layout.spinner_dropdown_item, countryList)

        spinnerNationality.adapter = nationalityAdapter
        spinnerCountry.adapter = countryAdapter

        spinnerNationality.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedNationality = nationalityList[position]
                // Do something with the selected nationality
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case when nothing is selected
            }
        }

        spinnerCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCountry = countryList[position]
                // Do something with the selected country
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case when nothing is selected
            }
        }

        editTextDateOfBirth.setOnClickListener {
            showDatePickerDialog()
        }

        createAccountBtn.setOnClickListener {
            onSubmitClicked(it)
        }

    }

    private fun showDatePickerDialog() {
        // Get the current date to set as the default selected date in the DatePickerDialog
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Create and show the DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Set the selected date in the EditText in the desired format (e.g., DD/MM/YY)
                val selectedDate = String.format("%02d/%02d/%02d", dayOfMonth, monthOfYear + 1, year % 100)
                editTextDateOfBirth.setText(selectedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        this.finish()
        return true
    }

    // Validate the form before submitting
    private fun validateForm(): Boolean {
        // Validate first name
        val firstName = firstNameEditText.text.toString().trim()
        if (firstName.isEmpty()) {
            firstNameEditText.error = "Please enter your first name"
            firstNameEditText.requestFocus()
            return false
        }

        // Validate last name
        val lastName = lastNameEditText.text.toString().trim()
        if (lastName.isEmpty()) {
            lastNameEditText.error = "Please enter your last name"
            lastNameEditText.requestFocus()
            return false
        }

        // Validate email
        val email = emailEditText.text.toString().trim()
        if (email.isEmpty()) {
            emailEditText.error = "Please enter your email"
            emailEditText.requestFocus()
            return false
        } else if (!isEmailValid(email)) {
            emailEditText.error = "Please enter a valid email address"
            emailEditText.requestFocus()
            return false
        }

        // Validate date of birth
        val dateOfBirth = editTextDateOfBirth.text.toString().trim()
        if (dateOfBirth.isEmpty()) {
            editTextDateOfBirth.error = "Please select your date of birth"
            editTextDateOfBirth.requestFocus()
            return false
        }


        return true
    }

    // Helper method to check if the email is valid
    private fun isEmailValid(email: String): Boolean {
        val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailRegex.toRegex())
    }

    // Handle form submission
    private fun onSubmitClicked(view: View) {
        if (validateForm()) {
            Log.d(TAG,"Form Validate Success")
            Toast.makeText(this, "Successfully Create", Toast.LENGTH_SHORT).show()
            this.finish()
        }else{
            Log.d(TAG, "Form Validate Failed")
            Toast.makeText(this, "Fill out all data", Toast.LENGTH_SHORT).show()

        }
    }
}