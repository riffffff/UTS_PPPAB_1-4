package com.example.taskreminder

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taskreminder.databinding.ActivitySecondBinding
import android.widget.DatePicker

class SecondActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private val binding by lazy {
        ActivitySecondBinding.inflate(layoutInflater)
    }

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.second)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val stringRepeat = resources.getStringArray(R.array.repeat)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        with(binding) {
            // Setup Spinner
            val adapterSpinnerRepeat = ArrayAdapter(this@SecondActivity, android.R.layout.simple_spinner_item, stringRepeat)
            spinnerRepeat.adapter = adapterSpinnerRepeat
            spinnerRepeat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    // Handle item selection if needed
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Handle when nothing is selected
                }
            }

            // TimePicker listener
            timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
                val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                Toast.makeText(this@SecondActivity, selectedTime, Toast.LENGTH_SHORT).show()
            }

            // Date Picker Dialog
            btnPickDate.setOnClickListener {
                val datePickerDialog = DatePickerDialog(
                    this@SecondActivity,
                    { _, selectedYear, selectedMonth, selectedDay ->
                        val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                        btnPickDate.text = selectedDate
                    }, year, month, day
                )
                datePickerDialog.show()
            }

            // Button to add task
            binding.button.setOnClickListener {
                checkFieldsAndShowAlert()
            }
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        val selectedDate = "$day/${month + 1}/$year"
        Toast.makeText(this, selectedDate, Toast.LENGTH_SHORT).show()
    }

    private fun checkFieldsAndShowAlert() {
        val title = binding.edtTitle.text.toString()
        val repeat = binding.spinnerRepeat.selectedItem?.toString()
        val date = binding.btnPickDate.text.toString()
        val time = String.format("%02d:%02d %s", binding.timePicker.hour % 12, binding.timePicker.minute, if (binding.timePicker.hour < 12) "AM" else "PM")

        if (title.isEmpty() || repeat == null || date.isEmpty() || time.isEmpty()) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Error")
            builder.setMessage("All fields must be filled")
            builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            builder.show()
        } else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("SimpliRemind")
            builder.setMessage("Do you want to add this as a new task?")
            builder.setPositiveButton("Ya") { _, _ ->
                val intent = Intent(this@SecondActivity, ThirdActivity::class.java)
                intent.putExtra("title", title)
                intent.putExtra("repeat", repeat)
                intent.putExtra("time", time)
                intent.putExtra("date", date)
                startActivity(intent)
            }
            builder.setNegativeButton("Tidak") { dialog, _ -> dialog.dismiss() }
            builder.show()
        }
    }
}
