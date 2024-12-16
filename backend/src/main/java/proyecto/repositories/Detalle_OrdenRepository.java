package proyecto.repositories;

import org.springframework.http.ResponseEntity;
import proyecto.entities.Detalle_OrdenEntity;

import java.util.List;

public interface Detalle_OrdenRepository {

    ResponseEntity <List<Object>> findAll();
    ResponseEntity<Object> findById(int id_detalle_orden);

    ResponseEntity<Object> create(Detalle_OrdenEntity detalle_orden);

    ResponseEntity<Object> update(Detalle_OrdenEntity detalle_orden);

    ResponseEntity<Object> delete(int id_detalle_orden);

    ResponseEntity<List<Object>> getDetallesByOrdenId(int id_orden);
}
