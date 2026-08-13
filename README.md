# **Service Products**

Microservicio REST para la gestión de productos, categorías, unidades de medida, proveedores y las relaciones entre productos y proveedores.

El proyecto forma parte de una arquitectura de microservicios y está desarrollado utilizando Java 17 y Spring Boot 4.0.6, aplicando principios de Clean Architecture y Hexagonal Architecture para mantener desacoplada la lógica de negocio de los detalles de infraestructura.

## **Características**

Gestión de productos.
Gestión de categorías.
Gestión de unidades de medida.
Gestión de proveedores.
Gestión de relaciones entre productos y proveedores.
Validación de datos mediante Bean Validation.
Manejo centralizado de excepciones.
Autenticación y autorización mediante JWT.
OAuth2 Resource Server.
Control de acceso mediante authorities/scopes.
Documentación de la API mediante OpenAPI y Swagger UI.
Persistencia con PostgreSQL.
Pruebas unitarias.
Pruebas de integración.
PostgreSQL mediante Testcontainers para las pruebas de integración.
Cobertura de código mediante JaCoCo.
Ejecución mediante Docker y Docker Compose.


## **Arquitectura**

El proyecto combina principios de Clean Architecture y Hexagonal Architecture.

La estructura principal separa el dominio y los casos de uso de los mecanismos utilizados para exponer y persistir la información.

## **Estructura de capas**

Domain

Contiene los elementos propios del dominio:

Entidades de dominio.
Value Objects.
Validaciones.
Reglas relacionadas con el modelo de negocio.



Application

Contiene la lógica de aplicación:

DTOs.
Mappers.
Ports.
Use Cases.
Excepciones de aplicación.

Los casos de uso se encuentran separados por responsabilidad, incluyendo operaciones relacionadas con productos, categorías, proveedores, unidades de medida y relaciones producto-proveedor.



Infrastructure

Contiene los detalles tecnológicos:

Los adaptadores REST funcionan como entrada al sistema, mientras que los adaptadores de persistencia implementan la comunicación con PostgreSQL.



## **Tecnologías**


* Java 17
* Spring Boot 4.0.6
* Spring Web MVC
* Spring Data JPA
* Hibernate
* PostgreSQL 18
* Spring Security
* OAuth2 Resource Server
* JWT
* Spring Validation
* Springdoc OpenAPI
* Swagger UI
* JUnit
* Mockito
* Testcontainers
* Docker
* Docker Compose
* JaCoCo
* Lombok

Las dependencias y versiones principales están definidas en el pom.xml del servicio.



## **Seguridad**

La API utiliza Spring Security OAuth2 Resource Server para validar tokens JWT.

La configuración de seguridad utiliza authorities basadas en scopes, por ejemplo:

SCOPE_admin
SCOPE_user
SCOPE_category:write

Algunos endpoints utilizan @PreAuthorize para restringir operaciones según los permisos incluidos en el JWT.

Por ejemplo:

@PreAuthorize("hasAnyAuthority('SCOPE_admin', 'SCOPE_user')")

Las operaciones de categorías utilizan permisos específicos para escritura:

@PreAuthorize(
"hasAuthority('SCOPE_admin') or " +
"hasAnyAuthority('SCOPE_category:write', 'SCOPE_user')"
)

La configuración actual utiliza una clave secreta proporcionada mediante la variable de entorno SECRET_KEY.

Nota: este servicio funciona como Resource Server. La generación/autenticación de usuarios y tokens pertenece al servicio de autenticación dentro de la arquitectura de microservicios.




## **Swagger / OpenAPI**

El proyecto utiliza Springdoc OpenAPI para generar automáticamente la documentación de la API.

La dependencia utilizada es:

<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>3.1.0</version>
</dependency>


Nota: este servicio funciona como Resource Server. La generación/autenticación de usuarios y tokens pertenece al servicio de autenticación dentro de la arquitectura de microservicios.


Una vez iniciada la aplicación, Swagger UI estará disponible normalmente en:

http://localhost:8080/swagger-ui.html

La especificación OpenAPI puede consultarse en:

http://localhost:8080/v3/api-docs

Los contratos de los controladores utilizan anotaciones como @Operation, @ApiResponses, @Parameter y @Tag para documentar los endpoints.




## **Configuración**

La aplicación obtiene su configuración sensible mediante variables de entorno.

Las principales variables utilizadas son:

Variable	Descripción
SPRING_DATASOURCE_URL	URL de conexión a PostgreSQL
SPRING_DATASOURCE_USERNAME	Usuario de PostgreSQL
SPRING_DATASOURCE_PASSWORD	Contraseña de PostgreSQL
SPRING_JPA_HIBERNATE_DDL_AUTO	Estrategia de generación/validación del esquema
SECRET_KEY	Clave utilizada para la validación JWT

