-- Tabla Categoria
CREATE TABLE Categoria (
    id_categoria SERIAL PRIMARY KEY, -- Identificador único de la categoría
    nombre VARCHAR(100) NOT NULL     -- Nombre de la categoría
);

-- Tabla Cliente
CREATE TABLE Cliente (
    id_cliente SERIAL PRIMARY KEY, -- Identificador único del cliente
    nombre VARCHAR(255) NOT NULL,  -- Nombre completo del cliente
    contrasena VARCHAR(255) NOT NULL, -- Contraseña del cliente
    direccion VARCHAR(255),        -- Dirección del cliente
    email VARCHAR(100) UNIQUE,     -- Correo electrónico del cliente (único)
    telefono VARCHAR(20)           -- Teléfono de contacto
);

-- Tabla Orden
CREATE TABLE Orden (
    id_orden SERIAL PRIMARY KEY,   -- Identificador único de la orden
    fecha_orden TIMESTAMP NOT NULL DEFAULT NOW(), -- Fecha de la orden (por defecto la actual)
    estado VARCHAR(50) NOT NULL CHECK (estado IN ('pendiente', 'pagada', 'enviada')), -- Estado de la orden
    id_cliente INTEGER NOT NULL,   -- Clave foránea a Cliente
    total DECIMAL(10, 2) NOT NULL, -- Total a pagar por la orden
    CONSTRAINT fk_orden_cliente FOREIGN KEY (id_cliente) REFERENCES Cliente (id_cliente) ON DELETE CASCADE
);

-- Tabla Producto
CREATE TABLE Producto (
    id_producto SERIAL PRIMARY KEY, -- Identificador único del producto
    nombre VARCHAR(255) NOT NULL,   -- Nombre del producto
    descripcion TEXT,               -- Descripción del producto
    precio DECIMAL(10, 2) NOT NULL, -- Precio del producto
    stock INT NOT NULL,             -- Cantidad disponible del producto
    estado VARCHAR(50) NOT NULL CHECK (estado IN ('disponible', 'agotado')), -- Estado del producto
    id_categoria INTEGER NOT NULL,  -- Clave foránea a Categoria
    CONSTRAINT fk_producto_categoria FOREIGN KEY (id_categoria) REFERENCES Categoria (id_categoria) ON DELETE SET NULL
);

-- Tabla Detalle_Orden
CREATE TABLE Detalle_Orden (
    id_detalle SERIAL PRIMARY KEY,   -- Identificador único del detalle de la orden
    id_orden INTEGER NOT NULL,       -- Clave foránea a Orden
    id_producto INTEGER NOT NULL,    -- Clave foránea a Producto
    cantidad INT NOT NULL CHECK (cantidad > 0), -- Cantidad del producto en la orden
    precio_unitario DECIMAL(10, 2) NOT NULL, -- Precio unitario del producto
    CONSTRAINT fk_detalle_orden FOREIGN KEY (id_orden) REFERENCES Orden (id_orden) ON DELETE CASCADE,
    CONSTRAINT fk_detalle_producto FOREIGN KEY (id_producto) REFERENCES Producto (id_producto) ON DELETE CASCADE
);

-- Tabla Query_Log
CREATE TABLE Query_Log (
    id_query_log SERIAL PRIMARY KEY,   -- Identificador único de la query_log
    name VARCHAR(255) NOT NULL,
    actual_timestamp TIMESTAMP NOT NULL,
    query_type CHAR(50) NOT NULL,
	query_statement TEXT NOT NULL
);
