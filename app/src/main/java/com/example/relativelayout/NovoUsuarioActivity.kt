package com.example.relativelayout

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.RadioButton

import java.util.*

class NovoUsuarioActivity : AppCompatActivity() {

    lateinit var editEmail: EditText
    lateinit var editSenha: EditText
    lateinit var editNome: EditText
    lateinit var editProfissao: EditText
    lateinit var editAltura: EditText
    lateinit var editDataNascimento: EditText
    lateinit var buttonFeminino : RadioButton
    lateinit var buttonMasculino : RadioButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_usuario)

       editEmail = findViewById<EditText>(R.id.edit_email)
        editSenha = findViewById<EditText>(R.id.edit_senha)
        editNome = findViewById<EditText>(R.id.edit_nome)
        editProfissao = findViewById<EditText>(R.id.edit_profissao)
        editAltura = findViewById<EditText>(R.id.edit_altura)
        editDataNascimento = findViewById<EditText>(R.id.edit_data)
        buttonFeminino = findViewById<RadioButton>(R.id.button_feminino)
        buttonMasculino = findViewById<RadioButton>(R.id.button_masculino)


        supportActionBar!!.title = "PERFIL"

        // criando o Calendário
        val calendario = Calendar.getInstance()

// determinar os dados (dia / mês / ano)
        val ano = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

// abrir o componente Date Picker
        val etDataNascimento = findViewById<EditText>(R.id.edit_data)
        etDataNascimento.setOnClickListener {
            val dp = DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener{view, _ano, _mes, _dia ->
                        etDataNascimento.setText("$_dia/$_mes/$_ano")
                    }, ano, mes, dia)

            dp.show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_novo_usuario, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (validarCampos()){

        }
        return true
    }

    fun validarCampos() : Boolean {

       var valido = true

        if(editEmail.text.isEmpty()){
            editEmail.error = "O e-mail é obrigatório!"
            valido = false
        }
        if(editSenha.text.isEmpty()){
            editSenha.error = "A senha é obrigatória!"
            valido = false
        }
        if(editNome.text.isEmpty()){
            editNome.error = "O nome é obrigatório!"
            valido = false
        }
        if(editAltura.text.isEmpty()){
            editAltura.error = "A altura é obrigatório!"
            valido = false
        }
        if(editDataNascimento.text.isEmpty()){
            editDataNascimento.error = "A data de nascimento é obrigatório!"
            valido = false
        }
        if(editProfissao.text.isEmpty()){
            editProfissao.error = "A profissão é obrigatório!"
            valido = false
        }
        if(buttonFeminino.isChecked || buttonMasculino.isChecked){
            valido = true
        }else{
            buttonMasculino.error = "Preencha um dos campos!"
            buttonFeminino.error = "Preencha um dos campos!"
        }
        return valido
    }
}
