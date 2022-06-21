package com.example.simuladordepartidas.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.simuladordepartidas.databinding.ActivityDetailBinding
import com.example.simuladordepartidas.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}