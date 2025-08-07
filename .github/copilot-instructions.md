<!-- Use this file to provide workspace-specific custom instructions to Copilot. For more details, visit https://code.visualstudio.com/docs/copilot/copilot-customization#_use-a-githubcopilotinstructionsmd-file -->

# Escuela Lucía Admin API - Copilot Instructions

## Project Overview
This is a Spring Boot REST API for a school management system ("Escuela Lucía") that manages students, groups, payments for school breakfasts, and provides dashboard statistics.

## Tech Stack
- Java 17
- Spring Boot 3.2.0
- Spring Data JPA with Hibernate
- Spring Security (Basic Auth)
- MySQL 8.0
- Maven
- Bean Validation

## Code Style and Conventions
- Use Java naming conventions (camelCase for variables/methods, PascalCase for classes)
- All service methods should be annotated with @Transactional
- Use DTOs for API requests/responses, never expose entities directly
- Include proper validation annotations on DTOs (@NotNull, @NotBlank, @Email, etc.)
- Return ResponseEntity with proper HTTP status codes in controllers
- Use Optional for methods that might not return a value
- Include proper exception handling in controllers
- Follow REST API conventions for endpoint naming

## Architecture Patterns
- Entity → Repository → Service → Controller layers
- Use DTOs to transfer data between layers
- Repository pattern with Spring Data JPA
- Service layer contains business logic
- Controllers handle HTTP requests/responses only

## Database Design
- Students can be assigned to Groups
- Payments are linked to Students
- Groups have academic levels (MATERNAL, PREESCOLAR, PRIMARIA, SECUNDARIA)
- Payment statuses: PENDIENTE, PAGADO, VENCIDO
- Payment methods: EFECTIVO, TRANSFERENCIA, TARJETA
- Period types: DIARIO, SEMANAL, MENSUAL

## Key Entities
- Student: Main entity for student information
- Group: Academic groups with capacity limits
- Payment: Breakfast payment tracking
- BreakfastPackage: Different pricing packages

## API Design
- All endpoints use /api prefix
- REST conventions: GET, POST, PUT, DELETE, PATCH
- Use query parameters for filtering/searching
- Return consistent JSON responses
- Include proper CORS configuration for frontend integration

## Security
- Basic HTTP authentication
- Two users: admin/admin123, user/user123
- All endpoints (except actuator/health) require authentication
- CORS enabled for local development (ports 3000, 5173)

## Testing Guidelines
- Write unit tests for services
- Integration tests for repositories
- Mock external dependencies
- Test both success and error scenarios

## Frontend Integration
- This API works with a React frontend
- Ensure CORS is properly configured
- Use consistent data formats that match frontend expectations
- Handle errors gracefully and return meaningful messages
