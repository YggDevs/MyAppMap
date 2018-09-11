package com.example.yeray.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
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
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
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


public class MainActivity extends AppCompatActivity {





             TextView mensaje1;
             TextView mensaje2;


    private Button btnConsultar;
    private  Button btn;
    private Button btnIrMapsActivity;
    private  Button btnMostrarClientes;
    private Button btnClientes;
    private Button btnVerClientes;

    private SQLiteDatabase db;      //Conector base de datos




    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        btn = findViewById(R.id.btn_irAMaps);
        btnConsultar = findViewById(R.id.btnConsultar);
        btnIrMapsActivity = findViewById(R.id.btnIrMapsActivity);
        btnMostrarClientes = findViewById(R.id.btnMostrarClientes);
        btnClientes = findViewById(R.id.btnVerClientes);
        btnVerClientes=findViewById( R.id.btnMostrarClientes );







        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.DONUT)
            @Override

            public void onClick(View v) {


             /*  //Otra forma de iniciar la navegacion pero sin paso de parametros

             Uri gmmIntentUri = Uri.parse("google.navigation:q=28.4630279,-16.3417534"); Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri); mapIntent.setPackage("com.google.android.apps.maps"); startActivity(mapIntent);*/


           /*  //array double
                double [][] coordenadas = {{28.4630279,-16.3417534},{27.32665,26.65465,9},{8,10,12}};

                for (int x=0; x < coordenadas.length; x++) {
                    for (int y=0; y < coordenadas[x].length; y++) {


                        System.out.println (coordenadas[x][y]);
                    }
                }
*/


/*
       Array con coordenadas recibe coordenadas y las mete en elmetodo visitarcleintes
       que es el que se encarga de mostrar las coordenadas en el mapa,hay un break para
       que solo recoga los dos primeros datos del array
           */

                double [] coordenadas = {28.4630279,-16.3417534,27.32665,-26.65465,30.54654,-50.35465,20.35464,-1066544,};


                for (int x=0; x < coordenadas.length; x++) {

                    if(x == 1){
                        break;
                    }
                    double latitude = coordenadas[0];
                    double longitude =coordenadas[1];

                    visitarclientes(latitude,longitude);

                }


/*



                Uri gmmIntentUri = Uri.parse("google.navigation:q=Alcampo");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

*/


            }
        });


        btnIrMapsActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent intent = new Intent(v.getContext(), MapsActivity.class);
                startActivityForResult(intent, 0);


            }

        });


        btnConsultar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent intent = new Intent(v.getContext(), RutasActivity.class);
                startActivityForResult(intent, 0);


            }

        });





        btnClientes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent intent = new Intent(v.getContext(), VerClientesLista.class);
                startActivityForResult(intent, 0);


            }

        });




        btnVerClientes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent intent = new Intent(v.getContext(), VerClientesActivity.class);
                startActivityForResult(intent, 0);


            }

        });







/*
  TextView que recoge informacion de GPS
  */



        mensaje1 = (TextView) findViewById(

                R.id.mensaje_id);
        mensaje2 = (TextView) findViewById(R.id.mensaje_id2);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();

        }
    }



    public void visitarclientes(double lat,double lon){


        String uri = String.format(Locale.ENGLISH, "google.navigation:q=%1$f,%2$f", lat, lon);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);


    }








   /* crear menu*/



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;}



   /*     Metodos para obtener las coordenadas GPS*/

    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);


        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
        mensaje1.setText("Obteniendo datos GPS");
        mensaje2.setText("");
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }


    @SuppressLint("SetTextI18n")
    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());

            List<Address> list = geocoder.getFromLocation(
                    loc.getLatitude(), loc.getLongitude(), 1);

            if (!list.isEmpty()) {
                Address DirCalle = list.get(0);
                mensaje2.setText("Mi direccion es: \n" + DirCalle.getAddressLine(0));




     /* Se define una localizacion Y un margen de error( MARGENERROR )
        para luego comprara saber cuando el usuario ha llegado a el lugar indicado
      */

                String Atecresa_latitude = "28.4557421";
                String Atecresa_longitude = "-16.2996893";

                Double latitude = Double.valueOf(Atecresa_latitude);
                Double longitude = Double.valueOf(Atecresa_longitude);


                double  MARGENERROR = 50.00;


                Location markerLocation = new Location("");
                markerLocation.setLatitude(latitude);
                markerLocation.setLongitude(longitude);

                Location myLocation = new Location("");
                myLocation.setLatitude(DirCalle.getLatitude());
                myLocation.setLongitude(DirCalle.getLongitude());

                //calcular las distancias entre puntos
                float distance = myLocation.distanceTo(markerLocation);



                if (distance <=  MARGENERROR)

                {
                    Toast.makeText(getApplicationContext(), "Estas en casa  distancia: " + distance, Toast.LENGTH_SHORT).show();

                }





            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /* Aqui empieza la Clase Localizacion */



    public class Localizacion implements LocationListener {
        MainActivity mainActivity;

        public MainActivity getMainActivity() {
            return mainActivity;
        }


        public void setMainActivity(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }


        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion
            loc.getLatitude();
            loc.getLongitude();

            String Text = "Mi ubicacion actual es: " + "\n Lat = "
                    + loc.getLatitude() + "\n Long = " + loc.getLongitude();

            mensaje1.setText(Text);

            this.mainActivity.setLocation(loc);


        }


        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            mensaje1.setText("GPS Desactivado");
        }


        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            mensaje1.setText("GPS Activado");
        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }



    }








