Este repositorio del juego de PacMan contiene solo código Java, a partir de la carpeta src/.
Cuando se te asigne una tarea, sigue estas guías:
## Estructura del proyecto
- `/src/main/java` : Código fuente principal.
- `/src/test/java` : Pruebas unitarias organizadas por paquete.
- `pom.xml` : Archivo de configuración de Maven.
## Convenciones para pruebas unitarias
1. **Ubicación**: Las clases de prueba deben estar en `/src/test/java`, replicando la estructura
de paquetes del código fuente.
2. **Nomenclatura**: Cada clase de prueba debe terminar en `Test`, por ejemplo:
`UserServiceTest`.
3. **Cobertura mínima**: Se espera una cobertura del 100% para clases nuevas. Usa `@Test`
para cada método relevante.
4. **Aislamiento**: Las pruebas deben ser independientes y no depender de estado compartido.
5. **Validación**: Ejecuta `mvn test` para validar los cambios antes de cada commit.
6. **Estilo**: Sigue las convenciones de código de Java (nombres en camelCase, clases en
PascalCase).
## Herramientas y dependencias
- Java 25
- JUnit 5 para pruebas unitarias
- Maven 3.8+
- JaCoCo para análisis de cobertura
## Flujo de desarrollo
- Formatear código: `mvn formatter:format`
- Ejecutar pruebas: `mvn test`
- Ver cobertura: `mvn jacoco:report`
- Validación completa: `mvn verify`

## Despliegue continuo a GitHub Pages con una landing page
El proyecto implementa despliegue continuo (CD) page en Github pages.
para publicar el .jar generado en una landing
### Activación del workflow
- Usa el evento `workflow_run` para activar el despliegue.
- El workflow debe ejecutarse después de que **`ci-ct` ha finalizado con éxito**.
- Solo se despliega desde la rama `main`.
### Workflow implementado
- Nombre del archivo: `.github/workflows/deploy-pages.yml`
- **Job Build**:
- Configura Java 17 
- **Job Deploy**:
- Configura GitHub Pages
- El artefacto es descargable en una landing page en GitHub Pages
- Notifica la URL del despliegue
