# Mendel - Java Code Challenge
Repositorio utilizado para el desarrollo del desafío de backend propuesto por Mendel.
Stack utilizado:
- Java 17
- Spring Boot
- JUnit & Mockito
- Maven
- Docker
## Endpoints
`PUT /transactions/{transaction_id}`

Crea o modifica una transacción con el id `transaction_id`. 
Ejemplo de uso:

    PUT /transactions/10
    { "amount": 5000.0, "type":"cars"}
    
    => {"status":"ok"}

    PUT /transactions/11
    {"amount":10000.0, "type":"shopping", "parentId":10}
    
    => {"status":"ok"}
    
    PUT /transactions/12
    {"amount":5000.0, "type":"shopping", "parentId":11}
    
    => {"status":"ok"}

En donde:
- `amount`: Valor de la transacción (obligatorio)
- `type`: Tipo de transacción (obligatorio)
- `parentId`: Id de transacción padre (opcional)
	- En caso de no existir una transacción con dicho ID, se procesará como si no tuviera transacción padre

---

`GET /transactions/types/{type}`

Trae todos los IDs de las transacciones de tipo `type` en una lista en formato JSON
Ejemplo de uso:

    GET /transactions/types/cars
    
    => [10]

---

`GET /transactions/sum/{transaction_id}`

Devuelve la suma total de la transacción indicada en `transaction_id` y sus hijas, de forma transitiva. 
Ejemplo de uso:

    GET /transactions/sum/10
    
    => [20000.0]


    GET /transactions/sum/11
    
    => [15000.0]
## Ejecución
El sistema tiene una imagen publicada en el registro público de docker bajo el nombre de `manudizen/mendel-challenge-app`.

Para utilizarla, el sistema debe contar con docker instalado en su maquina, y luego puede simplemente correr el comando:

    docker run -p 8080:8080 manudizen/mendel-challenge-app

Corriendo así de forma local el servicio. Las consultas se realizan a `http://localhost:8080/`. 
 

