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
INSERT INTO students (public_id, first_name, last_name, email, phone, date_of_birth, grade, section, parent_name, parent_phone, parent_email, address, enrollment_date, is_active, emergency_contact_name, emergency_contact_phone, emergency_contact_relationship, group_id, created_at, updated_at) VALUES
('b1a1e1c0-0001-4a1a-8a1a-000000000001', 'Ana', 'García', 'ana.garcia@email.com', '123-456-7890', '2015-03-15', 'Primero', 'A', 'María García', '987-654-3210', 'maria.garcia@email.com', 'Calle 123, Ciudad', '2024-02-01', true, 'Pedro García', '555-0123', 'Padre', 5, NOW(), NOW()),
('b1a1e1c0-0002-4a1a-8a1a-000000000002', 'Carlos', 'López', '', '', '2014-07-22', 'Primero', 'B', 'Carmen López', '987-654-3211', 'carmen.lopez@email.com', 'Avenida 456, Ciudad', '2024-02-01', true, 'Juan López', '555-0124', 'Padre', 6, NOW(), NOW()),
('b1a1e1c0-0003-4a1a-8a1a-000000000003', 'Sofía', 'Martínez', 'sofia.martinez@email.com', '123-456-7892', '2016-01-10', 'Preescolar', 'A', 'Laura Martínez', '987-654-3212', 'laura.martinez@email.com', 'Plaza 789, Ciudad', '2024-02-15', true, 'Roberto Martínez', '555-0125', 'Padre', 3, NOW(), NOW()),
('b1a1e1c0-0004-4a1a-8a1a-000000000004', 'Diego', 'Rodríguez', '', '', '2013-09-05', 'Segundo', 'A', 'Patricia Rodríguez', '987-654-3213', 'patricia.rodriguez@email.com', 'Bulevar 321, Ciudad', '2024-01-20', true, 'Miguel Rodríguez', '555-0126', 'Padre', 7, NOW(), NOW()),
('b1a1e1c0-0005-4a1a-8a1a-000000000005', 'Valentina', 'Herrera', 'valentina.herrera@email.com', '123-456-7894', '2017-12-03', 'Maternal', 'A', 'Andrea Herrera', '987-654-3214', 'andrea.herrera@email.com', 'Pasaje 654, Ciudad', '2024-03-01', true, 'Carlos Herrera', '555-0127', 'Padre', 1, NOW(), NOW()),
('b1a1e1c0-0006-4a1a-8a1a-000000000006', 'Mateo', 'Silva', '', '', '2012-04-18', 'Tercero', 'A', 'Mónica Silva', '987-654-3215', 'monica.silva@email.com', 'Diagonal 987, Ciudad', '2024-01-15', true, 'Fernando Silva', '555-0128', 'Padre', 8, NOW(), NOW()),
('b1a1e1c0-0007-4a1a-8a1a-000000000007', 'Isabella', 'Torres', 'isabella.torres@email.com', '123-456-7896', '2011-08-12', 'Primero', 'A', 'Claudia Torres', '987-654-3216', 'claudia.torres@email.com', 'Ronda 147, Ciudad', '2024-01-10', true, 'Alejandro Torres', '555-0129', 'Padre', 9, NOW(), NOW()),
('b1a1e1c0-0008-4a1a-8a1a-000000000008', 'Santiago', 'Morales', '', '', '2015-11-25', 'Primero', 'A', 'Beatriz Morales', '987-654-3217', 'beatriz.morales@email.com', 'Circuito 258, Ciudad', '2024-02-20', true, 'Ricardo Morales', '555-0130', 'Padre', 5, NOW(), NOW()),
('b1a1e1c0-0009-4a1a-8a1a-000000000009', 'Lucía', 'Ramírez', 'lucia.ramirez@email.com', '123-456-7891', '2015-05-10', 'Primero', 'A', 'Sofía Ramírez', '987-654-3220', 'sofia.ramirez@email.com', 'Calle 456, Ciudad', '2024-02-01', true, 'Mario Ramírez', '555-0131', 'Padre', 5, NOW(), NOW()),
('b1a1e1c0-0010-4a1a-8a1a-000000000010', 'Emilio', 'Gómez', 'emilio.gomez@email.com', '123-456-7892', '2014-09-18', 'Segundo', 'A', 'Laura Gómez', '987-654-3221', 'laura.gomez@email.com', 'Avenida 789, Ciudad', '2024-02-01', true, 'Pedro Gómez', '555-0132', 'Padre', 7, NOW(), NOW()),
('b1a1e1c0-0011-4a1a-8a1a-000000000011', 'Camila', 'Santos', 'camila.santos@email.com', '123-456-7893', '2016-03-22', 'Preescolar', 'A', 'Andrea Santos', '987-654-3222', 'andrea.santos@email.com', 'Plaza 321, Ciudad', '2024-02-15', true, 'Luis Santos', '555-0133', 'Padre', 3, NOW(), NOW()),
('b1a1e1c0-0012-4a1a-8a1a-000000000012', 'Martín', 'Vega', 'martin.vega@email.com', '123-456-7894', '2013-12-30', 'Tercero', 'A', 'Patricia Vega', '987-654-3223', 'patricia.vega@email.com', 'Bulevar 654, Ciudad', '2024-01-20', true, 'Jorge Vega', '555-0134', 'Padre', 8, NOW(), NOW()),
('b1a1e1c0-0013-4a1a-8a1a-000000000013', 'Renata', 'Mendoza', 'renata.mendoza@email.com', '123-456-7895', '2017-07-14', 'Maternal', 'A', 'Gabriela Mendoza', '987-654-3224', 'gabriela.mendoza@email.com', 'Pasaje 987, Ciudad', '2024-03-01', true, 'Carlos Mendoza', '555-0135', 'Padre', 1, NOW(), NOW()),
('b1a1e1c0-0014-4a1a-8a1a-000000000014', 'Tomás', 'Paredes', 'tomas.paredes@email.com', '123-456-7896', '2012-11-11', 'Primero', 'B', 'Mónica Paredes', '987-654-3225', 'monica.paredes@email.com', 'Diagonal 654, Ciudad', '2024-01-15', true, 'Fernando Paredes', '555-0136', 'Padre', 6, NOW(), NOW()),
('b1a1e1c0-0015-4a1a-8a1a-000000000015', 'Valeria', 'Navarro', 'valeria.navarro@email.com', '123-456-7897', '2011-06-25', 'Segundo', 'A', 'Claudia Navarro', '987-654-3226', 'claudia.navarro@email.com', 'Ronda 258, Ciudad', '2024-01-10', true, 'Alejandro Navarro', '555-0137', 'Padre', 7, NOW(), NOW()),
('b1a1e1c0-0016-4a1a-8a1a-000000000016', 'Julián', 'Cruz', 'julian.cruz@email.com', '123-456-7898', '2015-10-03', 'Primero', 'A', 'Beatriz Cruz', '987-654-3227', 'beatriz.cruz@email.com', 'Circuito 369, Ciudad', '2024-02-20', true, 'Ricardo Cruz', '555-0138', 'Padre', 5, NOW(), NOW()),
('b1a1e1c0-0017-4a1a-8a1a-000000000017', 'Paula', 'Silva', 'paula.silva@email.com', '123-456-7899', '2016-08-19', 'Preescolar', 'A', 'Marina Silva', '987-654-3228', 'marina.silva@email.com', 'Calle 741, Ciudad', '2024-02-15', true, 'Roberto Silva', '555-0139', 'Padre', 3, NOW(), NOW()),
('b1a1e1c0-0018-4a1a-8a1a-000000000018', 'David', 'Ortega', 'david.ortega@email.com', '123-456-7800', '2014-04-27', 'Segundo', 'A', 'Patricia Ortega', '987-654-3229', 'patricia.ortega@email.com', 'Avenida 852, Ciudad', '2024-01-20', true, 'Miguel Ortega', '555-0140', 'Padre', 7, NOW(), NOW()),
('b1a1e1c0-0019-4a1a-8a1a-000000000019', 'Mariana', 'Ríos', 'mariana.rios@email.com', '123-456-7801', '2013-02-14', 'Tercero', 'A', 'Andrea Ríos', '987-654-3230', 'andrea.rios@email.com', 'Plaza 963, Ciudad', '2024-01-15', true, 'Fernando Ríos', '555-0141', 'Padre', 8, NOW(), NOW()),
('b1a1e1c0-0020-4a1a-8a1a-000000000020', 'Santiago', 'Luna', 'santiago.luna@email.com', '123-456-7802', '2012-10-09', 'Primero', 'B', 'Mónica Luna', '987-654-3231', 'monica.luna@email.com', 'Diagonal 147, Ciudad', '2024-01-10', true, 'Alejandro Luna', '555-0142', 'Padre', 6, NOW(), NOW()),
('b1a1e1c0-0021-4a1a-8a1a-000000000021', 'Regina', 'Campos', 'regina.campos@email.com', '123-456-7803', '2011-03-21', 'Segundo', 'A', 'Claudia Campos', '987-654-3232', 'claudia.campos@email.com', 'Ronda 369, Ciudad', '2024-01-10', true, 'Alejandro Campos', '555-0143', 'Padre', 7, NOW(), NOW()),
('b1a1e1c0-0022-4a1a-8a1a-000000000022', 'Sebastián', 'Peña', 'sebastian.pena@email.com', '123-456-7804', '2015-12-01', 'Primero', 'A', 'Beatriz Peña', '987-654-3233', 'beatriz.pena@email.com', 'Circuito 741, Ciudad', '2024-02-20', true, 'Ricardo Peña', '555-0144', 'Padre', 5, NOW(), NOW());

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
