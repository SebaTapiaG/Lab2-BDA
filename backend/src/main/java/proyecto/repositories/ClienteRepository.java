package proyecto.repositories;

import proyecto.entities.ClienteEntity;

import java.util.List;

public interface ClienteRepository {

    List<ClienteEntity> findAll();
    ClienteEntity findById(int id_cliente);

    ClienteEntity create(ClienteEntity cliente);

    ClienteEntity update(ClienteEntity cliente);

    boolean delete(int id_cliente);
}
