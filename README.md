# MyAppMap
Descripción de la Aplicación 



La aplicación ha sido desarrollada en Java y Kotling utilizando

    Android Studio: entorno de desarrollo integrado utilizado 
    SQLite: motor de base de datos utilizado para almacenar la información de los clientes 
    Google Maps API: interfaz de programación de aplicaciones utilizada para mostrar la ubicación 

La aplicación consta de dos pantallas principales: 

    Pantalla principal: en esta pantalla se muestra una lista de los clientes almacenados en la base de datos SQLite. 
    Desde aquí se puede acceder a la ubicación de cada cliente en Google Maps.
    Pantalla de agregar cliente: en esta pantalla se puede agregar un nuevo cliente a la base de datos SQLite.
    
Las pantallas o actividades son las siguientes :

    MainActivity: pantalla principal que muestra las opciones para agregar un nuevo cliente o ver la lista de clientes.
    NuevoClienteActivity: pantalla para agregar un nuevo cliente a la base de datos.
    VerClientesActivity: pantalla para ver la lista de clientes de la base de datos.
    VerClientesListaActivity: pantalla para ver la lista de clientes de la base de datos en formato de lista y con la opción de navegar a las ubicaciones de los clientes en Google Maps.

Información Técnica

    Lenguaje de programación: Kotlin y Java
    Patrón de arquitectura: Modelo Vista Controlador (MVC)
    Base de datos: SQLite
    Gestor de base de datos: UsuariosSQLiteHelper (extiende de SQLiteOpenHelper)
    Dependencias externas: ninguna
    SDK mínimo requerido: Android 5.0 (API 21)
    SDK recomendado: Android 11 (API 30)

