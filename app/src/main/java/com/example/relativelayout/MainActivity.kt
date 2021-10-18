package com.example.relativelayout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val naoTemConta = findViewById<TextView>(R.id.text_nao_tem_conta)

        naoTemConta.setOnClickListener {
            val abrirNovoUsuario = Intent( this, NovoUsuarioActivity::class.java )
            startActivity(abrirNovoUsuario)

           }



        }
    }




