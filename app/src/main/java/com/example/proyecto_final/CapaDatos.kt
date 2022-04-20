package com.example.proyecto_final

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.room.*
import com.google.gson.annotations.SerializedName

class CapaDatos {

    class Prefs (context: Context)
    {
        val PREFS_NAME = "com.cursokotlin.sharedpreferences"
        val SHARED_NAME2 = ""
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

        var name2: String?
            get() = prefs.getString(SHARED_NAME2, "")
            set(value) = prefs.edit().putString(SHARED_NAME2, value).apply()
    }

    class SharedApp : Application() {
        companion object {
            lateinit var prefs: Prefs
            lateinit var UbicacionUsu: UBICACIONUSUARIO
            lateinit var Foto : String
            lateinit var NomUsuario: String
            lateinit var Usuario : Usuario
            lateinit var UsuarioJ : Usuario
            lateinit var listaUsuarios : MutableList<Usuario>
            var image_uri: Uri? = null
            lateinit var database: UserDatabase
        }

        override fun onCreate() {
            super.onCreate()
            prefs = Prefs(applicationContext)
            listaUsuarios = ArrayList()
            Usuario = Usuario("","","","","")
            UsuarioJ = Usuario("","","","","")
            NomUsuario = ""
            database =  Room.databaseBuilder(this, UserDatabase::class.java, "user-db").build()

        }
    }

    data class UBICACIONUSUARIO(

        var ejex:String,
        var ejey:String,
    )

    data class Usuario (
        @SerializedName("Usuario") var Usuario: String,
        @SerializedName("Nombre") var Nombre: String,
        @SerializedName("Apellido") var Apellido: String,
        @SerializedName("Contraseña") var Contraseña:  String,
        @SerializedName("Imagen") var Imagen:  String
    )

    @Entity(tableName = "user_db")
    data class UserEntity (
        @PrimaryKey()
        var usuario:String = "",
        var Nombre:String = "",
        var apellido:String= "",
        var Contrasena:String = "",
        var imagen:String = "",
        var isDone:Boolean = false
    )

    @Dao
    interface TaskDao {
        @Query("SELECT * FROM user_db ")
        fun getAllTasks(): MutableList<UserEntity>
        @Insert
        fun addTask(taskEntity : UserEntity):Long
        @Query("SELECT * FROM user_db where usuario like :usuario")
        fun getTaskById(usuario: String): MutableList<UserEntity>
        @Update
        fun updateTask(userEntity: UserEntity):Int
        @Delete
        fun deleteTask(userEntity: UserEntity):Int
        @Query("SELECT NULLIF(max(usuario),0) FROM user_db")
        fun getMaxTaskid(): Int
    }

    @Database(entities = arrayOf(UserEntity::class), version = 1)
    abstract class UserDatabase : RoomDatabase() {
        abstract fun taskDao(): TaskDao
    }


    ////

    data class UBICACION(
        @SerializedName("PROVINCIA1") var PROVINCIA1: String = "",
        @SerializedName("CANTON1") var CANTON1: String = "",
        @SerializedName("DISTRITO1") var DISTRITO1: String = "",
        @SerializedName("BARRIO1") var BARRIO1: String = "",
        @SerializedName("OTRASSENAS1") var OTRASSENAS1: String = "",
        @SerializedName("ID1") var ID1: String = ""
    )
    data class TELEFONOFAX
        (
        @SerializedName("CODIGOPAIS1") var CODIGOPAIS1: String = "",
        @SerializedName("NUMTELEFONO1") var NUMTELEFONO1: String = ""
    )
    data class PERSONA
        (
        @SerializedName("NOMBRE1") var NOMBRE1: String = "",
        @SerializedName("TIPO") var TIPO: String = "",
        @SerializedName("NUMERO1") var NUMERO1: String = "",
        @SerializedName("NOMBRECOMERCIAL1") var NOMBRECOMERCIAL1: String = "",
        @SerializedName("CORREOELECTRONICO1") var CORREOELECTRONICO1: String = "",
        @SerializedName("IDENTIFICACIONEXTRANJERO1") var IDENTIFICACIONEXTRANJERO1: String = "",
        @SerializedName("IDUBICACION1") var IDUBICACION1: UBICACION = UBICACION(),
        @SerializedName("TELEFONO1") var TELEFONO1: TELEFONOFAX = TELEFONOFAX(),
        @SerializedName("FAX1") var FAX1: TELEFONOFAX = TELEFONOFAX()
    )

