package com.example.proyecto_final

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.proyecto_final.CapaDatos.SharedApp.Companion.image_uri
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_registro.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.io.ByteArrayOutputStream
import java.io.InputStream

class RegistroActivity : AppCompatActivity() {

    private val PERMISSION_CODE = 1000;
    private val IMAGE_CAPTURE_CODE = 1001
    lateinit var users: MutableList<CapaDatos.UserEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_CONTACTS), 1
        )
        users = ArrayList()

        lbl_Termin.setOnClickListener {
            startActivity(Intent(this, TerminosActivity::class.java))
        }
        btn_Registro.setOnClickListener {
            if (cbx_terminos2.isChecked) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Contactos")
                builder.setMessage("Sus contactos serán obtenidos")
                builder.setIcon(android.R.drawable.ic_dialog_alert)

                builder.setPositiveButton("Si") { dialogInterface, which ->

                    UsuarioNuevoPref()
                    GetContactList()
                    CorrerCorrutina()

                }
                builder.setNegativeButton("No") { dialogInterface, which ->
                    val listaser2 = CapaDatos.SharedApp.prefs.name2
                    Toast.makeText(applicationContext, listaser2.toString(), Toast.LENGTH_LONG)
                        .show()
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()


            } else {
                val text = "Favor aceptar terminos y condiciones"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }
        }
        btnCamara.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                    val permission = arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permission, PERMISSION_CODE)
                }
                else{
                    openCamera()
                }
            }
            else{
                openCamera()
            }}

    }
    private fun encodeImage(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }
    fun CorrerCorrutina(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIServiceInsertar::class.java).registrationPost(listadecontactosentexto).execute()
            runOnUiThread {
                Log.i("", listadecontactosentexto.toString())
                Log.i("", call.body().toString())
                Log.i("", call.toString())
            }
        }
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")

        image_uri = contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    var imagenenbase64 = ""
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            var foto: ImageView = findViewById(R.id.imageViewRegistro)
            foto.setImageURI(image_uri)
            val imageStream: InputStream?
                    = image_uri?.let { contentResolver.openInputStream(it) }
            val selectedImage = BitmapFactory.decodeStream(imageStream)
            imagenenbase64 =  encodeImage(selectedImage)
            Log.d("imbase64",imagenenbase64)

        }
    }

    fun UsuarioNuevoPref(){
        val text = "Usuario agregado"
        val duration = Toast.LENGTH_SHORT
        CapaDatos.SharedApp.Usuario.Nombre = txt_Nombre.text.toString()
        CapaDatos.SharedApp.Usuario.Apellido = txt_Apellido.text.toString()
        CapaDatos.SharedApp.Usuario.Contraseña = txt_Contraseña.text.toString()
        CapaDatos.SharedApp.Usuario.Usuario = txt_UsuarioR.text.toString()
        CapaDatos.SharedApp.Usuario.Imagen = imagenenbase64
        val listaser = CapaDatos.SharedApp.prefs.name2
        if(listaser != "") {
            val gson = GsonBuilder().create()
            val Model = gson.fromJson(listaser, Array<CapaDatos.Usuario>::class.java).toMutableList()
            CapaDatos.SharedApp.listaUsuarios = Model
            CapaDatos.SharedApp.listaUsuarios.add(CapaDatos.SharedApp.Usuario)
        }else {
            CapaDatos.SharedApp.listaUsuarios.add(CapaDatos.SharedApp.Usuario)
        }
        val gson = Gson()
        val listaser2 = gson.toJson(CapaDatos.SharedApp.listaUsuarios)
        CapaDatos.SharedApp.prefs.name2 = listaser2
        Log.d("Ususario agregado: ", CapaDatos.SharedApp.listaUsuarios.toString())
        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()
        clearFocus()
        hideKeyboard()

    }
    fun clearFocus(){
        txt_UsuarioR.setText("")
        txt_Apellido.setText("")
        txt_Contraseña.setText("")
        txt_Nombre.setText("")

    }
    fun Context.hideKeyboard() {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
    var listadecontactosentexto: String=""

    @SuppressLint("Range")
    private fun GetContactList() {
        val cr: ContentResolver = contentResolver
        val cur: Cursor?
        cur  = cr.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )
        if ((cur?.getCount() ?: 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                val id: String = cur.getString(
                    cur.getColumnIndex(ContactsContract.Contacts._ID)
                )
                val name: String = cur.getString(
                    cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME
                    )
                )
                if (cur.getInt(
                        cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER
                        )
                    ) > 0
                ) {
                    val pCur: Cursor?
                    pCur  = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )
                    while (pCur!!.moveToNext()) {

                        val phoneNo: String = pCur.getString(
                            pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER

                            )
                        )
                        listadecontactosentexto = "$listadecontactosentexto $name : $phoneNo ; "
                    }
                    pCur.close()
                }
            }
        }

        Log.i("contactos", listadecontactosentexto)
        cur?.close()
    }
    interface APIServiceInsertar {
        @FormUrlEncoded
        @POST("contactos")
        fun registrationPost(
            @Field("DATA") op: String
        ): Call<String>
    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://semiamigo.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}