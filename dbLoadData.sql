-- Poblar Categoria
INSERT INTO Categoria (nombre) VALUES 
('Electrónica'),
('Ropa'),
('Hogar'),
('Juguetes'),
('Deportes');

-- Poblar Clientes con ubicación geográfica
INSERT INTO Cliente (nombre, contrasena, direccion, email, telefono, ubicacion) VALUES 
('Juan Pérez', 'password123', 'Calle Falsa 123', 'juan.perez@example.com', '123456789', ST_GeogFromText('SRID=4326;POINT(-70.6693 -33.4489)')), -- Santiago, Chile
('Ana Gómez', 'securepass456', 'Av. Siempre Viva 456', 'ana.gomez@example.com', '987654321', ST_GeogFromText('SRID=4326;POINT(-70.6417 -33.4691)')), -- Región Metropolitana, Chile
('Carlos Ruiz', 'mypassword789', 'Calle Principal 789', 'carlos.ruiz@example.com', '555666777', ST_GeogFromText('SRID=4326;POINT(-70.6483 -33.4562)')); -- Providencia, Chile

-- Poblar Productos
INSERT INTO Producto (nombre, descripcion, precio, stock, estado, id_categoria) VALUES 
('Smartphone', 'Teléfono inteligente de última generación', 699.99, 50, 'disponible', 1),
('Camiseta', 'Camiseta 100% algodón', 19.99, 200, 'disponible', 2),
('Sofá', 'Sofá de 3 plazas', 499.99, 10, 'disponible', 3),
('Pelota de fútbol', 'Pelota oficial tamaño 5', 29.99, 100, 'disponible', 5),
('Muñeca', 'Muñeca interactiva con accesorios', 39.99, 25, 'disponible', 4);

-- Poblar Orden con ubicación geográfica
INSERT INTO Orden (fecha_orden, estado, id_cliente, total, ubicacion) VALUES 
('2024-11-01 14:30:00', 'pendiente', 1, 749.98, ST_GeogFromText('SRID=4326;POINT(-70.6693 -33.4489)')), -- Ubicación asociada al cliente Juan Pérez
('2024-11-02 10:15:00', 'pagada', 2, 29.99, ST_GeogFromText('SRID=4326;POINT(-70.6417 -33.4691)')), -- Ubicación asociada al cliente Ana Gómez
('2024-11-03 18:45:00', 'enviada', 1, 499.99, ST_GeogFromText('SRID=4326;POINT(-70.6693 -33.4489)')); -- Ubicación asociada al cliente Juan Pérez

-- Poblar detalle orden
INSERT INTO Detalle_Orden (id_orden, id_producto, cantidad, precio_unitario) VALUES 
(1, 1, 1, 699.99), -- Smartphone para la orden 1
(1, 2, 2, 19.99),  -- 2 camisetas para la orden 1
(2, 4, 1, 29.99),  -- Pelota de fútbol para la orden 2
(3, 3, 1, 499.99); -- Sofá para la orden 3

-- Insertar una zona con un polígono de ejemplo
INSERT INTO zonas (nombre, estado, area) VALUES 
('Zona 1', 'Restringida', ST_GeogFromText('SRID=4326;POLYGON((-70.65 -33.45, -70.66 -33.45, -70.66 -33.46, -70.65 -33.46, -70.65 -33.45))')),
('Zona 2', 'Disponible', ST_GeogFromText('SRID=4326;POLYGON((-20.65 -50.45, -70.66 -33.45, -70.66 -33.46, -70.65 -33.46, -70.65 -33.45))'));

