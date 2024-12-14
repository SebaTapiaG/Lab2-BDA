package proyecto.repositories;
import org.springframework.http.ResponseEntity;
import proyecto.entities.RepartidorEntity;

import java.util.List;

public interface RepartidorRepository {
    public ResponseEntity<Object> findByNombre(String name);
    public ResponseEntity<Object> findByEmail(String email);
    public ResponseEntity<Object> findById(int id_repartidor);
    public ResponseEntity<List<Object>> findAll();
    public ResponseEntity<List<RepartidorEntity>>  findByAlmacen(int idAlmacen);
    public ResponseEntity<Object> update(RepartidorEntity repartidor);
    public ResponseEntity<Object> delete(int idRepartidor);
    public ResponseEntity<Object> register(RepartidorEntity repartidor);
    public ResponseEntity<Object> login(String email, String password);
}
