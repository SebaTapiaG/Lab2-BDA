package proyecto.repositories;

import org.springframework.stereotype.Repository;
import proyecto.entities.OrdenEntity;

import java.util.List;

@Repository
public interface OrdenRepository {
    List<OrdenEntity> findAll();
    OrdenEntity findById(int id_orden);

    List<OrdenEntity> findByCliente(int id_cliente);

    OrdenEntity create(OrdenEntity orden);

    OrdenEntity update(OrdenEntity orden);

    boolean delete(int id_orden);
}
