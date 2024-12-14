package proyecto.repositories;

import org.springframework.http.ResponseEntity;
import proyecto.entities.AlmacenEntity;

import java.util.List;

public interface AlmacenRepository {
    ResponseEntity<List<Object>> findAll();
    ResponseEntity<Object> findById(int id_almacen);
    ResponseEntity<Object> create(AlmacenEntity almacen);
    ResponseEntity<Object> update(AlmacenEntity almacen);
    ResponseEntity<Object> delete(int id_almacen);

}
