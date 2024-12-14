package proyecto.repositories;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AlmacenRepository {
    ResponseEntity<List<Object>> findAll();
    ResponseEntity<Object> findById(int id_almacen);
    ResponseEntity<Object> create(Object almacen);
    ResponseEntity<Object> update(Object almacen);
    ResponseEntity<Object> delete(int id_almacen);

}
