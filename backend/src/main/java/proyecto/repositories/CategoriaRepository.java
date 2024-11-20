package proyecto.repositories;


import java.util.List;

import org.springframework.http.ResponseEntity;
import proyecto.entities.CategoriaEntity;

public interface CategoriaRepository {

    ResponseEntity <List<Object>> findAll();
    ResponseEntity<Object> findById(int id_categoria);

    ResponseEntity<Object> create(CategoriaEntity categoria);

    ResponseEntity<Object> update(CategoriaEntity categoria);

    ResponseEntity<Object> delete(int id_categoria);



}
