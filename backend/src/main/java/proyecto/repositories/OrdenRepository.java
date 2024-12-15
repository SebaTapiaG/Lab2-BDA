package proyecto.repositories;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import proyecto.dto.RepartidorDTO;
import proyecto.entities.OrdenEntity;

import java.util.List;

@Repository
public interface OrdenRepository {
    ResponseEntity<List<Object>> findAll();

    ResponseEntity findById(int id_orden);

    ResponseEntity<List<Object>> findByCliente(int id_cliente);

    ResponseEntity<List<Object>> findPagadas();

    ResponseEntity create(OrdenEntity orden);

    ResponseEntity update(OrdenEntity orden);

    ResponseEntity updateEstado(int id_orden, String estado, int id_repartidor);

    ResponseEntity delete(int id_orden);

    ResponseEntity<List<RepartidorDTO>> findDeliveryCompletedInArea(int id_zona);
}
