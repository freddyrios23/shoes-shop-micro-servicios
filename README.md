# Shoes Shop Microservicios

Este proyecto es una tienda de zapatillas hecha con microservicios en Spring Boot.

La idea fue separar el sistema en varios servicios y hacer que se comuniquen entre ellos usando Eureka, Gateway y WebClient.

## Integrantes

| Integrante               | Parte realizada                                    |
| ------------------------ | -------------------------------------------------- |
| Freddy Rios              | Zapatillas, Eureka, Gateway y conexión con Boletas |
| [Nombre de tu compañero] | Boletas 2.0                                        |

## Microservicios

El proyecto tiene estos microservicios:

| Servicio    | Puerto |
| ----------- | ------ |
| Eureka      | 8761   |
| Gateway     | 8080   |
| Zapatillas  | 8081   |
| Boletas 2.0 | 8082   |

## Tecnologías usadas

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

## Cómo se ejecuta

Primero hay que tener MySQL o Laragon encendido.

Después se levantan los servicios en este orden:

### 1. Eureka

```powershell
cd eureka
.\mvnw.cmd spring-boot:run
```

Se puede revisar en:

```text
http://localhost:8761
```

Ahí deberían aparecer los servicios registrados.

### 2. Boletas 2.0

```powershell
cd boletas2.0
.\mvnw.cmd spring-boot:run
```

### 3. Zapatillas

```powershell
cd zapatillas
.\mvnw.cmd spring-boot:run
```

### 4. Gateway

```powershell
cd gateway
.\mvnw.cmd spring-boot:run
```

## Pruebas

Para probar Boletas directo:

```powershell
curl.exe -i "http://localhost:8082/api/v1/boletas/buscar-por-zapatilla/1"
```

Para probar Zapatillas directo:

```powershell
curl.exe -i "http://localhost:8081/api/v1/zapatillas"
```

Para probar por Gateway:

```powershell
curl.exe -i "http://localhost:8080/api/v1/zapatillas"
```

La prueba por Gateway es la más importante, porque muestra que funciona esta comunicación:

```text
Gateway → Zapatillas → WebClient → Boletas
```

## Comunicación entre Zapatillas y Boletas

El microservicio de Zapatillas se conecta con Boletas usando WebClient.

En vez de consultar directamente la base de datos de Boletas, Zapatillas llama al microservicio BOLETAS registrado en Eureka.

La URL que se usa para buscar la boleta relacionada es:

```text
/api/v1/boletas/buscar-por-zapatilla/{id}
```

En el código se llama así:

```java
.uri("http://BOLETAS/api/v1/boletas/buscar-por-zapatilla/" + id)
```

Con esto, cuando se consulta una zapatilla, también aparece la boleta asociada.

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

## Endpoints principales

### Zapatillas

| Método | Endpoint                  |
| ------ | ------------------------- |
| GET    | `/api/v1/zapatillas`      |
| GET    | `/api/v1/zapatillas/{id}` |
| POST   | `/api/v1/zapatillas`      |
| PATCH  | `/api/v1/zapatillas/{id}` |
| DELETE | `/api/v1/zapatillas/{id}` |

### Boletas

| Método | Endpoint                                    |
| ------ | ------------------------------------------- |
| GET    | `/api/v1/boletas`                           |
| GET    | `/api/v1/boletas/{id}`                      |
| GET    | `/api/v1/boletas/buscar-por-zapatilla/{id}` |
| POST   | `/api/v1/boletas`                           |
| PATCH  | `/api/v1/boletas/{id}`                      |
| PUT    | `/api/v1/boletas/{id}`                      |
| DELETE | `/api/v1/boletas/{id}`                      |

## Importante

Si se entra solo a estos puertos:

```text
http://localhost:8080
http://localhost:8081
http://localhost:8082
```

puede salir error 404, porque no tienen página de inicio.

Para probar hay que usar los endpoints `/api/v1/...`.

## Repositorio

```text
https://github.com/freddyrios23/shoes-shop-micro-servicios.git
```
