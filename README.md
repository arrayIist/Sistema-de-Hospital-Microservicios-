## Sistema de Gestión Hospitalaria
Microservicios desarrollados con Spring Boot.

## Servicios
- Eureka Server → Puerto 8761
- API Gateway → Puerto 8080
- Paciente Service → Puerto 8081
- Cita Service → Puerto 8082
- Notificación Service → RabbitMQ

## Endpoints por Gateway
- POST /api/pacientes
- GET /api/pacientes/{id}
- POST /api/citas
- PATCH /api/citas/{id}/estado
