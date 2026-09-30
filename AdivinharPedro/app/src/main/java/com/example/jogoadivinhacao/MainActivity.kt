package com.example.jogoadivinhacao

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtNome = findViewById<EditText>(R.id.edtNome)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupLimite)
        val btnJogar = findViewById<Button>(R.id.btnJogar)

        btnJogar.setOnClickListener {
            val nome = edtNome.text.toString().trim()

            if (nome.isEmpty()) {
                edtNome.error = "Digite seu nome"
                edtNome.requestFocus()
                return@setOnClickListener
            }

            val selecionado = radioGroup.checkedRadioButtonId

            if (selecionado == -1) {
                Toast.makeText(this, "Escolha um limite", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val radio = findViewById<RadioButton>(selecionado)
            val maximo = radio.tag.toString().toInt()

            val intent = Intent(this, JogoActivity::class.java)
            intent.putExtra("nome", nome)
            intent.putExtra("maximo", maximo)
            startActivity(intent)
        }
    }
}
