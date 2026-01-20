# PacmanProject

Juego básico de Pac-Man desarrollado en Java utilizando Swing para la interfaz gráfica.

## Características

- Laberinto donde Pac-Man y los fantasmas se mueven.
- Movimiento de Pac-Man controlado por el usuario (teclas de flecha).
- Fantasmas con movimiento automático.
- Puntos coleccionables que aumentan el puntaje.
- Condiciones de victoria (comer todos los puntos) y derrota (ser atrapado por un fantasma).
- Interfaz gráfica sencilla y modular.
- **94% cobertura de pruebas** con 79 pruebas unitarias

## Estructura de Archivos

```
pacmanGPS/
├── src/
│   ├── main/java/          # Código fuente
│   │   ├── Game.java       # Clase principal: inicializa la ventana y el juego
│   │   ├── Board.java      # Lógica y renderizado del tablero
│   │   ├── Pacman.java     # Lógica y renderizado de Pac-Man
│   │   ├── Ghost.java      # Lógica y renderizado de los fantasmas
│   │   ├── PowerUp.java    # Lógica de power-ups
│   │   └── Direction.java  # Enum para las direcciones de movimiento
│   └── test/java/          # Pruebas unitarias (JUnit 5)
├── pom.xml                 # Configuración de Maven
└── README.md
```

## Requisitos

- **Java 17** o superior
- **Maven 3.6+** (requerido para compilar y ejecutar pruebas)

## Compilación y Ejecución

⚠️ **IMPORTANTE**: Este proyecto usa Maven para la gestión de dependencias. NO uses `javac` directamente.

### Compilar el proyecto

```bash
mvn compile
```

### Ejecutar las pruebas

```bash
mvn test
```

### Ejecutar el juego

```bash
mvn exec:java -Dexec.mainClass="Game"
```

O después de compilar:

```bash
java -cp target/classes Game
```

### Ver cobertura de código

```bash
mvn verify
```

El reporte de cobertura se genera en: `target/site/jacoco/index.html`

## Configuración del IDE

### IntelliJ IDEA / Eclipse / NetBeans

1. Abre el proyecto como **proyecto Maven**
2. El IDE detectará automáticamente las dependencias del `pom.xml`
3. Ejecuta las pruebas desde el IDE o usando Maven

### VS Code

1. Instala las extensiones: **Java Extension Pack** y **Maven for Java**
2. Abre el proyecto - VS Code detectará automáticamente el `pom.xml`

## Solución de Problemas

### ❌ Error: "package org.junit.jupiter.api does not exist"

Esto significa que estás intentando compilar **sin Maven**. Las dependencias (JUnit, Mockito) se gestionan a través de Maven.

**Solución**: Usa Maven para compilar:

```bash
mvn clean compile test-compile
```

### Dependencias no se descargan

```bash
mvn dependency:resolve
mvn clean install
```

## Controles

- **Flechas del teclado** para mover a Pac-Man: izquierda, derecha, arriba, abajo.

## Extensiones Futuras

- Mejorar la IA de los fantasmas.
- Añadir niveles y nuevos mapas.
- Implementar efectos de sonido.
- Añadir “power-ups” y más funcionalidades clásicas del juego.

## Autor

Proyecto desarrollado por [paberlo].

---

¡Disfruta programando y jugando Pac-Man!