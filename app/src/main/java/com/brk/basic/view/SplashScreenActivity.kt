package com.brk.basic.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.brk.basic.R
import com.brk.basic.databinding.ActivityDetayBinding
import com.brk.basic.databinding.ActivitySplashScreenBinding
import com.bumptech.glide.Glide

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){

        Glide.with(this)
            .asGif()
            .load(R.raw.animation)  // Replace with your GIF resource
            .into(binding.imageView11)

        Handler().postDelayed({
            navigateToAnotherActivity()
        }, 2200)
    }


    private fun navigateToAnotherActivity() {
        // Diğer aktiviteye yönlendir
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Bu aktiviteyi kapat
    }

}