# Escuela Lucía - Admin Students Backoffice API

API REST para el sistema de gestión escolar de la Escuela Lucía. Este backend proporciona endpoints para administrar estudiantes, grupos, pagos de desayunos y estadísticas del dashboard.

## 🚀 Características

- **Gestión de Estudiantes**: CRUD completo para registro de estudiantes
- **Gestión de Grupos**: Administración de grupos académicos por nivel y grado
- **Sistema de Pagos**: Control de pagos de desayunos con múltiples métodos
- **Dashboard**: Estadísticas y métricas del sistema
- **Autenticación**: Seguridad básica con Spring Security
- **Base de Datos**: MySQL con JPA/Hibernate
- **API REST**: Endpoints documentados y estructurados

## 🛠️ Tecnologías

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **Spring Security**
- **MySQL 8.0**
- **Maven**
- **Hibernate**
- **Bean Validation**

## 📋 Requisitos Previos

- Java 17 o superior
- Maven 3.6+
- MySQL 8.0+
- IDE compatible (IntelliJ IDEA, Eclipse, VS Code)

## 🚀 Instalación y Configuración

### 1. Clonar el repositorio

```bash
git clone [URL_DEL_REPOSITORIO]
cd admin-students-backoffice-api
```

### 2. Configurar Base de Datos

Crear la base de datos en MySQL:

```sql
CREATE DATABASE escuela_lucia_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'escuela_user'@'localhost' IDENTIFIED BY 'escuela_password';
GRANT ALL PRIVILEGES ON escuela_lucia_db.* TO 'escuela_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Configurar application.properties

Editar `src/main/resources/application.properties` si es necesario:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/escuela_lucia_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=escuela_user
spring.datasource.password=escuela_password

# Server Configuration
server.port=8080
server.servlet.context-path=/api
```

### 4. Compilar y ejecutar

```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar la aplicación
mvn spring-boot:run
```

La API estará disponible en: `http://localhost:8080/api`

## 📚 Endpoints Principales

### Estudiantes (`/api/students`)

- `GET /students` - Obtener todos los estudiantes
- `GET /students/active` - Obtener estudiantes activos
- `GET /students/{id}` - Obtener estudiante por ID
- `GET /students/search?query={texto}` - Buscar estudiantes
- `POST /students` - Crear nuevo estudiante
- `PUT /students/{id}` - Actualizar estudiante
- `DELETE /students/{id}` - Eliminar estudiante
- `PATCH /students/{id}/toggle-status` - Cambiar estado activo/inactivo

### Grupos (`/api/groups`)

- `GET /groups` - Obtener todos los grupos
- `GET /groups/active` - Obtener grupos activos
- `GET /groups/{id}` - Obtener grupo por ID
- `GET /groups/available` - Obtener grupos con espacios disponibles
- `POST /groups` - Crear nuevo grupo
- `PUT /groups/{id}` - Actualizar grupo
- `DELETE /groups/{id}` - Eliminar grupo

### Dashboard (`/api/dashboard`)

- `GET /dashboard/stats` - Estadísticas principales
- `GET /dashboard/monthly-revenue` - Ingresos mensuales
- `GET /dashboard/upcoming-payments` - Resumen de pagos próximos
- `GET /dashboard/quick-stats` - Estadísticas rápidas

### Actuator (`/api/actuator`)

- `GET /actuator/health` - Estado de salud de la aplicación
- `GET /actuator/info` - Información de la aplicación
- `GET /actuator/metrics` - Métricas del sistema

## 🔐 Autenticación

La API usa autenticación básica HTTP. Usuarios predefinidos:

- **Admin**: `admin` / `admin123`
- **Usuario**: `user` / `user123`

Ejemplo de autenticación:
```bash
curl -u admin:admin123 http://localhost:8080/api/dashboard/stats
```

## 📊 Modelo de Datos

### Student (Estudiante)
```json
{
  "id": 1,
  "firstName": "Ana",
  "lastName": "García",
  "email": "ana.garcia@email.com",
  "phone": "123-456-7890",
  "dateOfBirth": "2015-03-15",
  "grade": "Primero",
  "section": "A",
  "parentName": "María García",
  "parentPhone": "987-654-3210",
  "parentEmail": "maria.garcia@email.com",
  "address": "Calle 123, Ciudad",
  "enrollmentDate": "2024-02-01",
  "isActive": true,
  "emergencyContact": {
    "name": "Pedro García",
    "phone": "555-0123",
    "relationship": "Padre"
  }
}
```

