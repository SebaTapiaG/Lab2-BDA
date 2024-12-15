-- Crear una función para el trigger
CREATE OR REPLACE FUNCTION listar_repartidores_en_zona()
    RETURNS TRIGGER AS $$
BEGIN
    RAISE NOTICE 'Repartidores con entregas completadas en zona %:', NEW.id_zona;
    RAISE NOTICE '%', (
        SELECT STRING_AGG(DISTINCT r.nombre, ', ')
        FROM Orden o
                 JOIN Repartidor r ON o.id_repartidor = r.id_repartidor
                 JOIN Zonas z ON ST_Contains(z.area::GEOMETRY, o.ubicacion::GEOMETRY)
        WHERE z.id_zona = NEW.id_zona
          AND o.estado = 'completada'
    );

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trigger_listar_repartidores ON Zonas;

CREATE TRIGGER trigger_listar_repartidores
    AFTER INSERT OR UPDATE
    ON Zonas
    FOR EACH ROW
EXECUTE FUNCTION listar_repartidores_en_zona();