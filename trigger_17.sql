-- Crear la función
CREATE OR REPLACE FUNCTION fn_verificar_zona_restringida()
RETURNS TRIGGER AS $$
BEGIN
    -- Verificar si la ubicación de la orden está dentro de alguna zona con estado 'Restringida'
    IF EXISTS (
        SELECT 1 
        FROM Zonas 
        WHERE estado = 'Restringida' 
        AND ST_Contains(Zonas.area::geometry, NEW.ubicacion::geometry)
    ) THEN
        -- Si la ubicación está dentro de la zona restringida, cambiar el estado a 'pendiente'
        NEW.estado := 'zona restringida';
    END IF;

    -- Retornar la nueva fila para que se inserte o actualice
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Crear el TRIGGER
CREATE OR REPLACE TRIGGER trg_verificar_zona_restringida
BEFORE INSERT OR UPDATE ON Orden
FOR EACH ROW
EXECUTE FUNCTION fn_verificar_zona_restringida();