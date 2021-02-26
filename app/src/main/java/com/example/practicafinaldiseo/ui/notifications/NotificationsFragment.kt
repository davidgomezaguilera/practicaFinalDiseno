package com.example.practicafinaldiseo.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.renderscript.Sampler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.practicafinaldiseo.Login
import com.example.practicafinaldiseo.R
import com.example.practicafinaldiseo.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_notifications.view.*

class NotificationsFragment : Fragment() {

    private lateinit var database: DatabaseReference
    var email =""
    var nombre =""
    var provider = ""

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        database = Firebase.database.reference
        var usuario = Firebase.auth.currentUser
        var uidUsuario = usuario?.uid

        root.btLogOut.setOnClickListener {

            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(root.context,Login::class.java))
        }


        var referencia = database.child("Usuarios").child(uidUsuario.toString())
        referencia.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                println("OnDataChange")
                for(hijos in snapshot.children){
                    println("Dentro del for")
                    if(hijos.key.equals("email")) {
                        email = hijos.value.toString()
                        root.tvEmailPerfil.setText(email)
                    }
                    if(hijos.key.equals("nombre")){
                        nombre = hijos.value.toString()
                        root.tvNombrePerfil.setText(nombre)
                    }
                    if(hijos.key.equals("provider")){
                        provider = hijos.value.toString()
                        root.tvProviderPerfil.setText(provider)
                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
        return root
    }
}