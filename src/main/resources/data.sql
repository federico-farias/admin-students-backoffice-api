-- Script de inicialización de datos para Escuela Lucía
-- Ejecutar después de crear la base de datos

USE admin_students;

-- Insertar grupos
INSERT INTO school_groups (academic_level, grade, name, academic_year, max_students, is_active, created_at, updated_at) VALUES
('MATERNAL', 'Primero', 'A', '2024-2025', 10, true, NOW(), NOW()),
('MATERNAL', 'Segundo', 'A', '2024-2025', 10, true, NOW(), NOW()),
('PREESCOLAR', 'Primero', 'A', '2024-2025', 15, true, NOW(), NOW()),
('PREESCOLAR', 'Segundo', 'A', '2024-2025', 15, true, NOW(), NOW()),
('PRIMARIA', 'Primero', 'A', '2024-2025', 25, true, NOW(), NOW()),
('PRIMARIA', 'Primero', 'B', '2024-2025', 25, true, NOW(), NOW()),
('PRIMARIA', 'Segundo', 'A', '2024-2025', 25, true, NOW(), NOW()),
('PRIMARIA', 'Tercero', 'A', '2024-2025', 25, true, NOW(), NOW()),
('SECUNDARIA', 'Primero', 'A', '2024-2025', 30, true, NOW(), NOW()),
('SECUNDARIA', 'Segundo', 'A', '2024-2025', 30, true, NOW(), NOW());

-- Insertar paquetes de desayuno
INSERT INTO breakfast_packages (name, description, price_per_day, price_per_week, price_per_month, is_active, created_at, updated_at) VALUES
('Básico', 'Desayuno básico con pan, leche y fruta', 5.00, 25.00, 100.00, true, NOW(), NOW()),
('Completo', 'Desayuno completo con cereales, yogurt y jugo', 8.00, 40.00, 160.00, true, NOW(), NOW()),
('Premium', 'Desayuno premium con opciones variadas', 12.00, 60.00, 240.00, true, NOW(), NOW());

-- Insertar estudiantes de ejemplo
INSERT INTO students (first_name, last_name, email, phone, date_of_birth, grade, section, parent_name, parent_phone, parent_email, address, enrollment_date, is_active, emergency_contact_name, emergency_contact_phone, emergency_contact_relationship, group_id, created_at, updated_at) VALUES
('Ana', 'García', 'ana.garcia@email.com', '123-456-7890', '2015-03-15', 'Primero', 'A', 'María García', '987-654-3210', 'maria.garcia@email.com', 'Calle 123, Ciudad', '2024-02-01', true, 'Pedro García', '555-0123', 'Padre', 5, NOW(), NOW()),
('Carlos', 'López', '', '', '2014-07-22', 'Primero', 'B', 'Carmen López', '987-654-3211', 'carmen.lopez@email.com', 'Avenida 456, Ciudad', '2024-02-01', true, 'Juan López', '555-0124', 'Padre', 6, NOW(), NOW()),
('Sofía', 'Martínez', 'sofia.martinez@email.com', '123-456-7892', '2016-01-10', 'Preescolar', 'A', 'Laura Martínez', '987-654-3212', 'laura.martinez@email.com', 'Plaza 789, Ciudad', '2024-02-15', true, 'Roberto Martínez', '555-0125', 'Padre', 3, NOW(), NOW()),
('Diego', 'Rodríguez', '', '', '2013-09-05', 'Segundo', 'A', 'Patricia Rodríguez', '987-654-3213', 'patricia.rodriguez@email.com', 'Bulevar 321, Ciudad', '2024-01-20', true, 'Miguel Rodríguez', '555-0126', 'Padre', 7, NOW(), NOW()),
('Valentina', 'Herrera', 'valentina.herrera@email.com', '123-456-7894', '2017-12-03', 'Maternal', 'A', 'Andrea Herrera', '987-654-3214', 'andrea.herrera@email.com', 'Pasaje 654, Ciudad', '2024-03-01', true, 'Carlos Herrera', '555-0127', 'Padre', 1, NOW(), NOW()),
('Mateo', 'Silva', '', '', '2012-04-18', 'Tercero', 'A', 'Mónica Silva', '987-654-3215', 'monica.silva@email.com', 'Diagonal 987, Ciudad', '2024-01-15', true, 'Fernando Silva', '555-0128', 'Padre', 8, NOW(), NOW()),
('Isabella', 'Torres', 'isabella.torres@email.com', '123-456-7896', '2011-08-12', 'Primero', 'A', 'Claudia Torres', '987-654-3216', 'claudia.torres@email.com', 'Ronda 147, Ciudad', '2024-01-10', true, 'Alejandro Torres', '555-0129', 'Padre', 9, NOW(), NOW()),
('Santiago', 'Morales', '', '', '2015-11-25', 'Primero', 'A', 'Beatriz Morales', '987-654-3217', 'beatriz.morales@email.com', 'Circuito 258, Ciudad', '2024-02-20', true, 'Ricardo Morales', '555-0130', 'Padre', 5, NOW(), NOW());

-- Insertar pagos de ejemplo
INSERT INTO payments (student_id, amount, payment_date, description, payment_method, status, due_date, period, period_type, breakfast_package_id, notes, created_at, updated_at) VALUES
(1, 150.00, '2025-01-05', 'Desayuno - Enero 2025', 'TRANSFERENCIA', 'PAGADO', '2025-01-31', 'Enero 2025', 'MENSUAL', 2, 'Pago completo del mes', NOW(), NOW()),
(2, 30.00, NULL, 'Desayuno - Semana 1 Agosto', 'EFECTIVO', 'PENDIENTE', '2025-08-07', 'Semana 1 de Agosto 2025', 'SEMANAL', 1, 'Primera semana de clases', NOW(), NOW()),
(3, 5.00, '2025-08-06', 'Desayuno - Día 6 Agosto', 'EFECTIVO', 'PAGADO', '2025-08-06', 'Día 6/8/2025', 'DIARIO', 1, 'Pago diario', NOW(), NOW()),
(4, 160.00, NULL, 'Desayuno - Agosto 2025', 'TARJETA', 'PENDIENTE', '2025-08-31', 'Agosto 2025', 'MENSUAL', 2, 'Pago mensual pendiente', NOW(), NOW()),
(5, 100.00, '2025-07-15', 'Desayuno - Julio 2025', 'TRANSFERENCIA', 'PAGADO', '2025-07-31', 'Julio 2025', 'MENSUAL', 1, 'Pago anticipado', NOW(), NOW()),
(6, 240.00, NULL, 'Desayuno - Agosto 2025', 'TRANSFERENCIA', 'VENCIDO', '2025-08-05', 'Agosto 2025', 'MENSUAL', 3, 'Pago vencido, contactar padre', NOW(), NOW()),
(7, 25.00, '2025-08-01', 'Desayuno - Semana 1 Agosto', 'EFECTIVO', 'PAGADO', '2025-08-07', 'Semana 1-5 Agosto', 'SEMANAL', 1, 'Primera semana pagada', NOW(), NOW()),
(8, 100.00, NULL, 'Desayuno - Agosto 2025', 'EFECTIVO', 'PENDIENTE', '2025-08-15', 'Agosto 2025', 'MENSUAL', 1, 'Pago pendiente del mes', NOW(), NOW());

-- Actualizar contador de estudiantes en grupos (esto se haría automáticamente con triggers en producción)
UPDATE groups SET students_count = (
    SELECT COUNT(*) FROM students WHERE students.group_id = groups.id
) WHERE groups.id IS NOT NULL;
