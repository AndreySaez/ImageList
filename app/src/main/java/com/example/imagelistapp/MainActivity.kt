package com.example.imagelistapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.imagelistapp.main_list.presentation.view.ImageListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main, ImageListFragment())
                .commit()
        }
    }
}