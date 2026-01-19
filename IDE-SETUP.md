# Configuración del IDE para el Proyecto PacmanGPS

## Problema: Errores de compilación en el IDE

Si ves errores en tu IDE (IntelliJ IDEA, Eclipse, etc.) indicando que faltan las dependencias de JUnit y Mockito, pero `mvn test` funciona correctamente desde la línea de comandos, esto es un problema de sincronización del IDE con Maven.

## Solución para IntelliJ IDEA

### Opción 1: Reimportar el Proyecto Maven
1. Abre la ventana de Maven (View → Tool Windows → Maven)
2. Haz clic en el botón de "Reload All Maven Projects" (icono de recarga circular)
3. Espera a que el IDE descargue e indexe todas las dependencias

### Opción 2: Invalidar Cachés y Reiniciar
1. File → Invalidate Caches / Restart...
2. Selecciona "Invalidate and Restart"
3. Espera a que el IDE se reinicie y reindexe el proyecto

### Opción 3: Eliminar y Reimportar el Proyecto
1. Cierra el proyecto en IntelliJ
2. Elimina la carpeta `.idea/` del proyecto
3. Abre el proyecto de nuevo seleccionando el archivo `pom.xml`
4. IntelliJ lo importará como un proyecto Maven nuevo

### Opción 4: Desde la Terminal (Recomendado)
```bash
# Limpia y construye el proyecto con Maven
mvn clean install

# Luego en IntelliJ, reimporta el proyecto Maven
```

## Solución para Eclipse

1. Haz clic derecho en el proyecto
2. Maven → Update Project...
3. Selecciona "Force Update of Snapshots/Releases"
4. Haz clic en OK

## Solución para VSCode

1. Abre la paleta de comandos (Ctrl+Shift+P)
2. Escribe "Java: Clean Java Language Server Workspace"
3. Confirma la acción
4. Recarga la ventana (Ctrl+Shift+P → "Reload Window")

## Verificación

Una vez completada la reimportación, verifica que:

✅ La carpeta `External Libraries` o `Maven Dependencies` muestre:
- junit-jupiter-api-5.10.1.jar
- junit-jupiter-engine-5.10.1.jar
- mockito-core-5.8.0.jar
- mockito-junit-jupiter-5.8.0.jar

✅ No debería haber errores rojos en los archivos de prueba

✅ Ejecutar `mvn clean test` debe mostrar:
```
Tests run: 127, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## Ejecución desde la Línea de Comandos

Si el IDE continúa teniendo problemas, siempre puedes usar Maven directamente:

```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar los tests
mvn test

# Verificar cobertura
mvn verify

# Ver reporte de cobertura
mvn jacoco:report
# Luego abre: target/site/jacoco/index.html
```

## Notas Importantes

- **Las dependencias están correctas en `pom.xml`** ✅
- **Los tests compilan y ejecutan correctamente con Maven** ✅
- **El problema es solo de sincronización del IDE** ⚠️

Si después de intentar estas soluciones el problema persiste, asegúrate de que:
1. Estás usando Java 17 (verificar con `java -version`)
2. Maven está correctamente instalado (verificar con `mvn -version`)
3. Tienes conexión a Internet para descargar las dependencias
