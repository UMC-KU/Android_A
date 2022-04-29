package com.example.mythread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import com.example.mythread.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val handler = Handler(Looper.getMainLooper())
        val imageList = arrayListOf<Int>()

        imageList.add(R.drawable.bear)
        imageList.add(R.drawable.duke)
        imageList.add(R.drawable.just)
        imageList.add(R.drawable.bear)
        imageList.add(R.drawable.duke)
        imageList.add(R.drawable.just)
        imageList.add(R.drawable.bear)
        imageList.add(R.drawable.duke)
        imageList.add(R.drawable.just)

        Thread{
            for (image in imageList){
                runOnUiThread{
                    binding.iv.setImageResource(image)
                }
                Thread.sleep(2000)
                }
            }.start()
        }
    }
}