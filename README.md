# Ensamble Musical - Sistema de Gestión

En este proyecto hemos desarrollado una aplicación de escritorio para facilitar las tareas administrativas asociadas al Ensamble Músico Vocal de la Universidad Nacional de Colombia, sede Bogotá. Utlizando **Java** con **JavaFX** como parte del curso **Estructuras de Datos**.

El sistema actualmente implementa un **buzón de sugerencias** que utiliza una **cola (Queue)** como estructura principal, junto con persistencia en archivo local.

---

## Tecnologías utilizadas:

- Java 17+
- JavaFX
- IntelliJ IDEA
- Git & GitHub

---

## ¿Cómo ejecutar el proyecto?:

1. Clona el repositorio:

   ```bash
   git clone https://github.com/JJineteR41/data-structures-project

2. Abre el Proyecto en tu IDE de preferencia; nosotros utilizamos IntelliJ IDEA en su versión Community Edition.
3. Asegúrate de tener configurado JavaFX en tu entorno. En IntelliJ puedes revisar File → Project Structure → Libraries)
4. Ejecuta la clase HelloApplication para iniciar la aplicación.

## Estructura del Proyecto:

En el momento, la estructura del proyecto es la siguiente:

```
   src/
   └── org.example.interf/
       ├── HelloApplication.java        # Clase principal (punto de entrada)
       ├── InicioController.java        # Controlador de la pantalla principal
       ├── SugerenciasController.java   # Lógica del buzón de sugerencias
       ├── PersistenciaSugerencias.java # Clase que maneja lectura y escritura en archivo
       ├── Cola.java                    # Implementación de la estructura de cola
       ├── Nodo.java                    # Nodo genérico usado en la cola
   resources/
   └── org/example/interf/
       ├── inicio-view.fxml             # Interfaz de inicio
       └── sugerencias-view.fxml        # Interfaz del buzón
   data/
   └── sugerencias.txt                  # Archivo de persistencia 
```

## Autores:

1. Juan Miguel Díaz Jinete
2. Julián Esteban León Ibáñez
3. Federico Puentes Acosta
4. Santiago Rafael Puentes Durán
