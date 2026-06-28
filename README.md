# Shoes Shop Microservicios

Proyecto desarrollado para la asignatura **FullStack 1**, basado en una tienda de zapatillas implementada con arquitectura de microservicios usando **Spring Boot**, **Eureka**, **Gateway**, **WebClient**, **MySQL** y **Flyway**.

El sistema permite gestionar zapatillas y boletas en microservicios separados. Además, se implementó comunicación entre microservicios para que el servicio de **Zapatillas** pueda consultar información del servicio de **Boletas** mediante WebClient y Eureka.

---

## Integrantes y responsabilidades

| Integrante             | Microservicios / responsabilidades                                                                  |
| ---------------------- | --------------------------------------------------------------------------------------------------- |
| Freddy Rios            | Microservicio Zapatillas, Eureka, Gateway, conexión entre Zapatillas y Boletas con WebClient |
| Nicolas Castro | Microservicio Boletas 2.0                                                                           |

---

## Arquitectura del proyecto

El proyecto está dividido en los siguientes servicios:

| Servicio      | Puerto | Descripción                                                                      |
| ------------- | -----: | -------------------------------------------------------------------------------- |
| Eureka Server |   8761 | Servidor de descubrimiento donde se registran los microservicios                 |
| Gateway       |   8080 | Punto de entrada principal para acceder a los microservicios                     |
| Zapatillas    |   8081 | Microservicio encargado de gestionar zapatillas                                  |
| Boletas 2.0   |   8082 | Microservicio encargado de gestionar boletas, métodos de pago y métodos de envío |

La comunicación principal queda de la siguiente forma:

```text
Cliente
  ↓
Gateway
  ↓
Zapatillas
  ↓
WebClient + Eureka
  ↓
Boletas
```

---

## Tecnologías utilizadas

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Cloud Netflix Eureka
* Spring Cloud Gateway
* WebClient
* MySQL
* Flyway
* Lombok
* Swagger / OpenAPI
* Git y GitHub

---

## Bases de datos

Cada microservicio trabaja con su propia configuración de base de datos.

### Zapatillas

Base de datos utilizada:

```text
shoes_shop
```

Tablas principales:

```text
zapatilla
marca
color
tipo
material
```

### Boletas

Base de datos utilizada:

```text
shoes_shop_boletas_dev
```

Tablas principales:

```text
metodoEnvio
metodoPago
boleta
boletas
```

El microservicio de Boletas utiliza Flyway para crear tablas e insertar datos iniciales de prueba.

---

## Comunicación entre microservicios

Se implementó comunicación entre **Zapatillas** y **Boletas** usando `WebClient`.

El microservicio de Zapatillas no accede directamente a la base de datos de Boletas. En su lugar, consulta al servicio `BOLETAS` registrado en Eureka.

Ejemplo de llamada interna desde Zapatillas:

```java
.uri("http://BOLETAS/api/v1/boletas/" + id)
```

La respuesta se recibe en un DTO externo llamado:

```text
BoletaExternaDTO
```

Luego se agrega dentro de:

```text
ZapatillaDTO
```

De esta forma, cuando se consulta una zapatilla, también se puede ver la boleta asociada.

Ejemplo de respuesta:

```json
[
  {
    "id": 1,
    "nombre": "Nike Air Max",
    "precio": 89990,
    "marcaId": 1,
    "nombreMarca": "Nike",
    "boleta": {
      "id": 1,
      "fecha": "2026-06-28",
      "cantidad": 1,
      "total": 89990,
      "zapatillasId": [1]
    }
  }
]
```

---

## Orden para ejecutar el proyecto

Antes de iniciar los servicios, se debe tener MySQL o Laragon encendido.

### 1. Eureka Server

```powershell
cd eureka
.\mvnw.cmd spring-boot:run
```

URL:

```text
http://localhost:8761
```

---

### 2. Boletas 2.0

```powershell
cd boletas2.0
.\mvnw.cmd spring-boot:run
```

URL base:

```text
http://localhost:8082
```

---

### 3. Zapatillas

```powershell
cd zapatillas
.\mvnw.cmd spring-boot:run
```

URL base:

```text
http://localhost:8081
```

---

### 4. Gateway

```powershell
cd gateway
.\mvnw.cmd spring-boot:run
```

URL base:

```text
http://localhost:8080
```

---

## Pruebas principales

### Verificar Eureka

Ingresar a:

```text
http://localhost:8761
```

Deben aparecer registrados:

```text
BOLETAS
ZAPATILLAS
GATEWAY
```

---

### Probar Boletas directamente

```powershell
curl.exe -i "http://localhost:8082/api/v1/boletas/1"
```

Respuesta esperada:

```json
{
  "cantidad": 1,
  "fecha": "2026-06-28",
  "id": 1,
  "total": 89990,
  "zapatillasId": [1]
}
```

---

### Probar Zapatillas directamente

```powershell
curl.exe -i "http://localhost:8081/api/v1/zapatillas"
```

Respuesta esperada: lista de zapatillas con la boleta incluida.

---

### Probar mediante Gateway

```powershell
curl.exe -i "http://localhost:8080/api/v1/zapatillas"
```

Esta prueba confirma la comunicación:

```text
Gateway → Zapatillas → WebClient → Eureka → Boletas
```

---

## Endpoints principales

### Zapatillas

| Método | Endpoint                  | Descripción                  |
| ------ | ------------------------- | ---------------------------- |
| GET    | `/api/v1/zapatillas`      | Lista todas las zapatillas   |
| GET    | `/api/v1/zapatillas/{id}` | Busca una zapatilla por id   |
| POST   | `/api/v1/zapatillas`      | Registra una nueva zapatilla |
| PATCH  | `/api/v1/zapatillas/{id}` | Edita una zapatilla          |
| DELETE | `/api/v1/zapatillas/{id}` | Elimina una zapatilla        |

---

### Boletas

| Método | Endpoint               | Descripción               |
| ------ | ---------------------- | ------------------------- |
| GET    | `/api/v1/boletas`      | Lista todas las boletas   |
| GET    | `/api/v1/boletas/{id}` | Busca una boleta por id   |
| POST   | `/api/v1/boletas`      | Registra una nueva boleta |
| PUT    | `/api/v1/boletas/{id}` | Actualiza una boleta      |
| PATCH  | `/api/v1/boletas/{id}` | Edita una boleta          |
| DELETE | `/api/v1/boletas/{id}` | Elimina una boleta        |

---

### Métodos de pago y envío

| Método | Endpoint               | Descripción             |
| ------ | ---------------------- | ----------------------- |
| GET    | `/api/v2/metodo-pago`  | Lista métodos de pago   |
| GET    | `/api/v2/metodo-envio` | Lista métodos de envío  |
| GET    | `/api/v2/boletas`      | Lista boletas versión 2 |

---

## Swagger

Los microservicios cuentan con documentación mediante Swagger/OpenAPI.

Ejemplos:

```text
http://localhost:8081/doc/swagger-ui/index.html
http://localhost:8082/swagger-ui/index.html
```

---

## Estado final del proyecto

El proyecto queda funcionando con:

* Registro de microservicios en Eureka.
* Gateway como entrada principal.
* Microservicio Zapatillas funcionando.
* Microservicio Boletas 2.0 funcionando.
* Comunicación entre Zapatillas y Boletas usando WebClient.
* DTO externo para recibir datos de otro microservicio.
* Datos iniciales para probar la conexión.
* Proyecto versionado y subido a GitHub.

---

## Repositorio

```text
https://github.com/freddyrios23/shoes-shop-micro-servicios.git
```
