package com.example.yeray.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


public class VerClientesActivity extends AppCompatActivity {

/* Mostrar datos mediante un listView de la bas de datos SQLite
* Esta clase tiene un layout activity_listview y
* un layout activity_ver_clientes
*
* El primero contiene:
*
*
* <?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <TextView
        android:id="@+id/textView"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:padding="20dp"

        />

</LinearLayout>
*
* Lo vamos a usar para mostrar los datos en el sugundo:
*
* <?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".VerClientesActivity">

    <ListView
        android:id="@+id/ListViewPersonas"
        android:layout_width="324dp"
        android:layout_height="448dp"
        android:layout_marginBottom="8dp"
        android:layout_marginEnd="8dp"
        android:layout_marginStart="8dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</android.support.constraint.ConstraintLayout>
*
*
*
* */


    ListView lv1;

    private SQLiteDatabase db;
    private static final String LOGTAG = "LogsAndroid";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_ver_clientes );


        lv1 = (ListView) findViewById( R.id.ListViewPersonas );
        int[] array=new int[1000];


      /*  Array list que va a almacenar
        los datos extraidos de la
        base de datos
       */
        final ArrayList<String> datos = new ArrayList<>();

        /*
        Abrimos la base de datos en modo
        escritura para poder leer de ella


        */

        UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper( this, "DBUsuarios", null, 6 );
        db = usdbh.getWritableDatabase();

         /*
        Se crea un cursor para recorrer la base de datos y cogemos los datos
        correspondientes
        */

        final Cursor fila = db.rawQuery( "SELECT * FROM Usuarios", null );
        if (fila.moveToFirst()) {
            do {
                datos.add(
                                 fila.getString( 0 ) + "\n " +
                                fila.getString( 1 ) + " - " +             //codigo
                                fila.getString( 2 ) + " \n " +            //Nombre
                                fila.getString( 3 ) + "\n Horario:  " + //Direccion
                                fila.getString( 4 ) + "\n  "+  "\n Latitud:        "+
                        fila.getString( 5 )+ "\n Longitud:     " +
                        fila.getString( 6 )
                );



            } while (fila.moveToNext());
        }



        db.close();

        /*
        Mostramos los datos
        */

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( this, R.layout.activity_listview, R.id.textView, datos );
        lv1.setAdapter( arrayAdapter );

         /*
        Metodo para saber que item se esta pulsando
        */
        lv1.setClickable( true );
        lv1.setOnItemClickListener( new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                //Object o = listView.getItemAtPosition(position);
                // Realiza lo que deseas, al recibir clic en el elemento de tu listView determinado por su posicion.

                Log.i( "Click", "click en el elemento " + position + " de mi ListView" );
                //Toast.makeText(getApplicationContext(), "Has pulsado  " +position, Toast.LENGTH_SHORT).show();


                int i = 0;
                while ( i < 100) {

                    if (position == i) {

                        recibeLatLong(i+1);

                        Log.i(LOGTAG, "Informacion recibeLAT/LOng"+i);
                    }
                    i++;
                }





            }

            }
        );





    }









    public void recibeLatLong(int cx) {

        /*Abrir la base de datos
        */

        UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper( this, "DBUsuarios", null, 6 );
        db = usdbh.getWritableDatabase();

/*
        consulta a la base de datos pasandole cx que es el id auntoincremental de la bd
                en base a eso se extrae la longitud y la latitud
        */

        Cursor c = db.rawQuery("SELECT latitud, longitud FROM Usuarios WHERE id="+cx, null);

            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {

                    String longitud= c.getString(0);
                    String latitud= c.getString(1);
                    double doblelong = Double.parseDouble(longitud);
                    double doblelat = Double.parseDouble(latitud);

                    String uri = String.format(Locale.ENGLISH, "google.navigation:q=%1$f,%2$f", doblelong,doblelat);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    startActivityForResult(intent,1);
                    //finishActivity(1); finaliza el activity  maps





                } while (c.moveToNext());
            }
        }




}





