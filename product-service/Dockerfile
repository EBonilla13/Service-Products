
# = = = = = = = = = = = =
# Etapa 1: Build
# = = = = = = = = = = = =

# Usamos la imagen de maven y el kit de desarrollo de Java (JDK 17 de Eclipse Temurin),
# ademas de asignarle un Alias (AS builder) a esta etapa.
FROM maven:3.9.9-eclipse-temurin-17 AS builder

# Copiamos el archivo pom.xml
COPY pom.xml .

# Comandos a ejecutar, Se indica a maven que omita la descarga y preparacion de los plugins para las pruebas
# Ademas, se descarga en la cache local del contenedor todas las dependencias y librerias declaradas en el pom.xml
RUN mvn -B -q -e -DskipTests dependency:go-offline

# Se copia el directorio del codigo fuente Java
COPY src /src

# Compila y empaqueta el codigo fuente Java, y genera el archivo .jar
# Ademas de omitir la ejecucion de las pruebas
RUN mvn -B -DskipTests package

# = = = = = = = = = = = =
# Etapa 2: Runtime
# = = = = = = = = = = = =

# Se usa una imagen minimalista de JRE 17, el cual solo tiene el motor necesario para ejecutar Java
FROM eclipse-temurin:17-jre-alpine

# Copia un archivo de la etapa anterior llamada builder
# Busca el archivo .jar en el directorio target y lo renombra como app.jar
COPY --from=builder /target/*.jar app.jar

# Indica que la esta aplicacion dentro de la imagen escuchara en el puerto 8080
EXPOSE 8080

# Se define en formato de arreglo un comando que se ejecutara cuando el contenedor pase al estado de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]