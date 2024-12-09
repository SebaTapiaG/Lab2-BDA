-- Crear o reemplazar la función del trigger
CREATE OR REPLACE FUNCTION query_log()
    RETURNS TRIGGER AS $$
DECLARE
    actual_timestamp TIMESTAMP;
    query_type TEXT;
BEGIN
    -- Obtener el timestamp actual
    actual_timestamp := CURRENT_TIMESTAMP;

    -- Determinar el tipo de operación
    IF TG_OP = 'INSERT' THEN
        query_type := 'INSERT';
    ELSIF TG_OP = 'UPDATE' THEN
        query_type := 'UPDATE';
    ELSIF TG_OP = 'DELETE' THEN
        query_type := 'DELETE';
    END IF;

    -- Insertar en la tabla Query_Log
    INSERT INTO Query_Log (name, actual_timestamp, query_type, query_statement)
    VALUES (CURRENT_USER, actual_timestamp, query_type, CURRENT_QUERY());

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;



-- Crear el trigger asociado a la tabla Categoria
CREATE TRIGGER query_log_categoria
AFTER INSERT OR UPDATE OR DELETE ON Categoria
FOR EACH STATEMENT
EXECUTE FUNCTION query_log();

-- Crear el trigger asociado a la tabla Cliente
CREATE TRIGGER query_log_cliente
AFTER INSERT OR UPDATE OR DELETE ON Cliente
FOR EACH STATEMENT
EXECUTE FUNCTION query_log();

-- Crear el trigger asociado a la tabla Orden
CREATE TRIGGER query_log_orden
AFTER INSERT OR UPDATE OR DELETE ON Orden
FOR EACH STATEMENT
EXECUTE FUNCTION query_log();

-- Crear el trigger asociado a la tabla Categoria
CREATE TRIGGER query_log_producto
AFTER INSERT OR UPDATE OR DELETE ON Producto
FOR EACH STATEMENT
EXECUTE FUNCTION query_log();

-- Crear el trigger asociado a la tabla Detalle_Orden
CREATE TRIGGER query_log_detalle_orden
AFTER INSERT OR UPDATE OR DELETE ON Detalle_Orden
FOR EACH STATEMENT
EXECUTE FUNCTION query_log();