-- Poblar Categoria
INSERT INTO Categoria (nombre) VALUES 
('Electrónica'),
('Ropa'),
('Hogar'),
('Juguetes'),
('Deportes');

-- Poblar Clientes con ubicación geográfica
INSERT INTO Cliente (nombre, contrasena, direccion, comuna, email, telefono, ubicacion) VALUES
('Juan Pérez', 'password123', 'José Garrido 125', 'Maipú', 'juan.perez@example.com', '123456789', ST_GeogFromText('SRID=4326;POINT(-70.655 -33.455)')), -- Santiago, Chile
('Ana Gómez', 'securepass456', 'Los viñedos 1675', 'San Felipe', 'ana.gomez@example.com', '987654321', ST_GeogFromText('SRID=4326;POINT(-70.655 -33.455)')), -- Región Metropolitana, Chile
('Carlos Ruiz', 'mypassword789', 'Vicuña Mackenna 680', 'Peñaflor', 'carlos.ruiz@example.com', '555666777', ST_GeogFromText('SRID=4326;POINT(-70.6693 -33.4489)')); -- Providencia, Chile

-- Poblar Productos
INSERT INTO Producto (nombre, descripcion, precio, stock, estado, id_categoria) VALUES 
('Smartphone', 'Teléfono inteligente de última generación', 699.99, 50, 'disponible', (SELECT id_categoria FROM Categoria WHERE nombre = 'Electrónica')),
('Camiseta', 'Camiseta 100% algodón', 19.99, 200, 'disponible', (SELECT id_categoria FROM Categoria WHERE nombre = 'Ropa')),
('Sofá', 'Sofá de 3 plazas', 499.99, 10, 'disponible', (SELECT id_categoria FROM Categoria WHERE nombre = 'Hogar')),
('Pelota de fútbol', 'Pelota oficial tamaño 5', 29.99, 100, 'disponible', (SELECT id_categoria FROM Categoria WHERE nombre = 'Deportes')),
('Muñeca', 'Muñeca interactiva con accesorios', 39.99, 25, 'disponible', (SELECT id_categoria FROM Categoria WHERE nombre = 'Juguetes'));

-- Poblar Repartidor
INSERT INTO Repartidor (nombre, contrasena, email, telefono) VALUES
('Juan Pérez', 'password123', 'juan.perez@example.com', '987654321'),
('María González', 'securePass456', 'maria.gonzalez@example.com', '912345678'),
('Carlos López', 'mySecret789', 'carlos.lopez@example.com', '923456789'),
('Moha', 'moame', 'mohamed@example.com', '923456789');

-- Poblar Orden con ubicación geográfica
INSERT INTO Orden (fecha_orden, estado, id_cliente, total, ubicacion, id_repartidor) VALUES
('2024-11-01 14:30:00', 'completada', (SELECT id_cliente FROM Cliente WHERE email = 'juan.perez@example.com'), 749.98, ST_GeogFromText('SRID=4326;POINT(-70.655 -33.455)'), (SELECT id_repartidor FROM Repartidor WHERE nombre = 'Juan Pérez')),
('2024-11-02 10:15:00', 'completada', (SELECT id_cliente FROM Cliente WHERE email = 'ana.gomez@example.com'), 29.99, ST_GeogFromText('SRID=4326;POINT(-70.655 -33.455)'), (SELECT id_repartidor FROM Repartidor WHERE nombre = 'María González')),
('2024-11-03 18:45:00', 'enviada', (SELECT id_cliente FROM Cliente WHERE email = 'juan.perez@example.com'), 499.99, ST_GeogFromText('SRID=4326;POINT(-70.6693 -33.4489)'), (SELECT id_repartidor FROM Repartidor WHERE nombre = 'Carlos López')),
('2024-11-04 09:00:00', 'completada', (SELECT id_cliente FROM Cliente WHERE email = 'juan.perez@example.com'), 349.99, ST_GeogFromText('SRID=4326;POINT(-70.645 -33.445)'), (SELECT id_repartidor FROM Repartidor WHERE nombre = 'Juan Pérez')),
('2024-11-05 12:30:00', 'completada', (SELECT id_cliente FROM Cliente WHERE email = 'ana.gomez@example.com'), 129.99, ST_GeogFromText('SRID=4326;POINT(-70.635 -33.425)'), (SELECT id_repartidor FROM Repartidor WHERE nombre = 'María González')),
('2024-11-06 15:45:00', 'enviada', (SELECT id_cliente FROM Cliente WHERE email = 'carlos.ruiz@example.com'), 299.99, ST_GeogFromText('SRID=4326;POINT(-70.645 -33.445)'), (SELECT id_repartidor FROM Repartidor WHERE nombre = 'Carlos López'));

-- Poblar Detalle Orden
INSERT INTO Detalle_Orden (id_orden, id_producto, cantidad, precio_unitario) VALUES 
((SELECT id_orden FROM Orden WHERE fecha_orden = '2024-11-01 14:30:00'), (SELECT id_producto FROM Producto WHERE nombre = 'Smartphone'), 1, 699.99),
((SELECT id_orden FROM Orden WHERE fecha_orden = '2024-11-01 14:30:00'), (SELECT id_producto FROM Producto WHERE nombre = 'Camiseta'), 2, 19.99),
((SELECT id_orden FROM Orden WHERE fecha_orden = '2024-11-02 10:15:00'), (SELECT id_producto FROM Producto WHERE nombre = 'Pelota de fútbol'), 1, 29.99),
((SELECT id_orden FROM Orden WHERE fecha_orden = '2024-11-03 18:45:00'), (SELECT id_producto FROM Producto WHERE nombre = 'Sofá'), 1, 499.99);

-- Insertar zonas con un polígono de ejemplo
INSERT INTO zonas (nombre, estado, area) VALUES 
('Zona 1', 'Restringida', ST_GeogFromText('SRID=4326;POLYGON((-70.65 -33.45, -70.66 -33.45, -70.66 -33.46, -70.65 -33.46, -70.65 -33.45))')),
('Zona 2', 'Disponible', ST_GeogFromText('SRID=4326;POLYGON((-70.64 -33.44, -70.65 -33.44, -70.65 -33.45, -70.64 -33.45, -70.64 -33.44))')),
('Zona 3', 'Restringida', ST_GeogFromText('SRID=4326;POLYGON((-70.66 -33.46, -70.67 -33.46, -70.67 -33.47, -70.66 -33.47, -70.66 -33.46))')),
('Zona 4', 'Disponible', ST_GeogFromText('SRID=4326;POLYGON((-70.62 -33.42, -70.63 -33.42, -70.63 -33.43, -70.62 -33.43, -70.62 -33.42))')),
('Zona 5', 'Restringida', ST_GeogFromText('SRID=4326;POLYGON((-70.60 -33.40, -70.61 -33.40, -70.61 -33.41, -70.60 -33.41, -70.60 -33.40))'));