    data class EMPRESA(
        @SerializedName("CEDULAJURIDICA") var CEDULAJURIDICA: String,
        @SerializedName("DESPEDIDA") var DESPEDIDA: String,
        @SerializedName("DIRECCION") var DIRECCION: String,
        @SerializedName("DIRECCIONXY") var DIRECCIONXY: String,
        @SerializedName("ID") var ID: Number,
        @SerializedName("IMAGENURL") var IMAGENURL: String,
        @SerializedName("IMAGENURLMOVIL") var IMAGENURLMOVIL: String,
        @SerializedName("IMPUESTOAPLICA") var IMPUESTOAPLICA: String,
        @SerializedName("NOMBRE") var NOMBRE: String,
        @SerializedName("PAGADO") var PAGADO: String,
        @SerializedName("SALUDO") var SALUDO: String,
        @SerializedName("TELEFONO1") var TELEFONO1: String,
        @SerializedName("TELEFONO2") var TELEFONO2: String,
        @SerializedName("URLESCENARIO") var URLESCENARIO: String
    )

    data class EVENTO(
        @SerializedName("DESCRIPCION") var DESCRIPCION: String,
        @SerializedName("DETALLE") var DETALLE: String,
        @SerializedName("EMPRESA") var EMPRESA: String,
        @SerializedName("ID") var ID: Number,
        @SerializedName("IMAGENURL") var IMAGENURL: String,
        @SerializedName("IMPUESTO") var IMPUESTO: Number,
        @SerializedName("NOMBRE") var NOMBRE: String
    )
    data class LISTA_PRECIOS(

        @SerializedName("CODIGO_ACCESO") var CODIGO_ACCESO:String,
        @SerializedName("ID") var ID: Number,
        @SerializedName("NOMBRE") var NOMBRE:String,
        @SerializedName("NUMERO") var NUMERO:Number,
        @SerializedName("TOTAL") var TOTAL:Number

    )

    //clase padre donde se utiliza relaciones de composicion con las clases anteriores para obtener una unica clase con los
    //atributos necesarios que devuelve la peticion
    data class FECHA(
        @SerializedName("ANNO") var ANNO: Number,
        @SerializedName("CERRADO") var CERRADO: String,
        @SerializedName("DIA") var DIA: Number,
        @SerializedName("EMPRESA") var EMPRESA: EMPRESA,
        @SerializedName("EVENTO") var EVENTO: EVENTO,
        @SerializedName("HORAS_CADUCA") var HORAS_CADUCA: Number,
        @SerializedName("HORA_FIN") var HORA_FIN:String,
        @SerializedName("HORA_INICIA") var HORA_INICIA:String,
        @SerializedName("ID") var ID: Number,
        @SerializedName("LISTA_PRECIOS") var LISTA_PRECIOS: LISTA_PRECIOS,
        @SerializedName("MES") var MES: Number,
        @SerializedName("NUMERO_HORA_FIN") var NUMERO_HORA_FIN: Number,
        @SerializedName("NUMERO_HORA_INICIA") var NUMERO_HORA_INICIA: Number,
        @SerializedName("NUMERO_MINUTO_FIN") var NUMERO_MINUTO_FIN: Number,
        @SerializedName("NUMERO_MINUTO_INICIA") var NUMERO_MINUTO_INICIA: Number,
        @SerializedName("TERMINAL") var TERMINAL:String
    )

}