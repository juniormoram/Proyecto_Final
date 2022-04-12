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

}