package com.example.practicafinaldiseo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.practicafinaldiseo.models.Producto
import com.example.practicafinaldiseo.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registro.*

class Registro : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        title = "Registro de Usuario"
        database = Firebase.database.reference

        btRegistroFinal.setOnClickListener {

            var email = etEmailRegistro.text
            var pass = etPassRegistro.text
            var nombre = etNombreRegistro.text


            if (email.isNotEmpty() && pass.isNotEmpty() && nombre.isNotEmpty()){

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(etEmailRegistro.text.toString(),etPassRegistro.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                        mostrarSesionRegistradaOk()
                        var usuario = Firebase.auth.currentUser
                        var uid = usuario?.uid
                        val usuarioNuevo = User()
                        usuarioNuevo.nombre = nombre.toString()
                        usuarioNuevo.email = email.toString()
                        usuarioNuevo.provider = "Email"
                        usuarioNuevo.uid = uid.toString()
                        //usuarioNuevo.cesta = arrayListOf()
                        //usuarioNuevo.cesta.add(Producto("","",0.0,""))
                        database.child("Usuarios").child(uid.toString()).setValue(usuarioNuevo).addOnCompleteListener {
                            if(it.isSuccessful){
                                startActivity(Intent(this,Login::class.java))
                            }
                        }
                    }else{
                        mostrarErrorRegistro()
                    }
                }
            }
        }

    }
    private fun mostrarSesionRegistradaOk() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Registrado")
        builder.setMessage("Registrado correctamente")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun mostrarErrorRegistro(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Error al completar el registro")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }

}