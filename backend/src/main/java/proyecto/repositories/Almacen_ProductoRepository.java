package proyecto.repositories;

import org.springframework.http.ResponseEntity;
import proyecto.entities.Almacen_ProductoEntity;

import java.util.List;

public interface Almacen_ProductoRepository {
    ResponseEntity<List<Object>> findAll();
    ResponseEntity<Object> findById(int id_almacen_producto);
    ResponseEntity<Object> create(Almacen_ProductoEntity almacen_producto);
    ResponseEntity<Object> update(Object almacen_producto);
    ResponseEntity<Object> delete(int id_almacen_producto);

}
