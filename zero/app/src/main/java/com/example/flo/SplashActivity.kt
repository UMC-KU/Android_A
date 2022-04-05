package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySplashBinding


//액티비티를 만들면 manifest 파일에 알려줘야해
class SplashActivity :AppCompatActivity() {
    lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //핸들러 이용한 방법
       val handler =Handler(Looper.getMainLooper())
        handler.postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
        }, 1000)

    }

}