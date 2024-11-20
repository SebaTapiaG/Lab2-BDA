package proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import proyecto.entities.CategoriaEntity;
import proyecto.repositories.CategoriaRepository;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    public ResponseEntity<List<Object>> findAll(){
        return categoriaRepository.findAll();
    }
    public ResponseEntity<Object> findById(int id_categoria){
        return categoriaRepository.findById(id_categoria);
    }

    public ResponseEntity<Object> create(CategoriaEntity categoria){
        return categoriaRepository.create(categoria);
    }

    public ResponseEntity<Object> update(CategoriaEntity categoria){
        return categoriaRepository.update(categoria);
    }

    public ResponseEntity<Object> delete(int id_categoria){
        return categoriaRepository.delete(id_categoria);
    }

}
