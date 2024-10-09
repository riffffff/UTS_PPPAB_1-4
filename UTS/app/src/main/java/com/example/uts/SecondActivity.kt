package com.example.uts

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uts.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val name = intent.getStringExtra("username")

        binding.keluar.setOnClickListener {
            val intent = Intent (this@SecondActivity, ThirdActivity::class.java)
            startActivity(intent)
            Toast.makeText(
                this, "You have successfully logged out",
                Toast.LENGTH_SHORT
            ).show()
        }
        with(binding) {
            username.text=name
            }
    }


}