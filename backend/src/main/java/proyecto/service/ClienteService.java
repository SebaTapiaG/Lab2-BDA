package proyecto.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import proyecto.entities.ClienteEntity;
import proyecto.repositories.ClienteRepository;


import java.util.List;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    // Recupera todos los clientes
    public ResponseEntity<List<Object>> findAll() {
        return clienteRepository.findAll();
    }
    // Recupera un cliente por su ID
    public ResponseEntity<Object> findById(int id_cliente) {
        return clienteRepository.findById(id_cliente);
    }

    // Recupera un cliente por su nombre
    public ResponseEntity<Object> findByName(String name) {
        return clienteRepository.findByName(name);
    }

    // Crea un nuevo cliente
    public ResponseEntity<Object> createUser(ClienteEntity user) {
        return clienteRepository.createUser(user);
    }

    // Recupera un cliente por su correo electrónico
    public ResponseEntity<Object> findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }


    // Actualiza un cliente
    public ResponseEntity<Object> update(ClienteEntity cliente) {
        return clienteRepository.update(cliente);
    }

    // Elimina un cliente por su ID
    public ResponseEntity<Object> delete(int id_cliente) {
        return clienteRepository.delete(id_cliente);
    }

    // Realiza el login de un cliente
    public ResponseEntity<Object> loginUser(String email, String password) {
        return clienteRepository.loginUser(email, password);
    }
}
