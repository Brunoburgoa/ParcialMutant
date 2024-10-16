# ParcialMutant

Este proyecto es una API para la detección de mutantes mediante el análisis de secuencias de ADN. 

1. Descripción General
La API permite enviar secuencias de ADN en formato JSON y determinar si corresponden a un mutante.
Si la secuencia contiene dos o más secuencias consecutivas de cuatro letras iguales en cualquier dirección (horizontal, vertical o diagonal), será identificada como mutante.

Endpoints que se crearon:
POST /mutant: Verifica si una secuencia de ADN corresponde a un mutante.
GET /stats: Devuelve las estadísticas de las verificaciones de ADN realizadas.

# 2. Acceso a la API

La API está desplegada en Render y se puede acceder mediante los siguientes endpoints:

Base URL
https://parcialmutant-1rc1.onrender.com/

# 2.a Acceso a la base de datos

La base de datos se encuentra en render con el siguiente URL:

https://parcialmutant-1rc1.onrender.com/h2-console

Los datos para ingresar son:

Setting name: Generic H2 (Embedded)

Driver class: org.h2.Driver

JDBC URL: jdbc:h2:file:C:\\Users\\bruno\\OneDrive\\Escritorio\\UTN\\Tercer%Semestre\\Programacion%3\\Primer%Parcial\\ParcialMutantes\\Data\\mutantdb

Username: sa

password:

# POST /mutant
Verifica si una secuencia de ADN corresponde a un mutante. Para hacer dicho POST debemos utilizar POSTMAN.

Link a ingresar en dicha aplicacion: https://parcialmutant-1rc1.onrender.com/mutant

{
  "dna": [
    "ATGCGA",
    "CAGGAC",
    "TTTTGT",
    "AGAACG",
    "CAGCTA",
    "TCACTG"
  ]
}

Respuesta exitosa (mutante):
HTTP Status: 200 OK

Respuesta:

{
  "message": "Es mutante"
}

Respuesta en caso de que no sea mutante:
HTTP Status: 403 Forbidden

Respuesta:

{
  "message": "No es mutante"
}

# GET /stats
Devuelve estadísticas de las verificaciones de ADN.

Link para el GET https://parcialmutant-1rc1.onrender.com/stats

Respuesta exitosa:
{
  "count_mutant_dna": 40,
  "count_human_dna": 100,
  "ratio": 0.4
}

# 3. Estructura del Proyecto y Manejo de Datos
Tecnologías Utilizadas

Lenguaje: Java

Framework: Spring Boot

Base de Datos: H2

Testing: JUnit 5, Mockito

Control de Versiones: Git

IDE: Visual Studio Code (para diagramas), IntelliJ IDEA (recomendado para desarrollo)

Acontinuacion, la explicacion del proyecto

# a. Recepción y Validación de Datos (Controlador y DTO)
Cuando un cliente realiza una solicitud POST al endpoint /mutant con una secuencia de ADN, la secuencia se envía en formato JSON. La clase DnaController es la encargada de recibir esta secuencia.

La secuencia de ADN se mapea a la clase DTO DnaDto, que encapsula el arreglo de cadenas (secuencia de ADN) en un formato manejable por la API. El objeto DnaDto actúa como un contenedor para los datos que son enviados a la API.

Ejemplo de secuencia recibida:

{
  "dna": [
    "ATGCGA",
    "CAGTGC",
    "TTATGT",
    "AGAAGG",
    "CCCCTA",
    "TCACTG"
  ]
}

# b. Validaciones de la Secuencia de ADN
La validación de la secuencia se realiza en la clase DnaValidator. Este componente lleva a cabo las siguientes operaciones:

Validación del Tamaño de la Matriz (NxN): La secuencia de ADN debe formar una matriz de tamaño NxN. Esto se verifica antes de cualquier otra operación. Si la matriz no cumple con esta condición, se devuelve un error con el mensaje "El DNA no es una matriz NxN."

Validación de los Caracteres Permitidos: Se verifica que cada cadena en la matriz solo contenga los caracteres A, C, G y T. Si se encuentran caracteres inválidos, se devuelve un error con el mensaje "El DNA contiene letras no válidas."

