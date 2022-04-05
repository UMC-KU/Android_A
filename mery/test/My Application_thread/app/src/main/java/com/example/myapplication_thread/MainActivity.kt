package com.example.myapplication_thread

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication_thread.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageList = arrayListOf<Int>()
        imageList.add(R.drawable.bear)







    }
}