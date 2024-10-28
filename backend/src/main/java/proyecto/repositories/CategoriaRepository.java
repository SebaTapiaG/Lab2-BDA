package proyecto.repositories;


import java.util.List;
import proyecto.entities.CategoriaEntity;

public interface CategoriaRepository {

    List<CategoriaEntity> findAll();
    CategoriaEntity findById(int id_categoria);

    CategoriaEntity create(CategoriaEntity categoria);

    CategoriaEntity update(CategoriaEntity categoria);

    boolean delete(int id_categoria);



}
