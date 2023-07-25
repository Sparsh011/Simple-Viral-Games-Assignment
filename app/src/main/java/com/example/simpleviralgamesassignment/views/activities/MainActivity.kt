package com.example.simpleviralgamesassignment.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.simpleviralgamesassignment.R
import com.example.simpleviralgamesassignment.views.fragments.HomeScreenFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val defaultFragment = HomeScreenFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, defaultFragment)
            .commit()
    }
}