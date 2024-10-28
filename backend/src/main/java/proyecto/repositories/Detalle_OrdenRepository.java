package proyecto.repositories;

import proyecto.entities.Detalle_OrdenEntity;

import java.util.List;

public interface Detalle_OrdenRepository {
    List<Detalle_OrdenEntity> findAll();
    Detalle_OrdenEntity findById(int id_detalle_orden);

    Detalle_OrdenEntity create(Detalle_OrdenEntity detalle_orden);

    Detalle_OrdenEntity update(Detalle_OrdenEntity detalle_orden);

    boolean delete(int id_detalle_orden);
}
