# Shoes Shop Microservicios

Este proyecto corresponde a una tienda de zapatillas trabajada con microservicios en Spring Boot. La idea principal fue separar las partes del sistema en servicios distintos y hacer que se comuniquen entre ellos usando Eureka, Gateway y WebClient.

En este caso se trabajaron principalmente los microservicios de **Zapatillas** y **Boletas 2.0**, además del servidor **Eureka** y el **Gateway**.

---

## Integrantes y responsabilidades

| Integrante               | Trabajo realizado                                                                                |
| ------------------------ | ------------------------------------------------------------------------------------------------ |
| Freddy Rios              | Microservicio Zapatillas, Eureka, Gateway y conexión entre Zapatillas y Boletas usando WebClient |
| [Nombre de tu compañero] | Microservicio Boletas 2.0                                                                        |

---

## Servicios del proyecto

El proyecto tiene estos servicios:

| Servicio    | Puerto | Para qué sirve                                       |
| ----------- | -----: | ---------------------------------------------------- |
| Eureka      |   8761 | Registra los microservicios                          |
| Gateway     |   8080 | Entrada principal para probar los servicios          |
| Zapatillas  |   8081 | Gestiona las zapatillas                              |
| Boletas 2.0 |   8082 | Gestiona boletas, métodos de pago y métodos de envío |

La comunicación quedó así:

```text
Cliente → Gateway → Zapatillas → WebClient → Boletas
```

---

## Tecnologías usadas

* Java 21
* Spring Boot
* Spring Data JPA
* Spring Cloud Eureka
* Spring Cloud Gateway
* WebClient
* MySQL
* Flyway
* Lombok
* Swagger
* Git y GitHub

---

## Bases de datos

Para el proyecto se usaron bases de datos en MySQL.

### Zapatillas

Base de datos:

```text
shoes_shop
```

En este microservicio se manejan datos como zapatillas y marcas.

### Boletas

Base de datos:

```text
shoes_shop_boletas_dev
```

En este microservicio se manejan las boletas, métodos de pago, métodos de envío y la relación con zapatillas.

---

## Comunicación entre Zapatillas y Boletas

Una parte importante del proyecto fue conectar el microservicio de **Zapatillas** con el microservicio de **Boletas**.

La idea fue que Zapatillas no consulte directamente la base de datos de Boletas, sino que le pida la información al microservicio `BOLETAS`, que está registrado en Eureka.

Para eso se usó `WebClient`.

En Zapatillas se llama a este endpoint de Boletas:

```text
/api/v1/boletas/buscar-por-zapatilla/{id}
```

Ejemplo de la llamada interna:

```java
.uri("http://BOLETAS/api/v1/boletas/buscar-por-zapatilla/" + id)
```

Esto es parecido al ejemplo visto en clases, donde un servicio consulta información de otro servicio usando el id relacionado.

En nuestro caso:

```text
Zapatillas busca una boleta por el id de la zapatilla.
```

La información de Boletas llega a Zapatillas usando un DTO llamado:

```text
BoletaExternaDTO
```

Y después esa información queda dentro del:

```text
ZapatillaDTO
```

Ejemplo de respuesta:

```json
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
```

---

## Orden para ejecutar el proyecto

Antes de levantar los servicios hay que tener MySQL o Laragon encendido.

Se recomienda iniciar los servicios en este orden:

### 1. Eureka

```powershell
cd eureka
.\mvnw.cmd spring-boot:run
```

Después se puede revisar en:

```text
http://localhost:8761
```

Ahí deberían aparecer los servicios registrados.

---

### 2. Boletas 2.0

```powershell
cd boletas2.0
.\mvnw.cmd spring-boot:run
```

Endpoint de prueba:

```text
http://localhost:8082/api/v1/boletas/buscar-por-zapatilla/1
```

---

### 3. Zapatillas

```powershell
cd zapatillas
.\mvnw.cmd spring-boot:run
```