Verificación de Existencia del ADN: Antes de procesar la secuencia, se verifica si el ADN ya ha sido ingresado anteriormente en la base de datos. Si existe, se devuelve un error con el mensaje "El DNA ya ha sido ingresado anteriormente."

# c. Lógica de Verificación de Mutantes (Método isMutant)
La clase DnaService implementa la lógica principal para determinar si una secuencia corresponde a un mutante. Las reglas son las siguientes:

Criterio de Mutante: Se considera mutante a un ADN que contiene dos o más secuencias consecutivas de cuatro letras iguales en cualquier dirección (horizontal, vertical o diagonal).
El método isMutant() realiza las siguientes operaciones:

Transformación de la Secuencia: Convierte el arreglo de cadenas a una matriz bidimensional de caracteres para facilitar la verificación en todas las direcciones.

Verificación de Secuencias:

Horizontal: Recorre cada fila de la matriz.
Vertical: Recorre cada columna de la matriz.
Diagonal Principal: Verifica las diagonales de la esquina superior izquierda a la esquina inferior derecha.
Diagonal Secundaria: Verifica las diagonales de la esquina superior derecha a la esquina inferior izquierda.
Conteo de Secuencias: Utiliza el método countSequences de DnaValidator para contar las secuencias encontradas. Si el conteo total es mayor a 1, se determina que el ADN es mutante.

Persistencia del Resultado: Guarda la secuencia de ADN y el resultado (isMutant) en la base de datos.

# d. Persistencia de Datos (Base de Datos H2)
Una vez validada la secuencia de ADN y determinado si es mutante o no, se almacena en la base de datos H2. La clase Dna representa la entidad que se guarda en la base de datos y contiene:

ID: Un identificador único autogenerado.
DNA_SEQUENCE: La secuencia de ADN, almacenada como un arreglo de cadenas (String[]).
IS_MUTANT: Un booleano que indica si la secuencia pertenece a un mutante (true) o no (false).
La persistencia se maneja a través de Spring Data JPA, y el repositorio DnaRepository se utiliza para insertar y consultar los registros en la base de datos.

Ejemplo de tabla en la base de datos:

| ID  | DNA_SEQUENCE                             | IS_MUTANT|
| --- | ---------------------------------------- | -------- |
| 1   | ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG| true     |
| 2   | AATCGG,CGTCAA,TATGCT,GTCAAA,CGTCCA,TGTGAA| false    |

# e. Estadísticas del ADN (/stats)
El endpoint /stats devuelve las estadísticas acumuladas de las verificaciones de ADN:

count_mutant_dna: Número total de secuencias de ADN que son mutantes.
count_human_dna: Número total de secuencias de ADN que no son mutantes (humanas).
ratio: Relación entre mutantes y humanos, calculada como count_mutant_dna / count_human_dna.
La clase de servicio DnaService utiliza el repositorio DnaRepository para contar cuántos mutantes y cuántos humanos hay en la base de datos, y luego calcula el ratio.

Ejemplo de respuesta para /stats:

{
  "countMutantDna": 40,
  "countHumanDna": 100,
  "ratio": 0.4
}

# 4. Ejecución de Pruebas

Las pruebas se ejecutan localmente. Para clonar el repositorio y ejecutar los tests de manera local, sigue estos pasos:

Abre el terminal de IntelliJ, Navega al directorio donde quieras clonar el proyecto con el siguiente comando:

git clone https://github.com/Brunoburgoa/ParcialMutant.git

Esto creará una carpeta llamada ParcialMutant dentro del directorio donde te encuentres.

Resumen:

Para descargar el proyecto completo completo debemos buscar la carpeta donde queremos clonar el repositorio y hacer lo siguiente:

cd "Link de la carpeta"
git clone https://github.com/Brunoburgoa/ParcialMutant.git

Esto descargará el proyecto completo en la carpeta previamente designada con el nombre ParcialMutant.

Luego de clonar, puedes moverte al directorio del proyecto con:

cd ParcialMutant
Desde ahí podrás ejecutar los comandos de Gradle, abrir el proyecto en el IDE, o realizar otras acciones.

Para ejecutar los tests debemos escribir el siguiente comando en la terminal:

./gradlew test

Y asi obtendremos una URL donde podremos ver los resultados:
build/reports/tests/test/index.html
