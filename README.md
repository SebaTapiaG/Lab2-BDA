# Laboratorio 2 - Taller de bases de datos
## Este laboratorio fue desarrollado por el grupo N°6.
### Integrantes de grupo 6:
* Mohamed Al-Marzuk 
* Vicente Arce
* César Rodríguez
* Sebastián Tapia
* Benjamin Zúñiga

# Funcionalidades
* Trigger_16_lab2.sql: Corresponde a la funcionalidad número 16 requerida, la cúal se implemento a través de un trigger.
* Trigger_17.sql: Corresponde a la funcionalidad número 17 requerida.
* Funcionalidad número 22: Fue implementada para el cliente, calcula el punto medio.


# Herramientas de desarrollo

## Base de datos
* Postgres SQL versión 14
* PgAdmin versión 4
* PostGIS 3.5

## Backend
* Springboot 3.2.5
* Gradle
* IntelliJ IDEA Ultimate / Visual Studio Code
* JDK versión 21
* Postman

## Frontend
* Vue3
* Nuxt3
* Node.js 18^
* Visual Studio Code

# Guía de Instalación y Ejecución

## 1. Clonar el repositorio

Primero, clona el repositorio con:

git clone https://github.com/SebaTapiaG/Lab2-BDA

## 2. Instalación de IntelliJ IDEA / Visual Studio Code

1. Descarga el IDE elegido:
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
- [Visual Studio Code](https://code.visualstudio.com/)

2. Sigue las instrucciones de instalación de acuerdo con tu sistema operativo.

## 3. Instalación de PostgreSQL y pgAdmin

### 3.1 Instalación de PostgreSQL

1. Descarga PostgreSQL desde [aquí](https://www.postgresql.org/download/).

2. Sigue las instrucciones de instalación de acuerdo con tu sistema operativo.

3. Cuando se haya instalado PostgreSQL, instala PostGIS.

### 3.2 Instalación de pgAdmin

1. Descarga pgAdmin desde [aquí](https://www.pgadmin.org/download/).

2. Instálalo siguiendo las instrucciones para tu sistema operativo.

## 4. Configuración de la Base de Datos

### 4.1 Crear base de datos
1. Asegúrate de estar conectado a PostgreSQL con pgAdmin:
   Abre pgAdmin y conéctate a tu servidor PostgreSQL. Si aún no tienes una conexión configurada, puedes hacerlo siguiendo las instrucciones de pgAdmin.
   
2. Si aún no tienes una base de datos, crea una nueva haciendo clic derecho sobre "Databases" > "Create" > "Database".

3. Ejecuta el script SQL para crear las tablas de la base de datos:
   Haz clic con el botón derecho en la base de datos seleccionada y selecciona "Query Tool" para abrir una ventana donde podrás ejecutar consultas SQL.

4. Abre el archivo SQL con "File" > "Open" y selecciona el script necesario.

5. Ejecuta el script presionando F5 o con el botón execute.

### 4.2 Cargar la información

1. Abre el "Query Tool" en pgAdmin.

2. Abre y ejecuta los scripts para cargar datos (por ejemplo, dbCreate.sql y loadData.sql) de la misma manera que antes.

3. Asegúrate de que los datos se hayan cargado correctamente en la base de datos.

## 5. Ejecución del proyecto

### 5.1 Ejecución Backend

1. Abre la carpeta [backend](./backend) en tu IDE.

2. Asegúrate de que las dependencias estén instaladas.

3. Agrega las siguientes variables de entorno para la configuración de la base de datos: (DB_PORT=5432;DB_USERNAME=postgres;DB_PASSWORD=password)
   NNota: Si tu instalación de PostgreSQL tiene credenciales diferentes, cámbialas.

4. Inicia el servidor backend ejecutando la clase Application en tu IDE.

5. Accede a la API en [http://localhost:8080/](http://localhost:8080/).

### 5.2 Ejecución Frontend

1. Abre una terminal en la carpeta [front-end](./front-end).

2. Asegúrate de tener Node.js 18+ instalado. Si no, descárgalo desde [https://nodejs.org/](https://nodejs.org/).

3. Instala las dependencias del proyecto con:

   ```bash
   npm install
   ```
   
4. Inicia el frontend con:
   ```bash
   npm run dev
   ```

## 6. Accede a la aplicacion

1. Una vez que ambos servidores (backend y frontend) estén corriendo, abre tu navegador y accede a la URL del frontend en [http://localhost:5173/](http://localhost:8081/).

2. ¡Listo! Ahora puedes interactuar con la aplicación directamente desde el navegador.

# Triggers

A la hora de realizar los requerimientos funcionales de triggers, estos estan añadidos como archivos .sql con la terminación trigger_X, donde X es el entero que representa el requerimiento. 

Para poder trabajar con los triggers, se deben ejecutar los scripts de estos con nombre ya mencionados anteriormente, antes de la ejecución del proyecto.
