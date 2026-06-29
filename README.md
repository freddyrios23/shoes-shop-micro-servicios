# Shoes Shop Microservicios

Este es un proyecto de una tienda de zapatillas hecho con microservicios en Spring Boot.

La idea del proyecto fue separar algunas partes del sistema en servicios distintos. En mi caso trabajé con el microservicio de Zapatillas, Eureka, Gateway y la conexión con el microservicio de Boletas.

También se usó MySQL para guardar los datos y GitHub para subir el proyecto.

## Integrantes

Freddy Rios: trabajó en Zapatillas, Eureka, Gateway y la conexión entre Zapatillas y Boletas usando WebClient.

Nicolas Castro: trabajó en Boletas 2.0.

## Microservicios usados

El proyecto tiene estos servicios:

* Eureka: puerto 8761
* Gateway: puerto 8080
* Zapatillas: puerto 8081
* Boletas 2.0: puerto 8082

Eureka sirve para registrar los microservicios.

Gateway sirve como entrada para probar los servicios.

Zapatillas maneja la información de las zapatillas.

Boletas maneja las boletas, métodos de pago, métodos de envío y la relación con zapatillas.

## Tecnologías usadas

En el proyecto se usó:

* Java 21
* Spring Boot
* Spring Data JPA
* MySQL
* Flyway
* Eureka
* Gateway
* WebClient
* Lombok
* Swagger
* GitHub

## Bases de datos

Para Zapatillas se usó la base de datos:

shoes_shop

Para Boletas se usó la base de datos:

shoes_shop_boletas_dev

## Cómo se ejecuta el proyecto

Primero hay que tener MySQL o Laragon encendido.

Después se deben iniciar los servicios en este orden.

- Primero se inicia Eureka
Luego se puede revisar en el navegador:

http://localhost:8761

- Después se inicia Boletas

- Luego se inicia Zapatillas:

- Finalmente se inicia Gateway:

## Comunicación entre Zapatillas y Boletas

Una parte importante del proyecto fue hacer que Zapatillas pudiera obtener información de Boletas.

Para eso se usó WebClient.

Zapatillas no consulta directamente la base de datos de Boletas. Lo que hace es llamar al microservicio BOLETAS, que está registrado en Eureka.

El endpoint que se creó en Boletas fue este:

/api/v1/boletas/buscar-por-zapatilla/{id}

En Zapatillas se llama de esta forma:

.uri("http://BOLETAS/api/v1/boletas/buscar-por-zapatilla/" + id)

Con eso, cuando se consulta una zapatilla, también se puede mostrar la boleta relacionada.

La comunicación queda así:

Gateway -> Zapatillas -> WebClient -> Boletas

## Pruebas realizadas

Para probar Boletas directamente:

curl.exe -i "http://localhost:8082/api/v1/boletas/buscar-por-zapatilla/1"

Para probar Zapatillas directamente:

curl.exe -i "http://localhost:8081/api/v1/zapatillas"


Para probar desde Gateway:

curl.exe -i "http://localhost:8080/api/v1/zapatillas"


La prueba por Gateway es la más importante, porque confirma que la comunicación entre los servicios está funcionando.

## Ejemplo de respuesta

Al consultar zapatillas, la respuesta muestra la zapatilla junto con la boleta relacionada:


[
  {
    "boleta": {
      "cantidad": 1,
      "fecha": "2026-06-28",
      "id": 1,
      "total": 89990,
      "zapatillasId": [1]
    },
    "id": 1,
    "marcaId": 1,
    "nombre": "Nike Air Max",
    "nombreMarca": "Nike",
    "precio": 89990
  }
]


## Endpoints principales

En Zapatillas se pueden probar endpoints como:


GET /api/v1/zapatillas
GET /api/v1/zapatillas/{id}
POST /api/v1/zapatillas
PATCH /api/v1/zapatillas/{id}
DELETE /api/v1/zapatillas/{id}

En Boletas se pueden probar endpoints como:


GET /api/v1/boletas
GET /api/v1/boletas/{id}
GET /api/v1/boletas/buscar-por-zapatilla/{id}
POST /api/v1/boletas
PATCH /api/v1/boletas/{id}
PUT /api/v1/boletas/{id}
DELETE /api/v1/boletas/{id}


## Importante

Si se entra solo a estos enlaces:


http://localhost:8080
http://localhost:8081
http://localhost:8082


puede aparecer error 404. Eso no significa que el proyecto esté malo, solo que esos servicios no tienen una página de inicio.

Para probar hay que usar los endpoints que empiezan con: /api/v1/...


## Estado final

El proyecto queda funcionando con Eureka, Gateway, Zapatillas y Boletas.

También queda funcionando la comunicación entre Zapatillas y Boletas usando WebClient, buscando una boleta por el id de una zapatilla.

## Repositorio


https://github.com/freddyrios23/shoes-shop-micro-servicios.git

