package proyecto.repositories;

import org.springframework.http.ResponseEntity;
import proyecto.entities.ZonaEntity;

import java.util.List;

public interface ZonaRepository {
    ResponseEntity<List<Object>> findAll();
    ResponseEntity<Object> findById(int id_zona);
    ResponseEntity<List<Object>> findByEstado(String estado);
    ResponseEntity<Object> create(ZonaEntity zona);
    ResponseEntity<Object> update(ZonaEntity zona);
    ResponseEntity<Object> delete(int id_zona);
}
