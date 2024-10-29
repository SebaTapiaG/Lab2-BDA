package proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto.entities.CategoriaEntity;
import proyecto.repositories.CategoriaRepository;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    public List<CategoriaEntity> findAll(){
        return categoriaRepository.findAll();
    }

    public CategoriaEntity findById(int id_categoria){
        return categoriaRepository.findById(id_categoria);
    }

    public CategoriaEntity create(CategoriaEntity categoria){
        return categoriaRepository.create(categoria);
    }

    public CategoriaEntity update(CategoriaEntity categoria){
        return categoriaRepository.update(categoria);
    }

    public boolean delete(int id_categoria){
        return categoriaRepository.delete(id_categoria);
    }


}
