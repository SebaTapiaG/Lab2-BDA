-- Crear o reemplazar la función del trigger
CREATE OR REPLACE FUNCTION validar_stock()
RETURNS TRIGGER AS $$
BEGIN
    -- Verificar si el stock del producto es suficiente
    IF (SELECT stock FROM Producto WHERE id_producto = NEW.id_producto) < NEW.cantidad THEN
        RAISE EXCEPTION 'Stock insuficiente para el producto con id_producto=%', NEW.id_producto;
    END IF;

    -- Actualizar el stock del producto, restando la cantidad solicitada
    UPDATE Producto
    SET stock = stock - NEW.cantidad
    WHERE id_producto = NEW.id_producto;

    RETURN NEW; -- Permitir la inserción si no hay problemas con el stock
END;
$$ LANGUAGE plpgsql;

-- Crear el trigger asociado a la tabla Detalle_Orden
CREATE TRIGGER trigger_validar_stock
BEFORE INSERT ON Detalle_Orden
FOR EACH ROW
EXECUTE FUNCTION validar_stock();