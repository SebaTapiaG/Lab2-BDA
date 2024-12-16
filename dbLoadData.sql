-- Poblar Categoria
INSERT INTO Categoria (nombre) VALUES 
('Electrónica'),
('Ropa'),
('Hogar'),
('Juguetes'),
('Deportes');

-- Poblar Clientes con ubicación geográfica
INSERT INTO Cliente (nombre, contrasena, direccion, comuna, email, telefono, ubicacion) VALUES
('Juan Pérez', 'password123', 'José Garrido 125', 'Maipú', 'juan.perez@example.com', '123456789', ST_GeogFromText('SRID=4326;POINT(-70.655 -33.455)')) , -- Santiago, Chile
('Ana Gómez', 'securepass456', 'Los viñedos 1675', 'San Felipe', 'ana.gomez@example.com', '987654321', ST_GeogFromText('SRID=4326;POINT(-70.655 -33.455)')), -- Región Metropolitana, Chile
('Carlos Ruiz', 'mypassword789', 'Vicuña Mackenna 680', 'Peñaflor', 'carlos.ruiz@example.com', '555666777', ST_GeogFromText('SRID=4326;POINT(-70.6693 -33.4489)')); -- Providencia, Chile

-- Poblar Productos
INSERT INTO Producto (nombre, descripcion, precio, stock, estado, id_categoria) VALUES 
('Smartphone', 'Teléfono inteligente de última generación', 699.99, 50, 'disponible', 1),
('Camiseta', 'Camiseta 100% algodón', 19.99, 200, 'disponible', 2),
('Sofá', 'Sofá de 3 plazas', 499.99, 10, 'disponible', 3),
('Pelota de fútbol', 'Pelota oficial tamaño 5', 29.99, 100, 'disponible', 5),
('Muñeca', 'Muñeca interactiva con accesorios', 39.99, 25, 'disponible', 4);

-- Población Repartidor
INSERT INTO Repartidor (nombre, contrasena, email, telefono) VALUES
('Juan Pérez', 'password123', 'juan.perez@example.com', '987654321'),
('María González', 'securePass456', 'maria.gonzalez@example.com', '912345678'),
('Carlos López', 'mySecret789', 'carlos.lopez@example.com', '923456789'),
('Moha', 'moame', 'mohamed@example.com', '923456789');


-- Poblar Orden con ubicación geográfica
INSERT INTO Orden (fecha_orden, estado, id_cliente, total, ubicacion, id_repartidor) VALUES
('2024-11-01 14:30:00', 'completada', 1, 749.98, ST_GeogFromText('SRID=4326;POINT(-70.655 -33.455)'), 1), -- Ubicación asociada al cliente Juan Pérez
('2024-11-02 10:15:00', 'completada', 2, 29.99, ST_GeogFromText('SRID=4326;POINT(-70.655 -33.455)'), 2), -- Ubicación asociada al cliente Ana Gómez
('2024-11-03 18:45:00', 'enviada', 1, 499.99, ST_GeogFromText('SRID=4326;POINT(-70.6693 -33.4489)'), 3), -- Ubicación asociada al cliente Juan Pérez
('2024-11-04 09:00:00', 'completada', 1, 349.99,  ST_GeogFromText('SRID=4326;POINT(-70.645 -33.445)'), 1), -- Dentro de Zona 2 (Disponible)
('2024-11-05 12:30:00', 'completada', 2, 129.99, ST_GeogFromText('SRID=4326;POINT(-70.635 -33.425)'), 2), -- Dentro de Zona 4 (Disponible)
('2024-11-06 15:45:00', 'enviada', 3, 299.99, ST_GeogFromText('SRID=4326;POINT(-70.645 -33.445)'), 3), -- Dentro de Zona 2 (Disponible)
('2024-11-07 18:20:00', 'completada', 4, 749.99, ST_GeogFromText('SRID=4326;POINT(-70.625 -33.425)'), 4), -- Dentro de Zona 4 (Disponible)
('2024-11-08 20:00:00', 'enviada', 5, 549.99, ST_GeogFromText('SRID=4326;POINT(-70.645 -33.445)'), 2); -- Dentro de Zona 2 (Disponible)

-- Poblar detalle orden
INSERT INTO Detalle_Orden (id_orden, id_producto, cantidad, precio_unitario) VALUES 
(1, 1, 1, 699.99), -- Smartphone para la orden 1
(1, 2, 2, 19.99),  -- 2 camisetas para la orden 1
(2, 4, 1, 29.99),  -- Pelota de fútbol para la orden 2
(3, 3, 1, 499.99); -- Sofá para la orden 3

-- Insertar una zona con un polígono de ejemplo
INSERT INTO zonas (nombre, estado, area) VALUES 
('Zona 1', 'Restringida', ST_GeogFromText('SRID=4326;POLYGON((-70.65 -33.45, -70.66 -33.45, -70.66 -33.46, -70.65 -33.46, -70.65 -33.45))')),
('Zona 2', 'Disponible', ST_GeogFromText('SRID=4326;POLYGON((-70.64 -33.44, -70.65 -33.44, -70.65 -33.45, -70.64 -33.45, -70.64 -33.44))')),
('Zona 3', 'Restringida', ST_GeogFromText('SRID=4326;POLYGON((-70.66 -33.46, -70.67 -33.46, -70.67 -33.47, -70.66 -33.47, -70.66 -33.46))')),
('Zona 4', 'Disponible', ST_GeogFromText('SRID=4326;POLYGON((-70.62 -33.42, -70.63 -33.42, -70.63 -33.43, -70.62 -33.43, -70.62 -33.42))')),
('Zona 5', 'Restringida', ST_GeogFromText('SRID=4326;POLYGON((-70.60 -33.40, -70.61 -33.40, -70.61 -33.41, -70.60 -33.41, -70.60 -33.40))'));