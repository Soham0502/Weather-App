package com.example.weatherapp.app


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import kotlin.random.Random

class SplashActivity : AppCompatActivity() {

    private val randomTexts = arrayOf(
        "Its all about finding the                   CALM in the CHAOS :)",
        "No Storm can last forever! ",
        "After every STORM,\nthe SUN comes out <3",
        "The SUN always comes out after a STORM <3",
        "The sound of Rain.....",
        "Somedays, you just have to create your own Sunshine :)",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val randomTextView: TextView = findViewById(R.id.randomTextView)
        randomTextView.text = randomTexts[Random.nextInt(randomTexts.size)]

        Handler(getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        },3000)
    }
}






