package com.example.yeray.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RutasActivity extends AppCompatActivity {



    private TextView txtResultado;


    private EditText txtCodigo;     // Texto editable base de datos
    private EditText txtNombre;
    private EditText txtDireccion;
    private EditText txtHorario;
    private EditText txtLatitude;
    private EditText txtLongitude;







    private Button btnInsertar;     //Botones para la base de datos
    private Button btnActualizar;
    private Button btnEliminar;
    private Button btnConsultar;

    private SQLiteDatabase db;      //Conector base de datos




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutas);


        /*   Obtenemos las referencias a los controles
         */


        txtCodigo = findViewById(R.id.txtCodigo);
        txtNombre = findViewById(R.id.txtNombre);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtHorario = findViewById(R.id.txtHorario);

       txtLatitude= findViewById(R.id.txtLatitude);
       txtLongitude= findViewById(R.id.txtLongitude);





        txtResultado = findViewById(R.id.txtResultado);

        btnInsertar = findViewById(R.id.btnInsertar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnConsultar = findViewById(R.id.btnConsultar);



        /* Abrimos la base de datos 'DBUsuarios' en modo escritura
         */


        UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 6);

        db = usdbh.getWritableDatabase();


        btnInsertar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Recuperamos los valores de los campos de texto
                String cod = txtCodigo.getText().toString();
                String nom = txtNombre.getText().toString();
                String direccion = txtDireccion.getText().toString();
                String horario = txtHorario.getText().toString();

                String latitud = txtLatitude.getText().toString();
                String longitud = txtLongitude.getText().toString();


/*
                //Alternativa 1: método sqlExec()
                String sql = "INSERT INTO Usuarios (codigo,nombre) VALUES ('" + cod + "','" + nom + "','"+direccion +"') ";
                db.execSQL(sql);*/

                //Alternativa 2: método insert()
                ContentValues nuevoRegistro = new ContentValues();
                nuevoRegistro.put("codigo", cod);
                nuevoRegistro.put("nombre", nom);
                nuevoRegistro.put("direccion", direccion);
                nuevoRegistro.put("horario", horario);
                nuevoRegistro.put("longitud", longitud);
                nuevoRegistro.put("latitud", latitud);
                db.insert("Usuarios", null, nuevoRegistro);
            }
        });


        btnActualizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Recuperamos los valores de los campos de texto
                String cod = txtCodigo.getText().toString();
                String nom = txtNombre.getText().toString();


                //Alternativa 1: método sqlExec()
                //String sql = "UPDATE Usuarios SET nombre='" + nom + "' WHERE codigo=" + cod;
                //db.execSQL(sql);

                //Alternativa 2: método update()
                ContentValues valores = new ContentValues();
                valores.put("nombre", nom);
                db.update("Usuarios", valores, "codigo=" + cod , null);
            }
        });


        btnEliminar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Recuperamos los valores de los campos de texto
                String cod = txtCodigo.getText().toString();

                //Alternativa 1: método sqlExec()
                //String sql = "DELETE FROM Usuarios WHERE codigo=" + cod;
                //db.execSQL(sql);

                //Alternativa 2: método delete()
                db.delete("Usuarios", "codigo=" + cod, null);
            }
        });


   /*     btnConsultar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Alternativa 1: método rawQuery()
                Cursor c = db.rawQuery("SELECT * FROM Usuarios", null);

                //Alternativa 2: método delete()
                //String[] campos = new String[] {"codigo", "nombre"};
                //Cursor c = db.query("Usuarios", campos, null, null, null, null, null);

                //Recorremos los resultados para mostrarlos en pantalla
                txtResultado.setText("");
                if (c.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {
                        String cod = c.getString(0);
                        String nom = c.getString(1);
                        String direc = c.getString(2);
                        String horario = c.getString(3);
                        String longitud= c.getString(4);
                        String latitud= c.getString(4);

                        txtResultado.append(" " + cod + " - " + nom + " - " + direc+"-" +horario+"\n"+
                                "Longitud"+longitud+"\n"+
                                "Latitud "+latitud+"\n");
                    } while (c.moveToNext());
                }
            }
        });

*/


        btnConsultar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent intent = new Intent(v.getContext(), VerClientesLista.class);
                startActivityForResult(intent, 0);


            }

        });




















    }}