Endpoint de prueba:

```text
http://localhost:8081/api/v1/zapatillas
```

---

### 4. Gateway

```powershell
cd gateway
.\mvnw.cmd spring-boot:run
```

Endpoint de prueba por Gateway:

```text
http://localhost:8080/api/v1/zapatillas
```

---

## Pruebas realizadas

### Probar Eureka

Abrir en el navegador:

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

### Probar Boletas directo

```powershell
curl.exe -i "http://localhost:8082/api/v1/boletas/buscar-por-zapatilla/1"
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

### Probar Zapatillas directo

```powershell
curl.exe -i "http://localhost:8081/api/v1/zapatillas"
```

Esta prueba muestra las zapatillas y dentro de cada zapatilla aparece la boleta relacionada.

---

### Probar por Gateway

```powershell
curl.exe -i "http://localhost:8080/api/v1/zapatillas"
```

Esta es la prueba más importante, porque confirma que funciona:

```text
Gateway → Zapatillas → WebClient → Eureka → Boletas
```

---

## Endpoints principales

### Zapatillas

| Método | Endpoint                  | Descripción                |
| ------ | ------------------------- | -------------------------- |
| GET    | `/api/v1/zapatillas`      | Lista las zapatillas       |
| GET    | `/api/v1/zapatillas/{id}` | Busca una zapatilla por id |
| POST   | `/api/v1/zapatillas`      | Agrega una zapatilla       |
| PATCH  | `/api/v1/zapatillas/{id}` | Edita una zapatilla        |
| DELETE | `/api/v1/zapatillas/{id}` | Elimina una zapatilla      |

---

### Boletas

| Método | Endpoint                                    | Descripción                                  |
| ------ | ------------------------------------------- | -------------------------------------------- |
| GET    | `/api/v1/boletas`                           | Lista las boletas                            |
| GET    | `/api/v1/boletas/{id}`                      | Busca una boleta por id                      |
| GET    | `/api/v1/boletas/buscar-por-zapatilla/{id}` | Busca una boleta relacionada a una zapatilla |
| POST   | `/api/v1/boletas`                           | Agrega una boleta                            |
| PATCH  | `/api/v1/boletas/{id}`                      | Edita una boleta                             |
| PUT    | `/api/v1/boletas/{id}`                      | Actualiza una boleta                         |
| DELETE | `/api/v1/boletas/{id}`                      | Elimina una boleta                           |

---

### Métodos de pago y envío

| Método | Endpoint               | Descripción             |
| ------ | ---------------------- | ----------------------- |
| GET    | `/api/v2/metodo-pago`  | Lista métodos de pago   |
| GET    | `/api/v2/metodo-envio` | Lista métodos de envío  |
| GET    | `/api/v2/boletas`      | Lista boletas versión 2 |

---

## Swagger

También se puede revisar la documentación de los endpoints con Swagger.

Zapatillas:

```text
http://localhost:8081/doc/swagger-ui/index.html
```

Boletas:

```text
http://localhost:8082/swagger-ui/index.html
```

---

## Nota sobre las URLs

Si se entra solo a:

```text
http://localhost:8080
http://localhost:8081
http://localhost:8082
```

puede aparecer una página de error 404, porque esos servicios no tienen una página de inicio. Para probarlos se deben usar los endpoints `/api/v1/...` o `/api/v2/...`.

---

## Estado final

El proyecto queda con:

* Eureka funcionando.
* Gateway funcionando.
* Microservicio Zapatillas funcionando.
* Microservicio Boletas 2.0 funcionando.
* Comunicación entre Zapatillas y Boletas usando WebClient.
* Búsqueda de boleta por id de zapatilla.
* Respuesta de Zapatillas incluyendo datos de Boletas.
* Proyecto subido a GitHub.

---

## Repositorio

```text
https://github.com/freddyrios23/shoes-shop-micro-servicios.git
```
