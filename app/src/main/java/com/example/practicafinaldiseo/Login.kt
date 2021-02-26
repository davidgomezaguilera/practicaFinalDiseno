package com.example.practicafinaldiseo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.facebook.CallbackManager
import androidx.appcompat.app.AlertDialog
import com.example.practicafinaldiseo.models.Producto
import com.example.practicafinaldiseo.models.User
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    private val SING_IN_GOOGLE = 100;
    private val callbackManager = CallbackManager.Factory.create()
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(2000)
        setTheme(R.style.SplashTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        database = Firebase.database.reference
        var producto = Producto("Producto1","Descripcion Producto 1",10.50,"",1)
        database.child("Productos").child(producto.nombre).setValue(producto)
        iniciar()

        sesionIniciada()
    }
    override fun onRestart() {
        super.onRestart()
        loginLayout.visibility = View.VISIBLE
    }

    private fun sesionIniciada(){
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)

        if(email != null && provider != null){
            //esta linea de abajo lo que hace es que no nos muestre el layout donde se encuentra el login pero tenemos que controlar que nos lo vuelva a mostrar cuando volvamos al
            // cerrar sesion, eso lo controlamos en la funcion onRestart, que se ejecutara al volver al cerrar sesion.

            loginLayout.visibility = View.INVISIBLE
            mostrarActivityMain(email,  provider)
        }

    }

    private fun iniciar(){

        title = "Inicio de Sesión"

//boton registrar
        btRegistro.setOnClickListener {

            //esto quiere decir que los editText de email y contraseña no estan vacíos.
            startActivity(Intent(this, Registro::class.java))
        }

        btLogin.setOnClickListener {

            if (etEmail.text.isNotEmpty() && etPass.text.isNotEmpty()){
//este metodo es igual que el de registrar, solo cambia la propiedad que le añadimos al getInstance que en vez de crear un usuario, iniciamos sesion con el.
                FirebaseAuth.getInstance().signInWithEmailAndPassword(etEmail.text.toString(),etPass.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                        //aqui el usuario habria entrado correctamente
                        mostrarSesionIniciadaOk()
                        mostrarActivityMain(it.result?.user?.uid.toString() ?: "","Email y contraseña")
                    }else{
                        mostrarError()
                    }
                }
            }
        }

        btGoogle.setOnClickListener {

            val googleOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
            val googleClient = GoogleSignIn.getClient(this, googleOptions)
            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent, SING_IN_GOOGLE)
        }

        /*btFace.setOnClickListener {

            LoginManager.getInstance().logInWithReadPermissions(this,listOf("email"))

            LoginManager.getInstance().registerCallback(callbackManager,
                    object : FacebookCallback<LoginResult>{
                        override fun onSuccess(result: LoginResult?) {

                            result?.let {
                                val token = it.accessToken
                                val credencial = FacebookAuthProvider.getCredential(token.token)
                                FirebaseAuth.getInstance().signInWithCredential(credencial).addOnCompleteListener {
                                    if(it.isSuccessful){
                                        mostrarActivityMain(it.result?.user?.email ?: "", "FACEBOOK")
                                    }else{
                                        println("hola añsdlkfj")
                                        mostrarError()
                                    }
                                }
                            }

                        }

                        override fun onCancel() {

                        }

                        override fun onError(error: FacebookException?) {
                            mostrarError()
                        }
                    }
            )
        }
*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){

        callbackManager.onActivityResult(requestCode,resultCode,data)

        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == SING_IN_GOOGLE){

            val tarea = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val cuenta = tarea.getResult(ApiException::class.java)

                if(cuenta != null){
                    val credencial = GoogleAuthProvider.getCredential(cuenta.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credencial).addOnCompleteListener {
                        if(it.isSuccessful){
                            var usuario = Firebase.auth.currentUser
                            var uid = usuario?.uid
                            val usuarioNuevo = User()
                            usuarioNuevo.nombre = usuario?.displayName.toString()
                            usuarioNuevo.email = usuario?.email.toString()
                            usuarioNuevo.provider = "Google"
                            usuarioNuevo.uid = uid.toString()
                            println("DisplayName de cuenta "+cuenta.displayName)
                            println("DisplayName de usuario "+ usuario?.displayName)
                            database.child("Usuarios").child(uid.toString()).setValue(usuarioNuevo).addOnCompleteListener {
                                if(it.isSuccessful){
                                    mostrarActivityMain(cuenta.email ?: "", "GOOGLE")
                                }
                            }

                        }else{
                            println("hola1")
                            mostrarError()
                        }
                    }

                }
            }catch(e: ApiException){
                println("ERROOOOOOOOOOOR-----------------------"+e.message)
                mostrarError()
            }



        }


    }


    private fun mostrarSesionIniciadaOk() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Conectado")
        builder.setMessage("Conectado correctamente")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun mostrarError(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Error al completar el inicio de sesión")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }

    //funcion para mostrar un activity
    private fun mostrarActivityMain(email:String, provider:String){
        val logIntent = Intent(this,MainActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider", provider)
        }
        startActivity(logIntent)
    }

}