La configuración de application.yaml utiliza estas variables en lugar de almacenar directamente las credenciales.



## **Ejecución local**

Requisitos

Antes de ejecutar el proyecto necesitas:

Java 17
Maven 3.9+
PostgreSQL 18, o Docker
Docker Desktop si quieres utilizar Docker Compose

1. Clonar el repositorio
   git clone https://github.com/EBonilla13/Service-Products.git
   cd Service-Products
2. Configurar las variables de entorno

Antes de iniciar la aplicación configura las variables necesarias.

Ejemplo:

APP_PORT=8080

POSTGRES_URL=jdbc:postgresql://localhost:5432/product_service
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_DB=product_service

JPA_HIBERNATE_DDL_AUTO=update

SECRET_KEY=your-secret-key

No utilices una clave JWT real en el repositorio. Para desarrollo utiliza una variable de entorno o un archivo .env que no sea incluido en Git.

3. Ejecutar con Maven

El servicio se encuentra dentro del directorio product-service.

cd product-service

Ejecutar:

./mvnw spring-boot:run

En Windows:

mvnw.cmd spring-boot:run




## **Ejecución con Docker Compose**

El repositorio incluye un Docker-compose.yml en la raíz.

El servicio de Spring Boot se construye mediante el Dockerfile ubicado en product-service, mientras que PostgreSQL utiliza la imagen postgres:18-alpine. El Compose también incorpora un healthcheck para PostgreSQL y configura el servicio de la API para esperar a que la base de datos esté saludable.

Desde la raíz del repositorio:

docker compose up --build

Para ejecutar en segundo plano:

docker compose up --build -d

Para detener los servicios:

docker compose down

Para eliminar también el volumen de PostgreSQL:

docker compose down -v

El último comando elimina los datos persistidos en el volumen pgdata.



## **Testing**

Para ejecutar las pruebas:

./mvnw test

O en Windows:

mvnw.cmd test

Para ejecutar el ciclo completo de Maven:

./mvnw verify

Las pruebas de integración utilizan Testcontainers y PostgreSQL para ejecutar pruebas contra una base de datos real en un contenedor, evitando depender exclusivamente de una base de datos embebida.

El proyecto utiliza:

testcontainers-postgresql
testcontainers-junit-jupiter
spring-boot-testcontainers
spring-security-test



## **Cobertura de código**

La cobertura se genera mediante JaCoCo.

Para generar el reporte:

./mvnw verify

El reporte se genera dentro de:

target/site/jacoco/

El proyecto utiliza jacoco-maven-plugin versión 0.8.13.



## **Decisiones técnicas**

Clean Architecture + Hexagonal Architecture

Se utiliza una combinación de ambos enfoques para reducir el acoplamiento entre la lógica de negocio y la infraestructura.

Esto permite cambiar mecanismos externos, como la persistencia o la exposición HTTP, sin trasladar sus detalles hacia el núcleo de la aplicación.

DTOs

Los objetos utilizados para las peticiones y respuestas HTTP están separados del modelo de dominio.

Esto evita exponer directamente las entidades internas de la aplicación a través de la API.

Ports & Adapters

La aplicación define puertos para las operaciones que necesita y la infraestructura proporciona las implementaciones concretas.

Infrastructure Adapter
JWT / OAuth2 Resource Server

**La API no es responsable de generar los tokens de autenticación. Su responsabilidad es validar los JWT recibidos y aplicar autorización sobre los endpoints.**

Esto permite separar la responsabilidad de autenticación de la responsabilidad del microservicio de productos.

Testcontainers

Las pruebas de integración utilizan PostgreSQL mediante Testcontainers para aproximarse al entorno real de ejecución y evitar diferencias entre una base de datos embebida y PostgreSQL.



## **Estado del proyecto**

Actualmente el microservicio proporciona operaciones para:

* Productos
* Categorías
* Proveedores
* Unidades de medida
* Relaciones producto-proveedor
* Validación de requests
* Manejo de excepciones
* JWT / OAuth2 Resource Server
* OpenAPI / Swagger
* Unit testing
* Integration testing
* Testcontainers
* Docker
* Docker Compose
* JaCoCo



## **Próximos pasos**

El proyecto forma parte de una arquitectura de microservicios en evolución.

Algunas mejoras previstas:

Implementación del servicio de autenticación.
Integración con otros microservicios.
API Gateway.
Paginaciòn
Service Discovery.
Configuración centralizada.
Comunicación entre microservicios.
Mejoras adicionales de seguridad.
Versionado y gestión de releases.


## **Autor**

Eloy Bonilla

GitHub:
https://github.com/EBonilla13

Repositorio:
https://github.com/EBonilla13/Service-Products