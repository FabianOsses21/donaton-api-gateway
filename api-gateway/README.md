# api-gateway

API Gateway / BFF para el proyecto Donaton.

## Puerto

```txt
8080
```

## Microservicios que enruta

```txt
necesidades-service -> http://localhost:8083
donaciones-service  -> http://localhost:8082
logistica-service   -> http://localhost:8084
```

## Rutas desde API Gateway

### Necesidades

```http
http://localhost:8080/api/v1/necesidades
```

redirige a:

```http
http://localhost:8083/api/v1/necesidades
```

### Donaciones

```http
http://localhost:8080/api/v1/donaciones
```

redirige a:

```http
http://localhost:8082/api/v1/donaciones
```

### Logistica - Centros de acopio

```http
http://localhost:8080/api/v1/logistica/centros-acopio
```

redirige a:

```http
http://localhost:8084/api/v1/logistica/centros-acopio
```

### Logistica - Envios

```http
http://localhost:8080/api/v1/logistica/envios
```

redirige a:

```http
http://localhost:8084/api/v1/logistica/envios
```

## Ejecutar

Primero deben estar corriendo:

```txt
necesidades-service -> 8083
donaciones-service  -> 8082
logistica-service   -> 8084
```

Luego ejecutar:

```bash
mvn clean install -U
mvn spring-boot:run
```

## Pruebas rapidas

### GET necesidades por Gateway

```http
GET http://localhost:8080/api/v1/necesidades
```

### GET donaciones por Gateway

```http
GET http://localhost:8080/api/v1/donaciones
```

### POST centro de acopio por Gateway

```http
POST http://localhost:8080/api/v1/logistica/centros-acopio
```

Body:

```json
{
  "nombre": "Centro Acopio Santiago",
  "direccion": "Av. Principal 123",
  "comuna": "Santiago",
  "capacidadMaxima": 1000,
  "responsable": "Juan Perez",
  "telefono": "+56912345678"
}
```
