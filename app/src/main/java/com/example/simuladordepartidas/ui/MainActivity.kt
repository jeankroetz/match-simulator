package com.example.simuladordepartidas.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.simuladordepartidas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // binding.texView.text = "By world"

        setupMatchesList()
        setupMatchesRefresh()
        setupFloatingActionButton()
    }

    private fun setupFloatingActionButton() {
        //TODO("Criar evento de clique")
    }

    private fun setupMatchesRefresh() {
        //TODO("Atualizar as partidas na ação de swipe")
    }

    private fun setupMatchesList() {
        //TODO("Listar as partidas usando nossa API")
    }

}