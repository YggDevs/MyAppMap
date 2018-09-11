package com.example.yeray.myapplication

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.util.*

class VerClientesLista : AppCompatActivity() {

    private var txtResultado: TextView? = null


    private var btnConsultar: Button? = null
    private var btnNavegar: Button? = null

    private var db: SQLiteDatabase? = null      //Conector base de datos


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_clientes_lista)






        txtResultado = findViewById(R.id.txtResultado)


        btnConsultar = findViewById(R.id.btnConsultar)
        btnNavegar = findViewById(R.id.btnNavegar)


        /* Abrimos la base de datos 'DBUsuarios' en modo escritura
         */


        val usdbh = UsuariosSQLiteHelper(this, "DBUsuarios", null, 5)

        db = usdbh.writableDatabase


        fun visitarclientes(lat: Double, lon: Double) {


            val uri = String.format(Locale.ENGLISH, "google.navigation:q=%1\$f,%2\$f", lat, lon)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)


        }


        btnConsultar!!.setOnClickListener {
            val c = db!!.rawQuery("SELECT * FROM Usuarios", null)


            txtResultado!!.text = ""
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    val cod = c.getString(0)
                    val nom = c.getString(1)
                    val direc = c.getString(2)
                    val horario = c.getString(3)
                    val longitud = c.getDouble(4)
                    val latitud = c.getDouble(5)





                    txtResultado!!.append(" " + cod + " - " + nom + " - " + direc + "-" + horario + "\n" +
                            "Longitud" + longitud + "\n" +
                            "Latitud " + latitud + "\n")



                } while (c.moveToNext())
            }
        }


        btnNavegar!!.setOnClickListener {
            val c = db!!.rawQuery("SELECT * FROM Usuarios", null)



            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {

                    val longitud = c.getDouble(4)
                    val latitud = c.getDouble(5)



                    visitarclientes(latitud, longitud)


                } while (c.moveToNext())
            }


        }


    }



    }

