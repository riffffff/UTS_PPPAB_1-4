package com.example.uts

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Deklarasi variabel binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submit.setOnClickListener {
            checkFieldsAndShowAlert()
        }
    }
    private fun checkFieldsAndShowAlert() {
        val name = binding.enterName.text.toString()
        if (name.isEmpty()) {
            Toast.makeText(
                this, "Enter your name first",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            intent.putExtra("username", name)
            startActivity(intent)
        }
    }
}

