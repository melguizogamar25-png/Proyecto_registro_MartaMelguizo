# Registro de Jornada — Marta Melguizo García

API REST para gestionar el registro de jornada de una empresa: departamentos, empleados y fichajes de entrada y salida.

Proyecto de Acceso a Datos y Programación de Servicios y Procesos · Curso 2026-27 · Autor: **Tu nombre**

## Tecnologías

Java 21 · Spring Boot 4.1.1 (Web, Data JPA, Validation) · H2 · Lombok · springdoc-openapi (Swagger) · Maven

## Cómo ejecutarlo

```bash
git clone <URL-DEL-REPOSITORIO>
cd registro-jornada
./mvnw spring-boot:run        # en Windows: mvnw.cmd spring-boot:run
```

- API: `http://localhost:8080/api/v1`
- Swagger: `http://localhost:8080/swagger-ui.html`
- Consola H2: `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:./db/basedatos`, usuario `sa`, sin contraseña)

La base de datos se recrea en cada arranque con datos de ejemplo.

## Modelo de datos

- **Departamento**: id, nombre (único), presupuesto. Tiene muchos empleados.
- **Empleado**: id, nombreCompleto, cargo, salario. Pertenece a un departamento y tiene muchos fichajes.
- **Fichaje**: id, momento, tipo (`ENTRADA` o `SALIDA`).

## Endpoints

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/departamentos` | Listar departamentos (con sus empleados) |
| GET | `/departamentos/{id}` | Obtener un departamento |
| POST | `/departamentos` | Crear un departamento |
| PUT | `/departamentos/{id}` | Modificar un departamento |
| DELETE | `/departamentos/{id}` | Borrar un departamento |
| GET | `/empleados` | Listar empleados |
| GET | `/empleados/{id}` | Obtener un empleado |
| POST | `/empleados` | Crear un empleado |
| PUT | `/empleados/{id}` | Modificar un empleado |
| DELETE | `/empleados/{id}` | Borrar un empleado |
| PUT | `/empleados/{empleadoId}/departamento/{deptoId}` | Asignar un empleado a un departamento |
| POST | `/empleados/{empleadoId}/fichar` | Registrar una entrada o salida |
| GET | `/empleados/{empleadoId}/fichajes` | Historial de fichajes de un empleado |

**Fichar:** sin cuerpo alterna automáticamente entre ENTRADA y SALIDA. También se puede indicar el tipo con `{ "tipo": "ENTRADA" }`. Si es igual que el último fichaje del empleado, devuelve `409`.

## Validaciones y errores

- Campos obligatorios, presupuesto y salario con valores válidos.
- Validación personalizada `@SalariosDentroDePresupuesto`: al crear un empleado, la suma de los salarios del departamento no puede superar su presupuesto.
- Los errores se devuelven como `ProblemDetail`:

| Código | Cuándo |
|---|---|
| 400 | Datos no válidos |
| 404 | Departamento o empleado no encontrado |
| 409 | Fichaje duplicado, nombre de departamento repetido o departamento con empleados |