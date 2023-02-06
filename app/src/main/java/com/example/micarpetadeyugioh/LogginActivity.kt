package com.example.micarpetadeyugioh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import com.bumptech.glide.Glide
import com.example.micarpetadeyugioh.databinding.LogginActivityBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.Provider

class LogginActivity : AppCompatActivity(), OnQueryTextListener {

    private lateinit var binding: LogginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LogginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
        binding.svNombreDeCarta.setOnQueryTextListener(this)
    }

    private fun getRetrofit():Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://db.ygoprodeck.com/api/v7/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByName(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).geCardInformation("cardinfo.php?name=$query")
            val cardResponse: CardResponse? = call.body()
            runOnUiThread {
                if (call.isSuccessful && cardResponse?.data?.firstOrNull() != null){
                    showSuccesfull(cardResponse.data.first())
                } else {
                    showError()
                }
            }

        }
    }

    private fun showError() {
        Toast.makeText(this, "ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    private fun showSuccesfull(information: CardResponseData) {
        if (information.card_images.isNotEmpty()) {
            val image: CardImage = information.card_images.first()
            bind(image.image_url)
        }
    }

    fun bind(image:String){
        Glide.with(this).load(image).into(binding.ivCardImage)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()){
            searchByName(query)}
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun setup(){

        binding.btnRegistrarse.setOnClickListener {
            if (binding.etCorreo.text.isNotEmpty() && binding.etContraseA.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(
                        binding.etCorreo.text.toString(),
                        binding.etContraseA.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            mostrarBienvenida(it.result?.user?.email ?: "")
                        } else {

                        }
                    }
            }
        }

        binding.btnIniciarSesion.setOnClickListener {
            if (binding.etCorreo.text.isNotEmpty() && binding.etContraseA.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(
                        binding.etCorreo.text.toString(),
                        binding.etContraseA.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            mostrarBienvenida(it.result?.user?.email ?: "")
                        } else {
                            showError()
                        }
                    }
            }
        }



    }

    private fun mostrarBienvenida(email:String){
        val homeIntent: Intent = Intent(this,HomeActivity::class.java).apply {
            putExtra("email",email)
        }
        startActivity(homeIntent)
    }

    private fun mostrarAlerta(correo:String,contraseña:String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("$correo $contraseña")
        builder.setPositiveButton("Error",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}