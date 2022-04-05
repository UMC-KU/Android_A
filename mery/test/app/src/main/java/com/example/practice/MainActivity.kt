package com.example.practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val a = A()
        val b = B()

        a.start()
        a.join()
        b.start()
    }

    class A :Thread(){
        override fun run(){
            super.run()
            for (i in 1..1000){
                Log.d("test", "first : $i")

            }
        }
    }
    class B :Thread(){
        override fun run(){
            super.run()
            for (i in 1000 downTo 1){
                Log.d("test", "first : $i")

            }
        }
    }
}