package com.example.jogoadivinhacao

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs
import kotlin.random.Random

class JogoActivity : AppCompatActivity() {

    private lateinit var txtBoasVindas: TextView
    private lateinit var txtDica: TextView
    private lateinit var txtTentativas: TextView
    private lateinit var edtPalpite: EditText
    private lateinit var btnChutar: Button
    private lateinit var btnJogarNovamente: Button

    private var nome = ""
    private var maximo = 50
    private var secreto = 0
    private var tentativas = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogo)

        txtBoasVindas = findViewById(R.id.txtBoasVindas)
        txtDica = findViewById(R.id.txtDica)
        txtTentativas = findViewById(R.id.txtTentativas)
        edtPalpite = findViewById(R.id.edtPalpite)
        btnChutar = findViewById(R.id.btnChutar)
        btnJogarNovamente = findViewById(R.id.btnJogarNovamente)

        nome = intent.getStringExtra("nome") ?: "Jogador"
        maximo = intent.getIntExtra("maximo", 50)

        iniciarJogo()

        btnChutar.setOnClickListener {
            verificarPalpite()
        }

        btnJogarNovamente.setOnClickListener {
            finish()
        }
    }

    private fun iniciarJogo() {
        secreto = Random.nextInt(1, maximo + 1)
        tentativas = 0

        txtBoasVindas.text = "$nome, pensei em um número de 1 a $maximo!"
        txtDica.text = "Faça seu primeiro palpite."
        txtTentativas.text = "Tentativas: 0"

        edtPalpite.text.clear()
        edtPalpite.isEnabled = true
        btnChutar.isEnabled = true
        btnJogarNovamente.visibility = Button.GONE
    }

    private fun verificarPalpite() {
        val texto = edtPalpite.text.toString().trim()

        if (texto.isEmpty()) {
            edtPalpite.error = "Digite um número"
            edtPalpite.requestFocus()
            return
        }

        val palpite = texto.toIntOrNull()

        if (palpite == null) {
            edtPalpite.error = "Digite apenas números"
            edtPalpite.requestFocus()
            return
        }

        if (palpite !in 1..maximo) {
            edtPalpite.error = "Digite um número entre 1 e $maximo"
            edtPalpite.requestFocus()
            return
        }

        tentativas++
        txtTentativas.text = "Tentativas: $tentativas"

        if (palpite == secreto) {
            txtDica.text = "$nome acertou em $tentativas tentativas!"
            edtPalpite.isEnabled = false
            btnChutar.isEnabled = false
            btnJogarNovamente.visibility = Button.VISIBLE
            return
        }

        val diferenca = abs(secreto - palpite)
        val limiteQuente = maximo * 0.10

        val temperatura = if (diferenca <= limiteQuente) "Quente!" else "Frio!"

        txtDica.text = if (palpite < secreto) {
            "$temperatura O número é MAIOR."
        } else {
            "$temperatura O número é MENOR."
        }

        edtPalpite.text.clear()
        edtPalpite.requestFocus()
    }
}
