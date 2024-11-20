package proyecto.repositories;

import org.springframework.http.ResponseEntity;
import proyecto.entities.ClienteEntity;

import java.util.List;

public interface ClienteRepository {

    public ResponseEntity<Object> findByName(String name);

    public ResponseEntity<Object> createUser(ClienteEntity user);

    // Recupera todos los clientes
    public ResponseEntity<List<Object>> findAll();
    // Recupera un cliente por su ID
    public ResponseEntity<Object> findById(int id_cliente);

    // Recupera un cliente por su correo electrónico
    public ResponseEntity<Object> findByEmail(String email);

    // Actualiza un cliente
    public ResponseEntity<Object> update(ClienteEntity cliente);

    // Elimina un cliente por su ID
    public ResponseEntity<Object> delete(int id_cliente);

    // Realiza el login de un cliente
    public ResponseEntity<Object> loginUser(String email, String password);
}