### Group (Grupo)
```json
{
  "id": 1,
  "academicLevel": "PRIMARIA",
  "grade": "Primero",
  "name": "A",
  "maxStudents": 25,
  "studentsCount": 20,
  "isActive": true
}
```

### Payment (Pago)
```json
{
  "id": 1,
  "studentId": 1,
  "studentName": "Ana García",
  "amount": 150.00,
  "paymentDate": "2025-01-05",
  "description": "Desayuno - Enero 2025",
  "paymentMethod": "TRANSFERENCIA",
  "status": "PAGADO",
  "dueDate": "2025-01-31",
  "period": "Enero 2025",
  "periodType": "MENSUAL"
}
```

## 🏗️ Estructura del Proyecto

```
src/main/java/com/escuelalucia/admin/
├── EscuelaLuciaAdminApiApplication.java    # Clase principal
├── config/
│   └── SecurityConfig.java                # Configuración de seguridad
├── controller/                            # Controladores REST
│   ├── DashboardController.java
│   ├── GroupController.java
│   └── StudentController.java
├── dto/                                   # Data Transfer Objects
│   ├── DashboardStatsDTO.java
│   ├── GroupDTO.java
│   ├── PaymentDTO.java
│   └── StudentDTO.java
├── model/                                 # Entidades JPA
│   ├── AcademicLevel.java
│   ├── BreakfastPackage.java
│   ├── Group.java
│   ├── Payment.java
│   ├── PaymentMethod.java
│   ├── PaymentStatus.java
│   ├── PeriodType.java
│   └── Student.java
├── repository/                            # Repositorios JPA
│   ├── BreakfastPackageRepository.java
│   ├── GroupRepository.java
│   ├── PaymentRepository.java
│   └── StudentRepository.java
└── service/                               # Servicios de negocio
    ├── DashboardService.java
    ├── GroupService.java
    └── StudentService.java
```

## 🧪 Testing

Para ejecutar las pruebas:

```bash
mvn test
```

## 📦 Construcción para Producción

```bash
# Crear JAR ejecutable
mvn clean package

# Ejecutar JAR
java -jar target/admin-students-backoffice-api-1.0.0.jar
```

## 🚀 Despliegue

### Variables de Entorno para Producción

```bash
export SPRING_PROFILES_ACTIVE=prod
export DB_HOST=localhost
export DB_PORT=3306
export DB_NAME=escuela_lucia_db
export DB_USERNAME=escuela_user
export DB_PASSWORD=your_secure_password
export SERVER_PORT=8080
```

### Docker (Opcional)

```dockerfile
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/admin-students-backoffice-api-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

## 🤝 Integración con Frontend

Esta API está diseñada para trabajar con el frontend React disponible en:
[admin-students-backoffice](https://github.com/federico-farias/admin-students-backoffice)

### Configuración CORS

La API está configurada para permitir requests desde:
- `http://localhost:5173` (Vite dev server)
- `http://localhost:3000` (React dev server)

## 📈 Monitoreo

La aplicación incluye Spring Boot Actuator para monitoreo:

- Health Check: `GET /api/actuator/health`
- Metrics: `GET /api/actuator/metrics`
- Info: `GET /api/actuator/info`

## 🔧 Desarrollo

### Agregar Nuevas Funcionalidades

1. Crear la entidad en `model/`
2. Crear el repositorio en `repository/`
3. Crear el DTO en `dto/`
4. Implementar el servicio en `service/`
5. Crear el controlador en `controller/`
6. Actualizar tests

### Hot Reload

El proyecto incluye Spring Boot DevTools para hot reload automático durante el desarrollo.

## 📝 Licencia

Este proyecto está bajo la Licencia MIT.

## 👥 Contribución

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -m 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abrir un Pull Request

## 📞 Soporte

Para soporte técnico o consultas sobre el proyecto, por favor crear un issue en el repositorio.
