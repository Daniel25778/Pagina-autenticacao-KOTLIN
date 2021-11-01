package com.example.relativelayout.ui

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64.encodeToString
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.relativelayout.R
import com.example.relativelayout.model.Usuario
import com.example.relativelayout.utils.convertStringToLocalDate
import java.time.LocalDate
import java.io.ByteArrayOutputStream

import java.util.*

const val CODE_IMAGE = 100

class NovoUsuarioActivity : AppCompatActivity() {

    lateinit var editEmail: EditText
    lateinit var editSenha: EditText
    lateinit var editNome: EditText
    lateinit var editProfissao: EditText
    lateinit var editAltura: EditText
    lateinit var editDataNascimento: EditText
    lateinit var buttonFeminino : RadioButton
    lateinit var buttonMasculino : RadioButton
    lateinit var tvTrocarFoto : TextView
    lateinit var ivFotoPerfil : ImageView
    var imageBitmap: Bitmap? = null



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
        tvTrocarFoto = findViewById<TextView>(R.id.tv_trocar_foto)
        ivFotoPerfil = findViewById<ImageView>(R.id.iv_foto_perfil)




        supportActionBar!!.title = "PERFIL"

        tvTrocarFoto.setOnClickListener {
            abrirGaleria()
        }

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

                        var diaFinal = _dia
                        var mesFinal = _mes + 1

                        var mesString = "$mesFinal"
                        var diaString = "$diaFinal"


                        if((mesFinal) < 10){
                            mesString  = "0$mesFinal"
                        }

                        if((diaFinal) < 10){
                           diaString = "0$diaFinal"
                        }

                        etDataNascimento.setText("$diaString/$mesString/$_ano")
                    }, ano, mes, dia)
            dp.show()

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imagem: Intent?) {
        super.onActivityResult(requestCode, resultCode, imagem)

        if (requestCode == CODE_IMAGE && resultCode == -1){
            //Recuperar a imagem do  stream
            val fluxoImagem = contentResolver.openInputStream(imagem!!.data!!)

            imageBitmap = BitmapFactory.decodeStream(fluxoImagem)

            ivFotoPerfil.setImageBitmap(imageBitmap)

        }
    }

    private  fun abrirGaleria(){

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"

        // Abrir activity responsavel por exibir as imagens
        //Esta activity retornará o conteudo selecionado
        // para o nosso app

        startActivityForResult(
                Intent.createChooser( intent,
                        "Escolha uma foto"),
                CODE_IMAGE
                )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_novo_usuario, menu)
        return true
    }

    private fun encodeImage (bitmap: Bitmap): String? {
        val bitmapArray = ByteArrayOutputStream ()
        bitmap.compress (Bitmap.CompressFormat.JPEG, 100, bitmapArray)
        val b = bitmapArray.toByteArray ()
        Base64.encodeToString(b, Base64.DEFAULT )
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (validarCampos()){

            val nascimento = convertStringToLocalDate(editDataNascimento.text.toString())
            //Criar o objeto usuario
                val usuario = Usuario(
                        1,
                        editNome.text.toString(),
                        editEmail.text.toString(),
                        editSenha.text.toString(),
                        peso = 0,
                        editAltura.text.toString().toDouble(),
                        LocalDate.of(nascimento.year,
                                nascimento.monthValue,
                                nascimento.dayOfMonth
                        ),
                        editProfissao.text.toString(),
                        if(buttonFeminino.isChecked){
                            'F'
                        }else{
                            'M'
                        },
                        ""

                )

            //Salvar o registro
                //Em um SharedPreferences

                    //A instrução abaixo

            val dados = getSharedPreferences(
                    "usuario", Context.MODE_PRIVATE)

            val editor = dados.edit()
            editor.putInt("id", usuario.id)
            editor.putString("nome", usuario.nome)
            editor.putString("email", usuario.email)
            editor.putString("senha", usuario.senha)
            editor.putInt("peso", usuario.peso)
            editor.putFloat("altura", usuario.altura.toFloat())
            editor.putString("senha", usuario.senha)
            editor.putString("dataNascimento", usuario.dataNascimento.toString())
            editor.putString("profissao", usuario.profissao)
            editor.putString("sexo", usuario.sexo.toString())
            editor.apply()


        }
        Toast.makeText(this, "Usuário cadastrado!!", Toast.LENGTH_SHORT).show()
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
